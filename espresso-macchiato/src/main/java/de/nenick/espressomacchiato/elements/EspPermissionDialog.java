package de.nenick.espressomacchiato.elements;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import de.nenick.espressomacchiato.tools.EspPermissionsTool;
import de.nenick.espressomacchiato.tools.EspWait;

/**
 * Actions and assertions for a permission request dialog.
 *
 * You must provide the expected permissions.
 * It is necessary to avoid test crashes when you accedentely remove a granted permission.
 * This type of error is not easy to detect when it occurs.
 *
 * For pre marshmallow version mostly all functions are disabled to avoid errors.
 *
 * @since Espresso Macchiato 0.2
 */
public class EspPermissionDialog {

    /**
     * Delay after clicking any permissions dialog button.
     *
     * Less than 1000ms was sometimes not enough for permissions update on circle ci emulator
     */
    public static int DELAY_FOR_UPDATE_PERMISSION_STATE = 1000;
    private String[] permissions;

    /**
     * Create new element instance for given permissions.
     *
     * @param permissions List of requested permissions.
     *
     * @return New element instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.2
     */
    public static EspPermissionDialog build(String... permissions) {
        return new EspPermissionDialog(permissions);
    }

    /**
     * Create new element instance for given permissions.
     *
     * @param permissions List of requested permissions.
     *
     * @since Espresso Macchiato 0.2
     */
    public EspPermissionDialog(String[] permissions) {
        if (permissions.length < 1) {
            throw new IllegalStateException("No expected permissions specified. This could lead to curious test failures.");
        }
        this.permissions = permissions;
    }

    /**
     * Perform click on allow permission button.
     *
     * This will fail if no permission dialog is shown except on pre Android M version.
     *
     * @since Espresso Macchiato 0.2
     */
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
     * Perform click on deny permission button.
     *
     * This will fail if no permission dialog is shown except on pre Android M version..
     *
     * > Warning: Revoking an already granted permission would force your app to restart (test fail).
     * You can workaround with @FixMethodOrder(MethodSorters.NAME_ASCENDING)
     * or use {@link EspPermissionsTool#resetAllPermission()} which does not restart your app.
     *
     * @since Espresso Macchiato 0.2
     */
    public void deny() {
        // permissions handling only available since android marshmallow
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        avoidAppCrashWhenDenyGrantedPermission();
        //In Android N preview the Package changed to com.google.android.packageinstaller
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
