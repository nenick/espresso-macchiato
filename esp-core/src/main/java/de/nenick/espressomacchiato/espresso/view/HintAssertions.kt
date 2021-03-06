package de.nenick.espressomacchiato.espresso.view

import androidx.test.espresso.matcher.ViewMatchers.withHint
import de.nenick.espressomacchiato.internals.HasChecks

interface HintAssertions : HasChecks {
    fun checkHint(text: String) = check(withHint(text))
}