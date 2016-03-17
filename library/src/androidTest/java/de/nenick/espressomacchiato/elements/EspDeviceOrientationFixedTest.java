package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LandscapeFixedActivity;
import de.nenick.espressotools.EspressoTestCase;

public class EspDeviceOrientationFixedTest extends EspressoTestCase<LandscapeFixedActivity> {

    private EspDevice espDevice = EspDevice.root();

    @Test
    public void testRotateOnOrientationFixedActivity() {
        espDevice.assertOrientationIsLandscape();
        espDevice.rotateToLandscape();
        espDevice.assertOrientationIsLandscape();
    }
}
