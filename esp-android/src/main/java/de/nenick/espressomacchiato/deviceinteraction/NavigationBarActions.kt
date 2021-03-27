package de.nenick.espressomacchiato.deviceinteraction

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.pressMenuKey
import androidx.test.espresso.matcher.ViewMatchers.isRoot

interface NavigationBarActions {
    fun performPressBack() = Espresso.pressBack()
    fun performPressMenuKey() = Espresso.onView(isRoot()).perform(pressMenuKey())
}