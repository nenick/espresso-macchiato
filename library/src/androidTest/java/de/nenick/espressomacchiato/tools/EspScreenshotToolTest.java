package de.nenick.espressomacchiato.tools;

import android.Manifest;
import android.support.test.InstrumentationRegistry;
import android.widget.TextView;

import org.junit.Test;

import java.io.File;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EspScreenshotToolTest extends EspressoTestCase<BaseActivity> {

    public static final String PICTURE_TEST_SCREEN = "Picture Test Screen";

    @Test
    public void testScreenshot() {
        TextView textView = new TextView(getActivity());
        textView.setText(PICTURE_TEST_SCREEN);
        addViewToActivity(textView, BaseActivity.rootLayout);

        EspPermissionsTool.ensurePermissions(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        EspWait.forIdle();
        EspScreenshotTool.takeWithName("test screenshot");
        File screenshot = new File(InstrumentationRegistry.getTargetContext().getFilesDir(), "test-screenshots/test screenshot.png");
        assertThat(screenshot.exists(), is(true));
    }
}