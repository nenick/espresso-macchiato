package de.nenick.espressomacchiato.elements;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.test.views.LandscapeFixedActivity;
import de.nenick.espressotools.EspressoTestCase;

public class EspDeviceOrientationFixedTest extends EspressoTestCase<LandscapeFixedActivity> {

    private EspDevice espDevice = EspDevice.root();

    @After
    public void reset() {
        activityTestRule.getActivity().startActivity(new Intent(InstrumentationRegistry.getTargetContext(), BaseActivity.class));
        espDevice.rotateToPortrait();
    }

    @Test
    public void testRotateOnOrientationFixedActivity() {
        espDevice.assertOrientationIsLandscape();
        espDevice.rotateToLandscape();
        espDevice.assertOrientationIsLandscape();
    }
}
