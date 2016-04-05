package de.nenick.espressomacchiato.tools;

import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import java.io.File;

public class EspScreenshotTool {

    public static void takeWithName(String name) {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        File sddir = new File(obtainScreenshotDirectory());
        if (!sddir.exists() && !sddir.mkdir()) {
            throw new IllegalStateException("screenshot folder does not exist: " + sddir.getAbsolutePath());
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
        File externalCacheDir = InstrumentationRegistry.getTargetContext().getExternalCacheDir();
        if (externalCacheDir == null) {
            throw new IllegalStateException("could not find external cache dir to store screenshot");
        }
        return externalCacheDir.getAbsolutePath() + "/test-screenshots/";
    }
}
