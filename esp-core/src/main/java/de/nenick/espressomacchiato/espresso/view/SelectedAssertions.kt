package de.nenick.espressomacchiato.espresso.view

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isSelected
import org.hamcrest.CoreMatchers

interface SelectedAssertions {

    fun onView(): ViewInteraction

    fun checkIsSelected() = onView().check(matches(isSelected()))
    fun checkIsNotSelected() = onView().check(matches(CoreMatchers.not(isSelected())))
}