package de.nenick.espressomacchiato.tools;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;

import java.io.File;

public class EspScreenshotTool {

    public static void takeWithName(String name) {
        File sddir = new File(obtainScreenshotDirectory());
        if (!sddir.exists()) {
            throw new IllegalStateException("screenshot folder does not exist: " + sddir.getAbsolutePath());
        }
        String screenshotName = name + ".png";
        File screenShotFile = new File(obtainScreenshotDirectory(), screenshotName);

        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        if (!device.takeScreenshot(screenShotFile)) {
            throw new IllegalStateException("take picture failed");
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
