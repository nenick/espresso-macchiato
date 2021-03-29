package de.nenick.espressomacchiato.viewinteraction

import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.Visibility.*
import de.nenick.espressomacchiato.EspGlobalSettings
import de.nenick.espressomacchiato.internals.HasChecks
import de.nenick.espressomacchiato.internals.HasInteractions
import org.hamcrest.CoreMatchers.anyOf
import org.hamcrest.CoreMatchers.not

interface VisibilityAssertions : HasChecks, HasInteractions {

    fun checkDoesNotExist() = onView().check(doesNotExist())!!

    fun checkIsDisplayed() = check(EspGlobalSettings.isDisplayedCheckStrategy)
    fun checkIsCompletelyDisplayed() = check(isCompletelyDisplayed())

    fun checkIsVisible() = check(withEffectiveVisibility(VISIBLE))
    fun checkIsInvisible() = check(withEffectiveVisibility(INVISIBLE))
    fun checkIsGone() = check(withEffectiveVisibility(GONE))

    fun checkIsHidden() = check(anyOf(
            withEffectiveVisibility(INVISIBLE),
            withEffectiveVisibility(GONE),
            not(isDisplayingAtLeast(1))))
}