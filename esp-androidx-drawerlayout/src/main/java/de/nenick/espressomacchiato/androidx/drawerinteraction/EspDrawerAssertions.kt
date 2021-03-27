package de.nenick.espressomacchiato.androidx.drawerinteraction

import androidx.test.espresso.contrib.DrawerMatchers
import de.nenick.espressomacchiato.internals.HasChecks

interface EspDrawerAssertions : HasChecks {
    fun checkIsOpen() = check(DrawerMatchers.isOpen())
    fun checkIsClosed() = check(DrawerMatchers.isClosed())
}