package de.nenick.espressomacchiato.androidx.drawer

import androidx.test.ext.junit.rules.activityScenarioRule
import de.nenick.espressomacchiato.androidx.drawer.test.R
import de.nenick.espressomacchiato.androidx.test.tools.DrawerActivity
import de.nenick.espressomacchiato.test.core.ElementTest
import org.junit.Rule
import org.junit.Test

class EspDrawerTest : ElementTest<EspDrawer, DrawerActivity>() {

    @get:Rule
    override val activityScenarioRule = activityScenarioRule<DrawerActivity>()

    @Test
    fun testDrawer() {
        EspDrawer(R.id.drawer_layout) {
            checkIsClosed()
            performOpen()
            checkIsOpen()
            performClose()
            checkIsClosed()
        }
    }
}