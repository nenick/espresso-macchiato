package de.nenick.espressomacchiato.testtools

import android.view.View
import androidx.test.espresso.Root
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import de.nenick.espressomacchiato.elements.basics.EspView
import de.nenick.espressomacchiato.internals.OpenForExtensions
import org.hamcrest.Matcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Constructor
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


abstract class ElementTest<ELEMENT : EspView> : BaseActivityTest() {

    val testViewId: Int = android.R.id.home
    val testView: View = View(context)

    @Before
    fun setupElementTest() {
        testView.id = testViewId
        addViewToRoot(testView)
    }

    @Test
    fun isOpenForExtensions() {
        val parameterizedType = this.javaClass.genericSuperclass as ParameterizedType
        val elementUnderTest = parameterizedType.actualTypeArguments[0] as Class<*>
        assertTrue("element is annotated to be open for extension", elementUnderTest.isAnnotationPresent(OpenForExtensions::class.java))
    }

    @Test
    fun hasDefaultConstructors() {
        val parameterizedType = this.javaClass.genericSuperclass as ParameterizedType
        // This cast should never fail, because we read the generic type from this class where ELEMENT is
        @Suppress("UNCHECKED_CAST")
        val elementType = parameterizedType.actualTypeArguments[0] as Class<ELEMENT>
        var elementInstance: ELEMENT
        var constructor: Constructor<ELEMENT>

        // default constructor for all situations
        constructor = elementType.getConstructor(Matcher::class.java, Matcher::class.java, Function1::class.java)
        assertEquals(parameterizedType<Matcher<View>>(), constructor.parameters[0].parameterizedType)
        assertEquals(parameterizedType<Matcher<Root>>(), constructor.parameters[1].parameterizedType)
        assertEquals(parameterizedType<Function1<ELEMENT, Unit>>(elementType), constructor.parameters[2].parameterizedType)

        elementInstance = constructor.newInstance(withId(testViewId), null, { _: ELEMENT -> })
        elementInstance.check(isDisplayed())

        // default convenience constructor for view id
        constructor = elementType.getConstructor(Int::class.java, Function1::class.java)
        assertEquals(Int::class.java, constructor.parameters[0].type)
        assertEquals(parameterizedType<Function1<ELEMENT, Unit>>(elementType), constructor.parameters[1].parameterizedType)

        elementInstance = constructor.newInstance(testViewId, { _: ELEMENT -> })
        elementInstance.check(isDisplayed())

        // default convenience constructor for view id with different root
        constructor = elementType.getConstructor(Int::class.java, Matcher::class.java, Function1::class.java)
        assertEquals(Int::class.java, constructor.parameters[0].type)
        assertEquals(parameterizedType<Matcher<Root>>(), constructor.parameters[1].parameterizedType)
        assertEquals(parameterizedType<Function1<ELEMENT, Unit>>(elementType), constructor.parameters[2].parameterizedType)

        elementInstance = constructor.newInstance(testViewId, null, { _: ELEMENT -> })
        elementInstance.check(isDisplayed())
    }

    private inline fun <reified T> parameterizedType(refParam : Type? = null) = object : GenericTypeResolver<T>(refParam) {}

    private abstract class GenericTypeResolver<out A>(val refParam : Type? = null) : Type {

        override fun getTypeName(): String {
            val a = this::class.java.genericSuperclass as ParameterizedType
            val b = a.actualTypeArguments[0] as ParameterizedType
            // In situation where we have a lambda returning a Unit we couldn't find a way to produce
            // exact the same signature. That's why we correct the Unit return type signature.
            return b.typeName
                    .replace("? extends kotlin.Unit", "kotlin.Unit")
                    .replace("? super ELEMENT", "? super ${refParam?.typeName}")
        }

        override fun toString(): String {
            return typeName
        }

        override fun equals(other: Any?): Boolean {
            if (other !is ParameterizedType) return false
            return typeName == other.typeName
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }
}


