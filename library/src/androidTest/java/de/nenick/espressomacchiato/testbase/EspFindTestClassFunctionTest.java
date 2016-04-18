package de.nenick.espressomacchiato.testbase;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;

public class EspFindTestClassFunctionTest extends EspressoTestCase<BaseActivity> {

    @Before
    public void setup() {
        //just for coverage
        new EspFindTestClassFunction();
    }

    @Test
    public void testFailure() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Could not find test class!");
        EspFindTestClassFunction.apply(new Thread().getStackTrace());
    }
}