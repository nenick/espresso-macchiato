package de.nenick.espressomacchiato.android.material.test.tools

import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Rule

abstract class CollapsibleToolbarActivityTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<CollapsibleToolbarActivity>()


}