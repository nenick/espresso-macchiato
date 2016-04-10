package de.nenick.espressomacchiato.tools;

import android.Manifest;
import android.support.test.InstrumentationRegistry;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import de.nenick.espressomacchiato.elements.EspPermissionDialog;
import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EspScreenshotToolTest extends EspressoTestCase<BaseActivity> {

    public static final String PICTURE_TEST_SCREEN = "Picture Test Screen";

    @Before
    public void setup() {
        EspPermissionsTool.ensurePermissions(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Test
    public void testScreenshot() {
        TextView textView = new TextView(getActivity());
        textView.setText(PICTURE_TEST_SCREEN);
        addViewToActivity(textView, BaseActivity.rootLayout);

        // wait until all expected content is displayed
        EspWait.forIdle();

        EspScreenshotTool.takeWithName("test screenshot");
        File screenshot = new File(InstrumentationRegistry.getTargetContext().getFilesDir(), "test-screenshots/test screenshot.png");
        assertThat(screenshot.exists(), is(true));
    }

    @Test
    public void testScreenshotWithDialog() {
        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setMessage(PICTURE_TEST_SCREEN));

        // wait until all expected content is displayed
        EspWait.forIdle();

        EspScreenshotTool.takeWithName("test screenshot with dialog");
        File screenshot = new File(InstrumentationRegistry.getTargetContext().getFilesDir(), "test-screenshots/test screenshot with dialog.png");
        assertThat(screenshot.exists(), is(true));
    }

    @Test
    public void testScreenshotWithPermissionDialog() {
        skipTestIfBelowAndroidMarshmallow();
        EspPermissionsTool.resetAllPermission();
        EspPermissionsTool.ensurePermissions(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        EspPermissionsTool.requestPermissions(getActivity(), 42, Manifest.permission.READ_CONTACTS);

        // wait until all expected content is displayed
        // EspWait.forIdle() will not finish with permission dialog
        EspWait.forDelay(2000);

        EspScreenshotTool.takeWithName("test screenshot with permission dialog");

        EspPermissionDialog.build(Manifest.permission.READ_CONTACTS).allow();
        File screenshot = new File(InstrumentationRegistry.getTargetContext().getFilesDir(), "test-screenshots/test screenshot with permission dialog.png");
        assertThat(screenshot.exists(), is(true));
    }
}