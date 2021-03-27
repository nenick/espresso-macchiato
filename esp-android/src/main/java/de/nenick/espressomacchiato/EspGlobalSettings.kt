package de.nenick.espressomacchiato

import android.view.View
import androidx.test.espresso.action.ViewActions
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
    var isDisplayedCheckStrategy: Matcher<View> = ViewMatchers.isDisplayed()

    /**
     * Enforce use of a root matcher if non activity is in foreground (e.g. dialog).
     *
     * With this feature enabled, each action and check ensure that either the activity is in
     * foreground (nothing is hiding it) when you haven't provided a root matcher.
     *
     * In situations like interacting with dialogs you should always provide a root matcher.
     * Forgetting the root matcher is a common source of flaky tests with dialogs.
     *
     * Useful to debugging strange/flaky issues.
     */
    var activityForegroundCheckEnabled: Boolean = false

    /**
     * Adjust how text will be inserted in text inputs.
     *
     * A common option is to set this strategy to [ViewActions.replaceText]. This would reduce the
     * execution time to insert the text. The type text strategy does type every single character.
     *
     * Manly large test suits will benefit from pasting instead typing each character.
    */
    var typeTextStrategy = ViewActions::typeText

    /**
     * Resets the [EspGlobalSettings] after test execution.
     *
     * Useful for some rare use cases where you want to change the behavior for a single test case.
     *
     * <pre>
     * @Test
     * fun myTestMethod() = EspGlobalSettings.temporaryEspSettingsChange {
     *     // EspGlobalSettings.settingToChange = change
     *     // your test code
     * }
     * </pre>
     */
    fun temporaryEspSettingsChange(test: () -> Unit) {
        val isDisplayedCheckStrategy = this.isDisplayedCheckStrategy
        val activityForegroundCheckEnabled = this.activityForegroundCheckEnabled
        val typeTextStrategy = this.typeTextStrategy

        try {
            test()
        } finally {
            this.isDisplayedCheckStrategy = isDisplayedCheckStrategy
            this.activityForegroundCheckEnabled = activityForegroundCheckEnabled
            this.typeTextStrategy = typeTextStrategy
        }
    }
}