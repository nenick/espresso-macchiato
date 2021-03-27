package de.nenick.espressomacchiato.viewinteraction

import androidx.test.espresso.action.ViewActions.*
import de.nenick.espressomacchiato.internals.HasActions
import de.nenick.espressomacchiato.internals.HasInteractions

interface GestureActions : HasActions, HasInteractions {
    fun performScrollTo() = onView().perform(scrollTo())

    fun performSwipeLeft() = perform(swipeLeft())
    fun performSwipeRight() = perform(swipeRight())
    fun performSwipeUp() = perform(swipeUp())
    fun performSwipeDown() = perform(swipeDown())
}