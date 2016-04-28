package de.nenick.espressomacchiato.elements;

import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import de.nenick.espressomacchiato.tools.EspPermissionsTool;
import de.nenick.espressomacchiato.tools.EspWait;

public class EspPermissionDialog {

    /**
     * wait below 1000ms was sometimes not enough for permissions update on circle ci emulator
     */
    public static int DELAY_FOR_UPDATE_PERMISSION_STATE = 1000;
    private String[] permissions;

    public static EspPermissionDialog build(String ... permissions) {
        return new EspPermissionDialog(permissions);
    }

    public EspPermissionDialog(String[] permissions) {
        if(permissions.length < 1) {
            throw new IllegalStateException("No expected permissions specified. This could lead to curious test failures.");
        }
        this.permissions = permissions;
    }

    public void allow() {
        // permissions handling only available since android marshmallow
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        //In Android N the Package is com.google.android.packageinstaller
        click("com.android.packageinstaller:id/permission_allow_button");
        waitUntilPermissionIsChanged();
    }

    /**
     * Warning: revoke an already granted permission would force your app to restart (test fail).
     * <p>
     * Deny tests should be the first executed tests.
     * Before test run ensure that all permission are revoked.
     * <p>
     * 1. Option: ordered test execution to force deny test first @FixMethodOrder(MethodSorters.NAME_ASCENDING) to avoid revoke
     * <p>
     * 2. Option: reset all permissions {@link EspPermissionsTool#resetAllPermission()}
     */
    public void deny() {
        // permissions handling only available since android marshmallow
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        avoidAppCrashWhenDenyGrantedPermission();
        //In Android N the Package is com.google.android.packageinstaller
        click("com.android.packageinstaller:id/permission_deny_button");
        waitUntilPermissionIsChanged();
    }

    protected void click(String targetId) {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject button = device.findObject(new UiSelector().resourceId(targetId));

        try {
            button.waitForExists(3000);
            button.click();
        } catch (UiObjectNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private void avoidAppCrashWhenDenyGrantedPermission() {
        if (EspPermissionsTool.anyPermissionsGranted(permissions)) {
            throw new IllegalStateException("Deny would revoke permission and restart app. This would let all following tests fail. See documentation for details.");
        }
    }

    private void waitUntilPermissionIsChanged() {
        // need to wait some time until permission is changed
        EspWait.forDelay(DELAY_FOR_UPDATE_PERMISSION_STATE);
    }
}
