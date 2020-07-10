package de.nenick.espressomacchiato.tools;

import android.Manifest;
import android.os.Build;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.nenick.espressomacchiato.elements.EspDevice;
import de.nenick.espressomacchiato.elements.EspDrawer;
import de.nenick.espressomacchiato.test.views.NavigationDrawerActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/** Basic test */
@Ignore("See also EspPermissionDialogTest")
public class EspScreenshotToolCompareTest extends EspressoTestCase<NavigationDrawerActivity> {

    private static final double DELTA_FOR_DIFFERENT_DEVICES = 3.0;

    @Before
    public void setup() {
        EspPermissionsTool.ensurePermissions(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Test
    public void testCompareSamePicture() {
        EspScreenshotTool.takeWithName("testCompareSamePicture");
        double percentage = EspScreenshotTool.comparePercentage(
                new EspScreenshotTool().obtainScreenshotDirectory() + "/testCompareSamePicture.png",
                new EspScreenshotTool().obtainScreenshotDirectory() + "/testCompareSamePicture.png");

        assertEquals(100.0, percentage, EspScreenshotTool.COMPARE_DELTA_FULL_IDENTICAL);
    }

    @Test
    public void testCompareSamePictureWithAMinuteDifference() {
        EspScreenshotTool.takeWithName("testCompareSamePictureWithOneMinuteDifference");
        EspWait.forDelay(65000);
        EspScreenshotTool.takeWithName("testCompareSamePictureWithOneMinuteDifference2");

        double percentage = EspScreenshotTool.comparePercentage(
                new EspScreenshotTool().obtainScreenshotDirectory() + "/testCompareSamePictureWithOneMinuteDifference.png",
                new EspScreenshotTool().obtainScreenshotDirectory() + "/testCompareSamePictureWithOneMinuteDifference2.png");

        // pre v18 we can't use uiautomator for screenshots so can't include status bar with time changes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            assertNotEquals(100.0, percentage, EspScreenshotTool.COMPARE_DELTA_FULL_IDENTICAL);
        }

        assertEquals(100.0, percentage, EspScreenshotTool.COMPARE_DELTA_TIME_CHANGE);
    }

    @Test
    public void testCompareFailureWhenDifferentDimensions() {
        EspScreenshotTool.takeWithName("testCompareDifferentDimensions");
        EspDevice.root().rotateToLandscape();
        EspScreenshotTool.takeWithName("testCompareDifferentDimensions2");

        exception.expect(AssertionFailedError.class);
        exception.expectMessage("Images must have same dimensions.");
        EspScreenshotTool.comparePercentage(
                new EspScreenshotTool().obtainScreenshotDirectory() + "/testCompareDifferentDimensions.png",
                new EspScreenshotTool().obtainScreenshotDirectory() + "/testCompareDifferentDimensions2.png");
    }

    @Test
    public void testCompareDifferentPictures() {
        EspScreenshotTool.takeWithName("testCompareDifferentPictures");
        EspDrawer.byId(NavigationDrawerActivity.drawerLayout, NavigationDrawerActivity.drawerNavigationView).open();
        EspScreenshotTool.takeWithName("testCompareDifferentPictures2");

        double percentage = EspScreenshotTool.comparePercentage(
                new EspScreenshotTool().obtainScreenshotDirectory() + "/testCompareDifferentPictures.png",
                new EspScreenshotTool().obtainScreenshotDirectory() + "/testCompareDifferentPictures2.png");

        assertEquals(88.0, percentage, EspScreenshotTool.COMPARE_DELTA_TIME_CHANGE + DELTA_FOR_DIFFERENT_DEVICES);
    }

    @Test
    public void testScreenshotLocation() {
        EspScreenshotTool.takeWithName("screenshot location");
        assertTrue(EspScreenshotTool.screenshotLocation("screenshot location").exists());
    }
}
