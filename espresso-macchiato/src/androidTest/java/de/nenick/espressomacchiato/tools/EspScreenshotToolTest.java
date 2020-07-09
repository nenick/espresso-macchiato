package de.nenick.espressomacchiato.tools;

import android.Manifest;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.test.InstrumentationRegistry;
import android.test.mock.MockContext;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import de.nenick.espressomacchiato.elements.EspPermissionDialog;
import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/** Basic test */
public class EspScreenshotToolTest extends EspressoTestCase<BaseActivity> {

    public static final String PICTURE_TEST_SCREEN = "Picture Test Screen";

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
    public void testWithoutUiAutomator() {
        // may happen when storage is not setup properly on emulator
        EspScreenshotTool espScreenshotTool = new EspScreenshotTool() {
            @Override
            protected void throwIfUiAutomatorNotExist() throws ClassNotFoundException {
                throw new ClassNotFoundException();
            }
        };

        espScreenshotTool.takeWithNameInternal("test without uiautomator");

        File screenshot = new File(InstrumentationRegistry.getTargetContext().getFilesDir(), EspScreenshotTool.screenshotFolderName + "/test without uiautomator.png");
        assertThat(screenshot.exists(), is(true));
    }

    @Test
    public void testMkdirFailure() {
        // may happen when storage is not setup properly on emulator
        EspScreenshotTool espScreenshotTool = new EspScreenshotTool() {
            @Override
            public String obtainScreenshotDirectory() {
                return "/" + EspScreenshotTool.screenshotFolderName;
            }
        };

        exception.expect(IllegalStateException.class);
        exception.expectMessage("screenshot directory could not be created: /" + EspScreenshotTool.screenshotFolderName);
        espScreenshotTool.takeWithNameInternal("does not work");
    }

    @Test
    public void testGetFileDirFailure() {
        EspScreenshotTool espScreenshotTool = new EspScreenshotTool() {
            @Override
            protected Context getTargetContext() {
                return new MockContext() {
                    @Override
                    public File getFilesDir() {
                        // may happen when storage is not setup properly on emulator
                        return null;
                    }
                };
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
            public String obtainScreenshotDirectory() {
                return "/";
            }
        };

        exception.expect(IllegalStateException.class);
        exception.expectMessage("take picture failed");
        espScreenshotTool.takeWithNameInternal("does not work");
    }

    @Test
    public void testScreenshotLocationInternal() {
        File expectedLocation = new File(InstrumentationRegistry.getTargetContext().getFilesDir(), "test-screenshots" + "/location.png");
        File providedLocation = new EspScreenshotTool().screenshotLocationInternal("location");
        Assert.assertEquals(expectedLocation, providedLocation);
    }
}
