package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.EspressoTestCase;

public class EspDeviceTest extends EspressoTestCase {

    EspDevice espDevice = new EspDevice();

    @Test
    public void testRotate() {
//        onView(withText("blub")).check(matches(isDisplayed()));
        espDevice.checkOrientationIsPortrait();
        espDevice.rotate();
        espDevice.checkOrientationIsLandscape();
        espDevice.rotate();
        espDevice.checkOrientationIsPortrait();
    }
}
