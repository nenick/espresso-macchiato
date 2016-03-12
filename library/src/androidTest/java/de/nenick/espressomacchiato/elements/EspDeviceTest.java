package de.nenick.espressomacchiato.elements;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.nenick.espressotools.EspressoTestCase;
import de.nenick.espressomacchiato.test.views.BaseActivity;

import static org.hamcrest.CoreMatchers.is;

public class EspDeviceTest extends EspressoTestCase<BaseActivity> {

    private EspDevice espDevice = new EspDevice();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testRotate() {
        espDevice.assertOrientationIsPortrait();

        espDevice.rotateToLandscape();
        espDevice.assertOrientationIsLandscape();

        espDevice.rotateToPortrait();
        espDevice.assertOrientationIsPortrait();
    }

    @Test
    public void testAssertOrientationIsPortraitFailure() {
        exception.expect(AssertionError.class);
        exception.expectMessage(is("expected device orientation PORTRAIT but was LANDSCAPE"));

        espDevice.rotateToLandscape();
        espDevice.assertOrientationIsPortrait();
    }

    @Test
    public void testAssertOrientationIsLandscapeFailure() {
        exception.expect(AssertionError.class);
        exception.expectMessage(is("expected device orientation LANDSCAPE but was PORTRAIT"));

        espDevice.rotateToPortrait();
        espDevice.assertOrientationIsLandscape();
    }
}
