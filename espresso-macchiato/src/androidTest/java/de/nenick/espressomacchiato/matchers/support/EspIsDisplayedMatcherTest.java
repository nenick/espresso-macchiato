package de.nenick.espressomacchiato.matchers.support;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestBase;

public class EspIsDisplayedMatcherTest extends EspressoTestBase<BaseActivity> {


    @Override
    public BaseActivity getActivity() {
        return null;
    }

    @Test
    public void testJustForCoverage(){
        new EspIsDisplayedMatcher();
    }
}