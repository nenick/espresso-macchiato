package de.nenick.espressomacchiato.viewinteraction

import androidx.test.espresso.action.ViewActions.*
import de.nenick.espressomacchiato.internals.HasActions

interface ClickActions : HasActions {
    fun performClick() = perform(click())
    fun performDoubleClick() = perform(doubleClick())
    fun performLongClick() = perform(longClick())
}