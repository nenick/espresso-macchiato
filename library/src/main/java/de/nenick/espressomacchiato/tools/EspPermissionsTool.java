package de.nenick.espressomacchiato.tools;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.v4.app.ActivityCompat;

import de.nenick.espressomacchiato.elements.EspPermissionDialog;

public class EspPermissionsTool {

    /**
     * wait below 8000ms was sometimes not enough for reset all permissions on circle ci emulator
     */
    public static int DELAY_FOR_COMMAND_EXECUTION = 8000;

    /**
     * Safe way to remove granted permission to you app without app restart.
     */
    public static void resetAllPermission() {
        // permissions handling only available since android marshmallow
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand("pm reset-permissions");

        EspWait.forDelay(DELAY_FOR_COMMAND_EXECUTION);
    }

    public static boolean isPermissionGranted(String permission) {
        return ActivityCompat.checkSelfPermission(InstrumentationRegistry.getTargetContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissions(Activity activity, int requestCode, String... permissions) {
        int foundCount = 0;
        try {
            String[] manifestPermissions = InstrumentationRegistry.getTargetContext().getPackageManager().getPackageInfo(InstrumentationRegistry.getTargetContext().getPackageName(), PackageManager.GET_PERMISSIONS).requestedPermissions;
            for (String manifestPermission : manifestPermissions) {
                for (String permission : permissions) {
                    if(manifestPermission.equals(permission)) {
                        foundCount++;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException(e);
        }

        if(foundCount != permissions.length) {
            throw new IllegalStateException("Not all requested permissions are declared in your manifest files.");
        }

        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    public static void ensurePermissions(Activity activity, String... permissions) {
        if (allPermissionsGranted(permissions)) {
            return;
        }

        EspPermissionsTool.requestPermissions(activity, 42, permissions);
        EspPermissionDialog.build(permissions).allow();
    }

    public static boolean allPermissionsGranted(String[] permissions) {
        boolean allPermissionsGranted = true;
        for (String permission : permissions) {
            if (!isPermissionGranted(permission)) {
                allPermissionsGranted = false;
            }
        }
        return allPermissionsGranted;
    }

    public static boolean anyPermissionsGranted(String[] permissions) {
        boolean anyPermissionsGranted = false;
        for (String permission : permissions) {
            if (isPermissionGranted(permission)) {
                anyPermissionsGranted = true;
            }
        }
        return anyPermissionsGranted;
    }
}
