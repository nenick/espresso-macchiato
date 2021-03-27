package de.nenick.espressomacchiato.test.core

import androidx.test.ext.junit.rules.activityScenarioRule
import de.nenick.espressomacchiato.view.EspView
import org.junit.Rule

abstract class DefaultElementTest<ELEMENT : EspView> : ElementTest<ELEMENT, BaseActivity>() {

    @get:Rule
    override val activityScenarioRule = activityScenarioRule<BaseActivity>()
}