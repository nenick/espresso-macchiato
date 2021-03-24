package de.nenick.espressomacchiato.internals

import androidx.test.espresso.ViewAction

interface HasActions {
    /** Short way to perform actions on this view. */
    fun perform(action: ViewAction)
}