package de.nenick.espressomacchiato.dialoginteraction

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import de.nenick.espressomacchiato.internals.IsActivityInForegroundMatcher

interface DialogVisibilityAssertions {
    fun checkIsDisplayed() {
        Espresso.onView(ViewMatchers.isRoot()).inRoot(RootMatchers.isDialog()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun checkNotDisplayed() {
        IsActivityInForegroundMatcher.assertActivityInForeground("Expected no dialog shown, but found something like a dialog.")
    }
}