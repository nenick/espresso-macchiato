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
import static de.nenick.espressomacchiato.assertions.OrientationAssertion.isOrientationPotrait;

/**
 * Actions you can do with android devices.
 */
public class EspDevice {

    /**
     * wait below 300ms was to less for common emulator instance
     * wait below 2000ms was to less for emulator instance on circle ci
     */
    public static int DELAY_TO_CHANGE_KEYBOARD_STATE = 2500;

    public static EspDevice root() {
        return new EspDevice();
    }

    /**
     * Click androids back button.
     * <p>
     * This is not the up button from an ActionBar.
     */
    public void clickBackButton() {
        Espresso.pressBack();
    }

    /**
     * Rotate screen to portrait.
     * <p>
     * Orientation is only changed if activity does not specify android:screenOrientation.
     */
    public void rotateToPortrait() {
        onView(isRoot()).perform(orientationPortrait());
    }

    /**
     * Rotate screen to landscape.
     * <p>
     * Orientation is only changed if activity does not specify android:screenOrientation.
     */
    public void rotateToLandscape() {
        onView(isRoot()).perform(orientationLandscape());
    }

    /**
     * Check if activity orientation is portrait.
     */
    public void assertOrientationIsPortrait() {
        onView(isRoot()).check(isOrientationPotrait());
    }

    /**
     * Check if activity orientation is landscape.
     */
    public void assertOrientationIsLandscape() {
        onView(isRoot()).check(isOrientationLandscape());
    }

    /**
     * Close the soft keyboard is visible.
     */
    public void closeSoftKeyboard() {
        onView(isRoot()).perform(ViewActions.closeSoftKeyboard());
    }

    /**
     * Check soft keyboard is open.
     * <p>
     * For emulator disable the hardware keyboard support (hw.keyboard=no) or soft keyboard will never be shown.
     */
    public void assertSoftKeyboardIsOpen() {
        assertSoftKeyboardIsOpen(true);
    }

    /**
     * Check soft keyboard is closed.
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
