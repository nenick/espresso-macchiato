package de.nenick.espressomacchiato.testbase;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;

/** Basic test */
public class EspCloseAllActivitiesFunctionTest extends EspressoTestCase<BaseActivity> {

    @Test
    public void testCoverage() {
        // just for coverage
        new EspCloseAllActivitiesFunction();
    }
}