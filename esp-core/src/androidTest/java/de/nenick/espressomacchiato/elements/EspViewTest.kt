package de.nenick.espressomacchiato.elements

import android.view.View
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import de.nenick.espressomacchiato.elements.basics.EspView
import de.nenick.espressomacchiato.espresso.view.VisibilityAssertions
import de.nenick.espressomacchiato.testtools.ElementTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class EspViewTest : ElementTest<EspView>() {

    @get:Rule
    val expectedException = ExpectedException.none()!!

    private var viewWasClicked = false

    @Before
    fun setup() {
        testView.setOnClickListener { viewWasClicked = true }
    }

    @Test
    fun createInstanceByResId() {
        EspView(this.testViewId).check(isDisplayed())
    }

    @Test
    fun createInstanceByResIdWithLambdaReceiver() {
        EspView(this.testViewId) { check(isDisplayed()) }
    }

    @Test
    fun createInstanceByViewMatcher() {
        EspView(withId(this.testViewId)).check(isDisplayed())
    }

    @Test
    fun createInstanceByViewMatcherWithLambdaReceiver() {
        EspView(this.testViewId) { check(isDisplayed()) }
    }

    @Test
    fun extendableWithAnonymousObjectStyle() {
        val anonymous = object : EspView(this.testViewId), VisibilityAssertions {}
        anonymous.checkIsVisible()
    }

    @Test
    fun extendableWithSubClassStyle() {
        class MyView : EspView(this.testViewId), VisibilityAssertions
        MyView().checkIsVisible()
    }

    @Test
    fun supportsCustomInteractionsWithPureEspresso() {
        EspView(this.testViewId).onView().check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun supportsCustomAssertionsConveniently() {
        EspView(this.testViewId).check(isDisplayed())
    }

    @Test
    fun supportsCustomActionsConveniently() {
        EspView(this.testViewId).perform(ViewActions.click())
        assertTrue(viewWasClicked)
    }

    @Test
    fun performNotAllowedOnInaccessibleViews() {
        testView.visibility = View.INVISIBLE

        expectedException.expectCause(CauseMatcher(RuntimeException::class.java, "at least 90 percent of the view's area is displayed to the user"))
        EspView(this.testViewId).perform(ViewActions.click())
    }

    @Test
    fun supportsLambdaReceiverInExtendedElements() {
        class NewElement(interactions: NewElement.() -> Unit) : EspView(this.testViewId), VisibilityAssertions {
            init {
                interactions(this)
            }
        }

        NewElement { checkIsDisplayed() }
    }

    @Test
    fun supportsLambdaReceiverInExtendedElementsDirty() {
        // A bit dirty but does work also ...
        @Suppress("UNCHECKED_CAST")
        class NewElementDirty(interactions: NewElementDirty.() -> Unit) : EspView(this.testViewId, interactions as EspView.() -> Unit), VisibilityAssertions

        NewElementDirty { checkIsDisplayed() }
    }

    // Defining this class inside test method had crashed the compiler.
    class MatcherView(matcher: Matcher<View>) : EspView(matcher), Matcher<View> by matcher

    @Test
    fun supportsBeingAMatcher() {
        val matcherView = MatcherView(withId(this.testViewId))
        matcherView.check(matcherView)
    }

    class CauseMatcher(private val type: Class<out Throwable>, private val expectedMessage: String) : TypeSafeMatcher<Throwable>() {
        override fun matchesSafely(item: Throwable): Boolean {
            return (item.javaClass.isAssignableFrom(type)
                    && item.message!!.contains(expectedMessage))
        }

        override fun describeTo(description: Description) {
            description.appendText("expects type ")
                    .appendValue(type)
                    .appendText(" and a message ")
                    .appendValue(expectedMessage)
        }
    }
}