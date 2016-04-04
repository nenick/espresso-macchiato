package de.nenick.espressomacchiato.tools;

import org.junit.Test;

import java.io.File;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EspScreenshotToolTest extends EspressoTestCase<BaseActivity> {

    @Test
    public void testScreenshot() {
        EspScreenshotTool.takeWithName("test screen");
        File screenshot = new File(getActivity().getExternalCacheDir(), "test-screenshots/test screen.png");
        assertThat(screenshot.exists(), is(true));

        //noinspection ResultOfMethodCallIgnored
        screenshot.delete();
    }
}