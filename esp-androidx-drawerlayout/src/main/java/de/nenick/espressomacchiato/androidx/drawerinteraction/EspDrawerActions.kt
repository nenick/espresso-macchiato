package de.nenick.espressomacchiato.androidx.drawerinteraction

import androidx.test.espresso.contrib.DrawerActions
import de.nenick.espressomacchiato.internals.HasActions

interface EspDrawerActions : HasActions {
    fun performOpen() = perform(DrawerActions.open())
    fun performClose() = perform(DrawerActions.close())
}