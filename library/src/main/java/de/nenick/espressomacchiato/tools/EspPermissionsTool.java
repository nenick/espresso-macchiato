package de.nenick.espressomacchiato.tools;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.v4.app.ActivityCompat;

import de.nenick.espressomacchiato.elements.EspPermissionDialog;

public class EspPermissionsTool {

    /**
     * wait below 3000ms was sometimes not enough for reset all permissions on circle ci emulator
     */
    public static int DELAY_FOR_COMMAND_EXECUTION = 3000;

    /**
     * Safe way to remove granted permission to you app without app restart.
     */
    public static void resetAllPermission() {
        // permissions dialog is only be available since
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand("pm reset-permissions");

        try {
            Thread.sleep(DELAY_FOR_COMMAND_EXECUTION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isPermissionGranted(String permission) {
        return ActivityCompat.checkSelfPermission(InstrumentationRegistry.getTargetContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissions(Activity activity, int requestCode, String ... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    public static void ensurePermissions(Activity activity, String ... permissions) {
        EspPermissionsTool.requestPermissions(activity, 42, permissions);
        EspPermissionDialog.build().allow();
    }
}
