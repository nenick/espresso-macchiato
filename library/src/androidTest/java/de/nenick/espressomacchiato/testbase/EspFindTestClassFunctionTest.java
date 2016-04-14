package de.nenick.espressomacchiato.testbase;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;

public class EspFindTestClassFunctionTest extends EspressoTestCase<BaseActivity> {

    @Test
    public void testFailure() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Could not find test class!");
        EspFindTestClassFunction.apply(new Thread().getStackTrace());

        //just for coverage
        new EspFindTestClassFunction();
    }
}