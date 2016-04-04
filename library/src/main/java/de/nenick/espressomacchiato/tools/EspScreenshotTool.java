package de.nenick.espressomacchiato.tools;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import java.io.File;

public class EspScreenshotTool {

    public static void takeWithName(String name) {
        File sddir = obtainScreenshotDirectory();
        if (!sddir.exists()) {
            throw new IllegalStateException("screenshot folder does not exist: " + sddir.getAbsolutePath());
        }
        String screenshotName = name + ".png";
        File screenShotFile = new File(sddir, screenshotName);

        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Log.v("EspressoMacchiato", "Take screenshot to " + sddir);

        if (!device.takeScreenshot(screenShotFile)) {
            throw new IllegalStateException("take picture failed");
        }
    }

    private static File obtainScreenshotDirectory() {
        File externalCacheDir = InstrumentationRegistry.getTargetContext().getExternalCacheDir();
        return new File(externalCacheDir, "test-screenshots");
    }
}
