package de.nenick.espressomacchiato.test.core

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Root
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import de.nenick.espressomacchiato.internals.OpenForExtensions
import de.nenick.espressomacchiato.view.EspView
import org.hamcrest.Matcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.lang.reflect.ParameterizedType

abstract class ElementTest<ELEMENT : EspView, TEST_ACTIVITY : Activity> : BaseActivityTest<TEST_ACTIVITY>() {

    val testViewId: Int = android.R.id.home
    val testView: View = View(context).apply {
        id = testViewId
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50)
        setBackgroundColor(context.resources.getColor(android.R.color.holo_green_dark))
    }

    private val elementTypeUnderTest = thisGenericParameterType()

    @Before
    fun setupElementTest() {
        addViewToRoot(testView)
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
        hasConstructorForCustomViewMatcher()
        assertEquals(8 /* each constructor exist twice */, elementTypeUnderTest.constructors.size)
    }

    fun hasConstructorForAllSituations() {
        val constructor = elementTypeUnderTest.getConstructor(Matcher::class.java, Matcher::class.java, Function1::class.java)

        assertEquals(parameterizedType<Matcher<View>>(), constructor.genericParameterTypes[0])
        assertEquals(parameterizedType<Matcher<Root>>(), constructor.genericParameterTypes[1])
        assertEquals(parameterizedType<Function1<ELEMENT, Unit>>(), constructor.genericParameterTypes[2])

        val elementInstance = constructor.newInstance(withId(testViewId), null, { _: ELEMENT -> })
        elementInstance.check(isDisplayed())
    }

    fun hasConstructorForViewId() {
        val constructor = elementTypeUnderTest.getConstructor(Int::class.java, Function1::class.java)

        assertEquals(Int::class.java, constructor.parameterTypes[0])
        assertEquals(parameterizedType<Function1<ELEMENT, Unit>>(), constructor.genericParameterTypes[1])

        val elementInstance = constructor.newInstance(testViewId, { _: ELEMENT -> })
        elementInstance.check(isDisplayed())
    }

    fun hasConstructorForViewIdWithCustomRoot() {
        val constructor = elementTypeUnderTest.getConstructor(Int::class.java, Matcher::class.java, Function1::class.java)

        assertEquals(Int::class.java, constructor.parameterTypes[0])
        assertEquals(parameterizedType<Matcher<Root>>(), constructor.genericParameterTypes[1])
        assertEquals(parameterizedType<Function1<ELEMENT, Unit>>(), constructor.genericParameterTypes[2])

        val elementInstance = constructor.newInstance(testViewId, RootMatchers.DEFAULT, { _: ELEMENT -> })
        elementInstance.check(isDisplayed())
    }

    fun hasConstructorForCustomViewMatcher() {
        val constructor = elementTypeUnderTest.getConstructor(Matcher::class.java, Function1::class.java)

        assertEquals(parameterizedType<Matcher<View>>(), constructor.genericParameterTypes[0])
        assertEquals(parameterizedType<Function1<ELEMENT, Unit>>(), constructor.genericParameterTypes[1])

        val elementInstance = constructor.newInstance(withId(testViewId), { _: ELEMENT -> })
        elementInstance.check(isDisplayed())
    }

    private fun thisGenericParameterType(): Class<ELEMENT> {
        val thisGenericClassType = this.javaClass.genericSuperclass as ParameterizedType
        // This cast should never fail, because we read the generic type from this class where ELEMENT is
        @Suppress("UNCHECKED_CAST")
        return thisGenericClassType.actualTypeArguments[0] as Class<ELEMENT>
    }

    private inline fun <reified T> parameterizedType() = object : GenericTypeResolver<T>("ELEMENT", elementTypeUnderTest.name) {}
}

