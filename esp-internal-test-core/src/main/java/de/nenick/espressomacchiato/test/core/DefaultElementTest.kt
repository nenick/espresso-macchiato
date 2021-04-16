package de.nenick.espressomacchiato.test.core

import androidx.test.ext.junit.rules.activityScenarioRule
import de.nenick.espressomacchiato.view.EspView

abstract class DefaultElementTest<ELEMENT : EspView> : ElementTest<ELEMENT, BaseActivity>() {

    override val activityScenarioRule = activityScenarioRule<BaseActivity>()
}