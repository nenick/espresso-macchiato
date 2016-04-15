package de.nenick.espressomacchiato.tools;

import android.Manifest;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

public class EspPermissionsToolTest extends EspressoTestCase<BaseActivity> {

    @Test
    public void testConstructor() {
        //just for coverage
        new EspPermissionsTool();
    }

    @Test
    public void testReportWhenPermissionNotDeclaredInManifest() {
        exception.expect(IllegalStateException.class);
        exception.expectMessage("Not all requested permissions are declared in your manifest files.");
        EspPermissionsTool.requestPermissions(getActivity(), 42, Manifest.permission.READ_SMS);
    }
}