package de.nenick.espressomacchiato.tools;

import android.Manifest;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import java.io.File;

public class EspScreenshotTool {

    public static String screenshotFolderName = "test-screenshots";

    public static void takeWithName(String name) {
        if(!EspPermissionsTool.isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Log.i("EspressoMacchiato", "Store pictures only available with WRITE_EXTERNAL_STORAGE permission.");
            return;
        }

        File screenshotDirectory = new File(obtainScreenshotDirectory());
        if (!screenshotDirectory.exists() && !screenshotDirectory.mkdirs()) {
            throw new IllegalStateException("screenshot directory could not be created: " + screenshotDirectory.getAbsolutePath());
        }
        String screenshotName = name + ".png";
        File screenshotFile = new File(obtainScreenshotDirectory(), screenshotName);
        Log.v("EspressoMacchiato", "take picture at " + screenshotFile.getAbsolutePath());

        boolean isUiAutomatorIncluded;
        try {
            Class.forName("android.support.test.uiautomator.UiDevice");
            isUiAutomatorIncluded = true;
        } catch (ClassNotFoundException e) {
            isUiAutomatorIncluded = false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 && isUiAutomatorIncluded) {
            UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
            if (!device.takeScreenshot(screenshotFile)) {
                throw new IllegalStateException("take picture failed");
            }
        } else {
            try {
                new EspScreenshotToolPreJellyBeanMr2().takeScreenShot(screenshotFile);
            } catch (Exception e) {
                throw new IllegalStateException("take picture failed", e);
            }
        }
    }

    private static String obtainScreenshotDirectory() {
        File appStorage = InstrumentationRegistry.getTargetContext().getFilesDir();
        if (appStorage == null) {
            throw new IllegalStateException("could not find directory to store screenshot");
        }
        return appStorage.getAbsolutePath() + "/" + screenshotFolderName;
    }
}
