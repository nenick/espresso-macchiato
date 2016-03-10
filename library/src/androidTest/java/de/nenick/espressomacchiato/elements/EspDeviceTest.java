package de.nenick.espressomacchiato.elements;

import android.content.res.Configuration;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AppCompatActivity;

import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EspDeviceTest {

    @Rule
    ActivityTestRule<AppCompatActivity> activityTestRule;

    EspDevice espDevice = new EspDevice();

    @Test
    public void testRotate() {
        assertThat(currentDeviceOrientation()).isEqualTo(Configuration.ORIENTATION_PORTRAIT);
        espDevice.rotate();
        assertThat(currentDeviceOrientation()).isEqualTo(Configuration.ORIENTATION_LANDSCAPE);
        espDevice.rotate();
        assertThat(currentDeviceOrientation()).isEqualTo(Configuration.ORIENTATION_PORTRAIT);
    }

    private int currentDeviceOrientation() {
        return activityTestRule.getActivity().getResources().getConfiguration().orientation;
    }
}
