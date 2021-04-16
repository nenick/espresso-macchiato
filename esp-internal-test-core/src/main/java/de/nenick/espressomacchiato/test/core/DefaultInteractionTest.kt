package de.nenick.espressomacchiato.test.core

import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Rule
import org.junit.rules.ExpectedException

abstract class DefaultInteractionTest : BaseActivityTest<BaseActivity>() {

    @get:Rule
    val expectedException = ExpectedException.none()!!

    override val activityScenarioRule = activityScenarioRule<BaseActivity>()
}