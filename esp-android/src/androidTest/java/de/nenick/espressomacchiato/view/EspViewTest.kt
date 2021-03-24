package de.nenick.espressomacchiato.view

import android.app.AlertDialog
import android.view.View
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import de.nenick.espressomacchiato.EspGlobalSettings
import de.nenick.espressomacchiato.espresso.view.VisibilityAssertions
import de.nenick.espressomacchiato.testtools.ElementTest
import de.nenick.espressomacchiato.widget.EspTextView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.lang.IllegalStateException

class EspViewTest : ElementTest<EspView>() {

    @get:Rule
    val expectedException = ExpectedException.none()!!

    @Test
    fun createInstanceByResId() {
        hasConstructorForViewId()
        EspView(this.testViewId).check(isDisplayed())
    }

    @Test
    fun createInstanceByResIdWithLambdaReceiver() {
        hasConstructorForViewId()
        EspView(this.testViewId) { check(isDisplayed()) }
    }

    @Test
    fun createInstanceByViewMatcher() {
        hasConstructorForAllSituations()
        EspView(withId(this.testViewId)).check(isDisplayed())
    }

    @Test
    fun createInstanceByViewMatcherWithLambdaReceiver() {
        hasConstructorForAllSituations()
        EspView(withId(this.testViewId)) { check(isDisplayed()) }
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
        var viewWasClicked = false
        testView.setOnClickListener { viewWasClicked = true }

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
    fun supportsLambdaReceiverInExtendedElementsAlternative() {
        // A bit dirty but does work also ...
        @Suppress("UNCHECKED_CAST")
        class NewElement(interactions: NewElement.() -> Unit) : EspView(this.testViewId, interactions as EspView.() -> Unit), VisibilityAssertions

        NewElement { checkIsDisplayed() }
    }

    // Defining this class inside test method had crashed the compiler.
    class MatcherView(matcher: Matcher<View>) : EspView(matcher), Matcher<View> by matcher

    @Test
    fun supportsBeingAMatcher() {
        val matcherView = MatcherView(withId(this.testViewId))
        // Simple example searches a view by id and match it is a view with the given id.
        // Just for simplification, other use cases like "is child of" would make more sense.
        matcherView.check(matcherView)
    }

    @Test
    fun detectsUnexpectedNonActivityRoot() {
        EspGlobalSettings.activityForegroundCheckEnabled = true

        // no issue when activity is in foreground
        EspView(this.testViewId).check(isDisplayed())

        runOnMainSync {
            AlertDialog.Builder(it)
                    .setMessage("Hello")
                    .show()
        }

        // dialog content can be accessed
        EspTextView(android.R.id.message, isDialog()).checkText("Hello")

        // does fail if you forgot the root matcher
        expectedException.expect(IllegalStateException::class.java)
        expectedException.expectMessage("Found something different than an activity in foreground. Is this a wrong situation or did you forgot to add a root matcher e.g. isDialog?")
        EspView(this.testViewId).check(isDisplayed())
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