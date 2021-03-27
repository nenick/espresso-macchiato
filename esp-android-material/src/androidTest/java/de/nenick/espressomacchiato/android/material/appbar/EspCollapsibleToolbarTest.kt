package de.nenick.espressomacchiato.android.material.appbar

import de.nenick.espressomacchiato.android.material.test.tools.CollapsibleToolbarActivityTest
import org.junit.Test

class EspCollapsibleToolbarTest : CollapsibleToolbarActivityTest() {

    @Test
    fun example() {
        EspCollapsibleToolbar {
            checkIsExpanded()

            performCollapse()
            checkIsCollapsed()

            performExpand()
            checkIsExpanded()

            Title { checkText("Collapsible") }
        }
    }
}