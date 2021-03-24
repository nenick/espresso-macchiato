package de.nenick.espressomacchiato.espresso.view

import androidx.test.espresso.matcher.ViewMatchers
import de.nenick.espressomacchiato.internals.HasChecks

interface CheckAssertions : HasChecks {
    fun checkIsChecked() = check(ViewMatchers.isChecked())
    fun checkIsNotChecked() = check(ViewMatchers.isNotChecked())
}