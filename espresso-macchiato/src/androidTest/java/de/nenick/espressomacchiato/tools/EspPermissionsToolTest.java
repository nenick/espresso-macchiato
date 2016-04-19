package de.nenick.espressomacchiato.tools;

import android.Manifest;
import android.app.Activity;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

public class EspPermissionsToolTest extends EspressoTestCase<BaseActivity> {

    private Activity activityWithUnknownPackage;

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

    @Test
    public void testFailureWrongPackageName() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                activityWithUnknownPackage = new Activity() {
                    @Override
                    public String getPackageName() {
                        return "unknown package";
                    }
                };
            }
        });

        exception.expect(IllegalStateException.class);
        exception.expectMessage("android.content.pm.PackageManager$NameNotFoundException: unknown package");
        EspPermissionsTool.requestPermissions(activityWithUnknownPackage, 42, Manifest.permission.WRITE_CONTACTS);
    }
}