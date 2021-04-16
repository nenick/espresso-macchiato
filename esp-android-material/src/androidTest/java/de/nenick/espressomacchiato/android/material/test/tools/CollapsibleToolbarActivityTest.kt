package de.nenick.espressomacchiato.android.material.test.tools

import androidx.test.ext.junit.rules.activityScenarioRule
import de.nenick.espressomacchiato.test.core.BaseActivityTest

abstract class CollapsibleToolbarActivityTest : BaseActivityTest<CollapsibleToolbarActivity>() {

    override var activityScenarioRule = activityScenarioRule<CollapsibleToolbarActivity>()
}