package de.nenick.espressomacchiato.espresso.view

import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import de.nenick.espressomacchiato.internals.HasChecks
import org.hamcrest.CoreMatchers.not

interface EnabledAssertions: HasChecks {
    fun checkIsEnabled() = check(isEnabled())
    fun checkIsDisabled() = check(not(isEnabled()))
}