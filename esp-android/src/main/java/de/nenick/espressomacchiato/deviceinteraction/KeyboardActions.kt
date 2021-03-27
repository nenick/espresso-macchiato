package de.nenick.espressomacchiato.deviceinteraction

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.EspressoKey
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.isRoot

interface KeyboardActions {
    fun performCloseSoftKeyboard() = Espresso.closeSoftKeyboard()
    fun performPressImeActionButton() = Espresso.onView(isRoot()).perform(ViewActions.pressImeActionButton())
    fun performPressKey(key: EspressoKey) = Espresso.onView(isRoot()).perform(ViewActions.pressKey(key))
}