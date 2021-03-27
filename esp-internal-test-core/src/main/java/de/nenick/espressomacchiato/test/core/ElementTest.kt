package de.nenick.espressomacchiato.test.core

import android.annotation.SuppressLint
import android.view.View
import androidx.test.espresso.Root
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import de.nenick.espressomacchiato.view.EspView
import de.nenick.espressomacchiato.internals.OpenForExtensions
import org.hamcrest.Matcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.lang.reflect.ParameterizedType

// It's complaining here but not when the "NewApi" usage is inside androidTest folder.
// This code is only targeting testing so we ignore this warning. Interestingly it works.
@SuppressLint("NewApi")
abstract class ElementTest<ELEMENT : EspView> : BaseActivityTest() {

    val testViewId: Int = android.R.id.home
    val testView: View = View(context).apply { id = testViewId }

    private val elementTypeUnderTest = thisGenericParameterType()

    @Before
    fun setupElementTest() {
        setViewToRoot(testView)
    }

    @Test
    fun isOpenForExtensions() {
        val hasOpenAnnotation = elementTypeUnderTest.isAnnotationPresent(OpenForExtensions::class.java)
        assertTrue("element is annotated to be open for extension", hasOpenAnnotation)
    }

    @Test
    fun hasDefaultConstructors() {
        hasConstructorForAllSituations()
        hasConstructorForViewId()
        hasConstructorForViewIdWithCustomRoot()
        assertEquals(6 /* each constructor exist twice */, elementTypeUnderTest.constructors.size)
    }

    fun hasConstructorForAllSituations() {
        val constructor = elementTypeUnderTest.getConstructor(Matcher::class.java, Matcher::class.java, Function1::class.java)

        assertEquals(parameterizedType<Matcher<View>>(), constructor.parameters[0].parameterizedType)
        assertEquals(parameterizedType<Matcher<Root>>(), constructor.parameters[1].parameterizedType)
        assertEquals(parameterizedType<Function1<ELEMENT, Unit>>(), constructor.parameters[2].parameterizedType)

        val elementInstance = constructor.newInstance(withId(testViewId), null, { _: ELEMENT -> })
        elementInstance.check(isDisplayed())
    }

    fun hasConstructorForViewId() {
        val constructor = elementTypeUnderTest.getConstructor(Int::class.java, Function1::class.java)

        assertEquals(Int::class.java, constructor.parameters[0].type)
        constructor.parameters[0].modifiers
        assertEquals(parameterizedType<Function1<ELEMENT, Unit>>(), constructor.parameters[1].parameterizedType)

        val elementInstance = constructor.newInstance(testViewId, { _: ELEMENT -> })
        elementInstance.check(isDisplayed())
    }

    fun hasConstructorForViewIdWithCustomRoot() {
        val constructor = elementTypeUnderTest.getConstructor(Int::class.java, Matcher::class.java, Function1::class.java)

        assertEquals(Int::class.java, constructor.parameters[0].type)
        assertEquals(parameterizedType<Matcher<Root>>(), constructor.parameters[1].parameterizedType)
        assertEquals(parameterizedType<Function1<ELEMENT, Unit>>(), constructor.parameters[2].parameterizedType)

        val elementInstance = constructor.newInstance(testViewId, RootMatchers.DEFAULT, { _: ELEMENT -> })
        elementInstance.check(isDisplayed())
    }

    private fun thisGenericParameterType(): Class<ELEMENT> {
        val thisGenericClassType = this.javaClass.genericSuperclass as ParameterizedType
        // This cast should never fail, because we read the generic type from this class where ELEMENT is
        @Suppress("UNCHECKED_CAST")
        return thisGenericClassType.actualTypeArguments[0] as Class<ELEMENT>
    }

    private inline fun <reified T> parameterizedType() = object : GenericTypeResolver<T>("ELEMENT", elementTypeUnderTest) {}
}


