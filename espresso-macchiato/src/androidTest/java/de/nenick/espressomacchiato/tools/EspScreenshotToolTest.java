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
        addViewToLayout(textView, BaseActivity.rootLayout);

        // wait until all expected content is displayed
        EspWait.forIdle();

        EspScreenshotTool.takeWithName("test screenshot");
        File screenshot = new File(InstrumentationRegistry.getTargetContext().getFilesDir(), EspScreenshotTool.screenshotFolderName + "/test screenshot.png");
        assertThat(screenshot.exists(), is(true));
    }

    @Test
    public void testScreenshotFromUiThread() {
        TextView textView = new TextView(getActivity());
        textView.setText(PICTURE_TEST_SCREEN);
        addViewToLayout(textView, BaseActivity.rootLayout);

        // wait until all expected content is displayed
        EspWait.forIdle();

        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                EspScreenshotTool.takeWithName("test screenshot ui thread");
            }
        });
        File screenshot = new File(InstrumentationRegistry.getTargetContext().getFilesDir(), EspScreenshotTool.screenshotFolderName + "/test screenshot ui thread.png");
        assertThat(screenshot.exists(), is(true));
    }

    @Test
    public void testScreenshotWithDialog() {
        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setMessage(PICTURE_TEST_SCREEN));

        // wait until all expected content is displayed
        EspWait.forIdle();

        EspScreenshotTool.takeWithName("test screenshot with dialog");
        File screenshot = new File(InstrumentationRegistry.getTargetContext().getFilesDir(), EspScreenshotTool.screenshotFolderName + "/test screenshot with dialog.png");
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
        File screenshot = new File(InstrumentationRegistry.getTargetContext().getFilesDir(), EspScreenshotTool.screenshotFolderName + "/test screenshot with permission dialog.png");
        assertThat(screenshot.exists(), is(true));
    }

    @Test
    public void testMissingPermission() {
        skipTestIfBelowAndroidMarshmallow();

        EspPermissionsTool.resetAllPermission();
        EspScreenshotTool.takeWithName("test screenshot missing permission");
        File screenshot = new File(InstrumentationRegistry.getTargetContext().getFilesDir(), EspScreenshotTool.screenshotFolderName + "/test screenshot missing permission.png");
        assertThat(screenshot.exists(), is(false));
    }

    @Test
    public void testMkdirFailure() {
        // may happen when storage is not setup properly on emulator
        EspScreenshotTool espScreenshotTool = new EspScreenshotTool() {
            @Override
            protected String obtainScreenshotDirectory() {
                return "/" + EspScreenshotTool.screenshotFolderName;
            }
        };

        exception.expect(IllegalStateException.class);
        exception.expectMessage("screenshot directory could not be created: /" + EspScreenshotTool.screenshotFolderName);
        espScreenshotTool.takeWithNameInternal("does not work");
    }

    @Test
    public void testGetFileDirFailure() {
        // may happen when storage is not setup properly on emulator
        EspScreenshotTool espScreenshotTool = new EspScreenshotTool() {
            @Override
            protected Context getTargetContext() {
                return new MockContext();
            }
        };

        exception.expect(IllegalStateException.class);
        exception.expectMessage("could not find directory to store screenshot");
        espScreenshotTool.takeWithNameInternal("does not work");
    }

    @Test
    public void testTakeScreenshotFailure() {
        // may happen when storage is not setup properly on emulator
        EspScreenshotTool espScreenshotTool = new EspScreenshotTool() {
            @Override
            protected String obtainScreenshotDirectory() {
                return "/";
            }
        };

        exception.expect(IllegalStateException.class);
        exception.expectMessage("take picture failed");
        espScreenshotTool.takeWithNameInternal("does not work");
    }
}
