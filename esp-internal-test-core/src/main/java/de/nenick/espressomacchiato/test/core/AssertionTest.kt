package de.nenick.espressomacchiato.test.core

import org.junit.Rule
import org.junit.rules.ExpectedException

abstract class AssertionTest : BaseActivityTest() {

    @get:Rule
    val expectedException = ExpectedException.none()!!

}