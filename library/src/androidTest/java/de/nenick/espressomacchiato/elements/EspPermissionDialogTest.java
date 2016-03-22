package de.nenick.espressomacchiato.elements;

import android.Manifest;
import android.os.Build;

import org.hamcrest.Matchers;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.tools.EspPermissionsTool;
import de.nenick.espressotools.EspressoTestCase;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EspPermissionDialogTest extends EspressoTestCase<BaseActivity> {

    private static final int REQUEST_CODE = 42;
    private String testPermission = Manifest.permission.WRITE_CONTACTS;
    private EspPermissionDialog espPermissionDialog = EspPermissionDialog.build();

    @Before
    public void setup() {
        // permission only available since android marshmallow
        Assume.assumeThat(Build.VERSION.SDK_INT, Matchers.greaterThanOrEqualTo(Build.VERSION_CODES.M));

        EspPermissionsTool.resetAllPermission();
        assertTestPermissionIsDenied();
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
        assertTestPermissionIsDenied();
    }

    @Test
    public void testDenyWhenAllowed() throws Throwable {
        exception.expect(IllegalStateException.class);
        exception.expectMessage("Deny would revoke permission and restart app. This would let all following tests fail. See documentation for details.");

        whenRequestTestPermission();
        espPermissionDialog.allow();

        assertTestPermissionIsGranted();
        espPermissionDialog.deny();
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