package de.nenick.espressomacchiato.espresso.dialog

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import de.nenick.espressomacchiato.view.IsActivityInForeground

interface DialogVisibilityAssertions {
    fun checkIsDisplayed() {
        Espresso.onView(ViewMatchers.isRoot()).inRoot(RootMatchers.isDialog()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun checkNotDisplayed() {
        IsActivityInForeground.assertActivityInForeground("Expected no dialog shown, but found something like a dialog.")
    }
}