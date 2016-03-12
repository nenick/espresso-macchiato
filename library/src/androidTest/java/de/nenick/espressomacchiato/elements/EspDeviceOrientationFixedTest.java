package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.EspressoTestCase;
import de.nenick.espressomacchiato.test.views.LandscapeFixedActivity;

public class EspDeviceOrientationFixedTest extends EspressoTestCase<LandscapeFixedActivity> {

    private EspDevice espDevice = new EspDevice();

    @Test
    public void testRotateOnOrientationFixedActivity() {
        espDevice.assertOrientationIsLandscape();
        espDevice.rotateToLandscape();
        espDevice.assertOrientationIsLandscape();
    }
}
