package de.nenick.espressomacchiato.tools;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import java.io.File;

/**
 * Tools for taking and comparing screenshots.
 */
public class EspScreenshotTool {

    /**
     * Use to assume full equality when comparing screenshots.
     */
    public static final double COMPARE_DELTA_FULL_IDENTICAL = 0.0;

    /**
     * Use to ignore time changes when comparing screenshots.
     *
     * Actionbar contains a time which must be ignored.
     */
    public static final double COMPARE_DELTA_TIME_CHANGE = 0.02;

    /**
     * This folder will be created in your application data to store screenshots.
     *
     * You can change the name to your needs.
     */
    public static String screenshotFolderName = "test-screenshots";

    public static void takeWithName(String name) {
        new EspScreenshotTool().takeWithNameInternal(name);
    }

    protected void takeWithNameInternal(String name) {
        if(!EspPermissionsTool.isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Log.i("EspressoMacchiato", "Store pictures only available with WRITE_EXTERNAL_STORAGE permission.");
            return;
        }

        File screenshotDirectory = new File(obtainScreenshotDirectory());
        if (!screenshotDirectory.exists() && !screenshotDirectory.mkdirs()) {
            throw new IllegalStateException("screenshot directory could not be created: " + screenshotDirectory.getAbsolutePath());
        }
        String screenshotName = name + ".png";
        File screenshotFile = new File(screenshotDirectory, screenshotName);
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

    public String obtainScreenshotDirectory() {
        File appStorage = getTargetContext().getFilesDir();
        if (appStorage == null) {
            throw new IllegalStateException("could not find directory to store screenshot");
        }
        return appStorage.getAbsolutePath() + "/" + screenshotFolderName;
    }

    protected Context getTargetContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    /**
     * Calculate percentage match of two images.
     *
     * Comparing images only works if both images have identical dimensions.
     * When to images are full identical then you get 100.0 percentage match else an value below of it.
     *
     * @param referenceImage Absolute path of the reference image.
     * @param comparedImage Absolute path of the image which should be compared with the referenceImage.
     *
     * @return Percentage match of comparedImage to the referenceImage. A value from 0.0 to 100.0 percentage.
     */
    public static double comparePercentage(String referenceImage, String comparedImage) {

        // original code https://rosettacode.org/wiki/Percentage_difference_between_images#Java

        Bitmap img1 = BitmapFactory.decodeFile(referenceImage);
        Bitmap img2 = BitmapFactory.decodeFile(comparedImage);

        int width1 = img1.getWidth();
        int width2 = img2.getWidth();
        int height1 = img1.getHeight();
        int height2 = img2.getHeight();

        if ((width1 != width2) || (height1 != height2)) {
            throw new AssertionError("Error: Images dimensions mismatch");
        }

        long diff = 0;
        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                int rgb1 = img1.getPixel(x, y);
                int rgb2 = img2.getPixel(x, y);
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = (rgb1) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = (rgb2) & 0xff;
                diff += Math.abs(r1 - r2);
                diff += Math.abs(g1 - g2);
                diff += Math.abs(b1 - b2);
            }
        }
        double n = width1 * height1 * 3;
        double p = diff / n / 255.0;

        return 100.0 - p * 100.0;
    }
}
