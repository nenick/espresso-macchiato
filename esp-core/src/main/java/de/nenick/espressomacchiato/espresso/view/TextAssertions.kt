package de.nenick.espressomacchiato.espresso.view

import androidx.annotation.StringRes
import androidx.test.espresso.matcher.ViewMatchers.withText
import de.nenick.espressomacchiato.internals.HasChecks

interface TextAssertions : HasChecks {
    fun checkText(text: String) = check(withText(text))
    fun checkText(@StringRes text: Int) = check(withText(text))
}