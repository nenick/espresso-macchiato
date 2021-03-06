package de.nenick.espressomacchiato.espresso.view

import androidx.test.espresso.action.ViewActions.*
import de.nenick.espressomacchiato.internals.HasChecks
import de.nenick.espressomacchiato.internals.HasActions

interface TextActions : HasChecks, HasActions {
    fun performClearText() = perform(clearText())
    fun performTypeText(text: String) = perform(typeText(text))
    fun performReplaceText(text: String) = perform(replaceText(text))
}