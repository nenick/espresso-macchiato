package de.nenick.espressomacchiato.espresso.view

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility.GONE
import androidx.test.espresso.matcher.ViewMatchers.Visibility.INVISIBLE
import androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import de.nenick.espressomacchiato.EspGlobalSettings
import org.hamcrest.CoreMatchers.anyOf
import org.hamcrest.CoreMatchers.not

interface VisibilityAssertions {

    fun onView(): ViewInteraction

    fun checkDoesNotExist() = onView().check(doesNotExist())

    fun checkIsDisplayed() = onView().check(matches(EspGlobalSettings.isDisplayedCheckStrategy))
    fun checkIsCompletlyDisplayed() = onView().check(matches(isCompletelyDisplayed()))

    fun checkIsVisible() = onView().check(matches(withEffectiveVisibility(VISIBLE)))
    fun checkIsInvisible() = onView().check(matches(withEffectiveVisibility(INVISIBLE)))
    fun checkIsGone() = onView().check(matches(withEffectiveVisibility(GONE)))

    fun checkIsHidden() = onView().check(matches(anyOf(
            withEffectiveVisibility(INVISIBLE),
            withEffectiveVisibility(GONE),
            not(isDisplayingAtLeast(1)))))
}