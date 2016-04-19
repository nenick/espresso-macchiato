package de.nenick.espressomacchiato.testbase;

import android.app.Activity;

import org.junit.Test;

import static org.junit.Assert.assertNull;

public class EspressoTestBaseFailureTest extends EspressoTestBase {

    @Override
    public Activity getActivity() {
        return null;
    }

    @Test
    public void testCurrentActivity() {
        assertNull(currentActivity());
    }

    @Test
    public void testGetGenericActivityFailure() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Please provide generic start activity for " + getClass().getSimpleName() + " (e.g. extends EspressoTestCase<MyStartActivity>)");
        getGenericActivityClass();
    }
}