package de.nenick.espressomacchiato

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

/** Defines some global settings to adjust espresso macchiato behaviors. */
object EspGlobalSettings {

    /**
     * Adjust how the isDisplayed checks behave.
     *
     * A common option is to set this strategy to [ViewMatchers.isCompletelyDisplayed]. This would
     * follow the idea that views are only usable if the view is full visible to the user.
     *
     * By default same logic like origin Espresso, partially displayed is enough.
     */
    var isDisplayedCheckStrategy: Matcher<View> = ViewMatchers.isDisplayed()!!

    /**
     * Adjust what should be checked before any interaction is performed.
     *
     * A common option is to set this strategy to [isDisplayedCheckStrategy]. This would follow
     * the idea that user can only interact with views, if they are displayed.
     *
     * By default same logic like origin Espresso, you can click views, which aren't displayed
     * to the user (e.g. out of screen, hidden).
     */
    var interactionPreconditionCheckStrategy: Matcher<View> = AnythingMatcher()

    /** Just accept every view in every condition how it has been found. */
    private class AnythingMatcher : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description?) {}
        override fun matchesSafely(item: View?) = true
    }
}