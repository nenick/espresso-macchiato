package de.nenick.espressomacchiato.testtools

import org.junit.Rule
import org.junit.rules.ExpectedException

abstract class AssertionTest : BaseActivityTest() {

    @get:Rule
    val expectedException = ExpectedException.none()!!

}