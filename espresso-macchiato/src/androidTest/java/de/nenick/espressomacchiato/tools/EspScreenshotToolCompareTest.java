package de.nenick.espressomacchiato.tools;

import android.Manifest;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.v7.app.AlertDialog;
import android.test.mock.MockContext;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import de.nenick.espressomacchiato.elements.EspDrawer;
import de.nenick.espressomacchiato.elements.EspPermissionDialog;
import de.nenick.espressomacchiato.test.views.NavigationDrawerActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EspScreenshotToolCompareTest extends EspressoTestCase<NavigationDrawerActivity> {

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

        assertNotEquals(100.0, percentage, EspScreenshotTool.COMPARE_DELTA_FULL_IDENTICAL);
        assertEquals(100.0, percentage, EspScreenshotTool.COMPARE_DELTA_TIME_CHANGE);
    }

    @Test
    public void testCompareDifferentPictures() {
        EspScreenshotTool.takeWithName("testCompareDifferentPictures");
        EspDrawer.byId(NavigationDrawerActivity.drawerLayout, NavigationDrawerActivity.drawerNavigationView).open();
        EspScreenshotTool.takeWithName("testCompareDifferentPictures2");

        double percentage = EspScreenshotTool.comparePercentage(
                new EspScreenshotTool().obtainScreenshotDirectory() + "/testCompareDifferentPictures.png",
                new EspScreenshotTool().obtainScreenshotDirectory() + "/testCompareDifferentPictures2.png");

        assertEquals(85.1, percentage, EspScreenshotTool.COMPARE_DELTA_TIME_CHANGE);
    }
}
