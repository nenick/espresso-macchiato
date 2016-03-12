package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.EspressoTestCase;

public class EspDeviceTest extends EspressoTestCase {

    private EspDevice espDevice = new EspDevice();

    @Test
    public void testRotate() {
        espDevice.assertOrientationIsPortrait();
        espDevice.rotate();
        espDevice.assertOrientationIsLandscape();
        espDevice.rotate();
        espDevice.assertOrientationIsPortrait();
    }
}
