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
     * Enforce use of a root matcher if non activity is in foreground (e.g. dialog).
     *
     * With this feature enabled, each action and check ensure that either the activity is in
     * foreground (nothing is hiding it) or you have provided a root matcher.
     *
     * In situations like interacting with dialogs you should always provide a root matcher.
     * Forgetting the root matcher is a common source of flaky tests with dialogs.
     *
     * Useful to debugging strange/flaky issues.
     */
    var activityForegroundCheckEnabled: Boolean = false
}