package de.nenick.espressomacchiato.elements;

import android.Manifest;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;
import de.nenick.espressomacchiato.tools.EspPermissionsTool;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/** Basic tests */
public class EspPermissionDialogTest extends EspressoTestCase<BaseActivity> {

    private static final int REQUEST_CODE = 42;
    private String testPermission = Manifest.permission.WRITE_CONTACTS;
    private EspPermissionDialog espPermissionDialog = EspPermissionDialog.build(testPermission);

    @Before
    public void setup() {
        EspPermissionsTool.resetAllPermission();

        // deny permissions only available since android marshmallow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            assertTestPermissionIsDenied();
        }
    }

    @Test
    public void testAllow() throws Throwable {
        whenRequestTestPermission();
        espPermissionDialog.allow();
        assertTestPermissionIsGranted();
    }

    @Test
    public void testDeny() throws Throwable {
        whenRequestTestPermission();
        espPermissionDialog.deny();

        // deny permissions only available since android marshmallow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            assertTestPermissionIsDenied();
        }
    }

    @Test
    public void testClickFailure() throws Throwable {
        // deny permission only available since android marshmallow
        skipTestIfBelowAndroidMarshmallow();

        exception.expect(IllegalStateException.class);
        espPermissionDialog.deny();
    }

    @Test
    public void testDenyWhenAllowedFailure() throws Throwable {
        // deny permission only available since android marshmallow
        skipTestIfBelowAndroidMarshmallow();

        exception.expect(IllegalStateException.class);
        exception.expectMessage("Deny would revoke permission and restart app. This would let all following tests fail. See documentation for details.");

        whenRequestTestPermission();
        espPermissionDialog.allow();

        assertTestPermissionIsGranted();
        espPermissionDialog.deny();
    }

    @Test
    public void testDialogNeedsRequestedPermission() {
        exception.expect(IllegalStateException.class);
        exception.expectMessage("No expected permissions specified. This could lead to curious test failures.");
        EspPermissionDialog.build();
    }

    private void whenRequestTestPermission() {
        EspPermissionsTool.requestPermissions(getActivity(), REQUEST_CODE, testPermission);
    }

    private void assertTestPermissionIsGranted() {
        assertThat(EspPermissionsTool.isPermissionGranted(testPermission), is(true));
    }

    private void assertTestPermissionIsDenied() {
        assertThat(EspPermissionsTool.isPermissionGranted(testPermission), is(false));
    }
}