package de.nenick.espressomacchiato.android.material.test.tools

import androidx.test.ext.junit.rules.activityScenarioRule
import de.nenick.espressomacchiato.test.core.BaseActivityTest
import org.junit.Rule

abstract class CollapsibleToolbarActivityTest : BaseActivityTest<CollapsibleToolbarActivity>() {

    @get:Rule
    override var activityScenarioRule = activityScenarioRule<CollapsibleToolbarActivity>()
}