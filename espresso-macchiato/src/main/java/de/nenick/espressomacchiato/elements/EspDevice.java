package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;

import de.nenick.espressomacchiato.assertions.KeyboardAssertion;
import de.nenick.espressomacchiato.tools.EspWait;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static de.nenick.espressomacchiato.actions.OrientationChangeAction.orientationLandscape;
import static de.nenick.espressomacchiato.actions.OrientationChangeAction.orientationPortrait;
import static de.nenick.espressomacchiato.assertions.OrientationAssertion.isOrientationLandscape;
import static de.nenick.espressomacchiato.assertions.OrientationAssertion.isOrientationPortrait;

/**
 * Actions and assertions more related to a device.
 *
 * @since Espresso Macchiato 0.2
 */
public class EspDevice {

    /**
     * Delay to wait before keyboard state is checked.
     *
     * Waiting less than 4000ms was not enough for emulator instance on circle ci
     */
    public static int DELAY_TO_CHANGE_KEYBOARD_STATE = 4000;

    /**
     * Create new element instance.
     *
     * @return New Instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.2
     */
    public static EspDevice root() {
        return new EspDevice();
    }

    /**
     * Click androids back button.
     *
     * Perform back button click so that the onBackPressed() method is called for the current activity.
     * This is not the up button from an ActionBar.
     *
     * > Warning: When you back press on your root activity then your application becomes closed.
     * Closing the application let current and all following tests fail.
     * You can avoid closing your application with the <a href="https://github.com/nenick/espresso-macchiato/wiki/Recipe%20Dummy%20Launcher">Dummy Launcher</a> concept.
     *
     * @since Espresso Macchiato 0.2
     */
    public void clickBackButton() {
        Espresso.pressBack();
    }

    /**
     * Rotate screen to portrait.
     *
     * Rotate the device to portrait mode.
     * Waits until device has expected state to avoid flaky tests.
     * Orientation is only changed if activity does not specify android:screenOrientation.
     *
     * @since Espresso Macchiato 0.2
     */
    public void rotateToPortrait() {
        onView(isRoot()).perform(orientationPortrait());
    }

    /**
     * Rotate screen to landscape.
     *
     * Rotate the device to landscape mode.
     * Waits until device has expected state to avoid flaky tests.
     * Orientation is only changed if activity does not specify android:screenOrientation.
     *
     * @since Espresso Macchiato 0.2
     */
    public void rotateToLandscape() {
        onView(isRoot()).perform(orientationLandscape());
    }

    /**
     * Check that activity orientation is portrait.
     *
     * @since Espresso Macchiato 0.2
     */
    public void assertOrientationIsPortrait() {
        onView(isRoot()).check(isOrientationPortrait());
    }

    /**
     * Check that activity orientation is landscape.
     *
     * @since Espresso Macchiato 0.2
     */
    public void assertOrientationIsLandscape() {
        onView(isRoot()).check(isOrientationLandscape());
    }

    /**
     * Close the soft keyboard.
     *
     * Its recommend to close the soft keyboard after tests run which could open it.
     * Opened keyboards are sometimes not closed between tests and may result in flaky tests.
     *
     * > Warning: Close and open soft keyboard may take some time and Espresso don't wait for finish it.
     * Remember this if you have flaky tests with not displayed elements
     *
     * @since Espresso Macchiato 0.2
     */
    public void closeSoftKeyboard() {
        onView(isRoot()).perform(ViewActions.closeSoftKeyboard());
    }

    /**
     * Check that the soft keyboard is open.
     *
     * For emulator disable the hardware keyboard support (hw.keyboard=no) or soft keyboard will never be shown.
     *
     * > Warning: Espresso don't wait for keyboard open/close actions so this method have a fixed delay to avoid flaky tests.
     * You can adjust the delay by changing EspDevice.DELAY_TO_CHANGE_KEYBOARD_STATE
     *
     * @since Espresso Macchiato 0.2
     */
    public void assertSoftKeyboardIsOpen() {
        assertSoftKeyboardIsOpen(true);
    }

    /**
     * Check that the soft keyboard is closed.
     *
     * > Warning: Espresso don't wait for keyboard open/close actions so this method have a fixed delay to avoid flaky tests.
     * You can adjust the delay by changing EspDevice.DELAY_TO_CHANGE_KEYBOARD_STATE
     *
     * @since Espresso Macchiato 0.2
     */
    public void assertSoftKeyboardIsClosed() {
        assertSoftKeyboardIsOpen(false);
    }

    private void assertSoftKeyboardIsOpen(final boolean expectIsShown) {
        // give keyboard some initial time for hide/show actions on emulator
        EspWait.forDelay(DELAY_TO_CHANGE_KEYBOARD_STATE);

        onView(isRoot()).check(new KeyboardAssertion(expectIsShown));
    }
}
