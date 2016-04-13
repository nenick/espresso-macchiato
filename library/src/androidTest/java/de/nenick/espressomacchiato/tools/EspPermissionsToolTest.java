package de.nenick.espressomacchiato.tools;

import android.app.Activity;

import org.junit.Test;

import de.nenick.espressomacchiato.testbase.EspressoTestBase;

public class EspPermissionsToolTest extends EspressoTestBase {

    @Test
    public void testConstructor() {
        //just for coverage
        new EspPermissionsTool();
    }

    @Override
    public Activity getActivity() {
        // current we don't need any activity for given tests in this class
        return null;
    }
}