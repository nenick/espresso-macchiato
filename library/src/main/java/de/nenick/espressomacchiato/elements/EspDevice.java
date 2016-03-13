package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.Espresso;

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
     * Click androids back button.
     * <p/>
     * This is not the up button from an ActionBar.
     */
    public void clickBackButton() {
        Espresso.pressBack();
    }

    /**
     * Rotate screen to portrait.
     * <p/>
     * Orientation is only changed if activity does not specify android:screenOrientation.
     */
    public void rotateToPortrait() {
        onView(isRoot()).perform(orientationPortrait());
    }

    /**
     * Rotate screen to landscape.
     * <p/>
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
}
