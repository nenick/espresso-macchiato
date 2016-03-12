package de.nenick.espressomacchiato.elements;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.view.View;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static de.nenick.espressomacchiato.actions.OrientationChangeAction.orientationLandscape;
import static de.nenick.espressomacchiato.actions.OrientationChangeAction.orientationPortrait;

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
        onView(isRoot()).check(orientation(Configuration.ORIENTATION_PORTRAIT));
    }

    /**
     * Check if activity orientation is landscape.
     */
    public void assertOrientationIsLandscape() {
        onView(isRoot()).check(orientation(Configuration.ORIENTATION_LANDSCAPE));
    }

    @NonNull
    private ViewAssertion orientation(final int expectedOrientation) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (noViewFoundException != null) {
                    throw noViewFoundException;
                }

                int requestedOrientation = ((Activity) InstrumentationRegistry.getContext()).getRequestedOrientation();
                if (currentOrientation() != expectedOrientation) {
                    String errorMessage = "expected device orientation "
                            + orientationAsString(expectedOrientation)
                            + " but was " + orientationAsString(currentOrientation());
                    errorMessage += " and requested orientation is " + requestedOrientation;
                    throw new AssertionError(errorMessage);
                }
            }
        };
    }

    private static String orientationAsString(int orientation) {
        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                return "PORTRAIT";
            case Configuration.ORIENTATION_LANDSCAPE:
                return "LANDSCAPE";
            default:
                return "UNDEFINED";
        }
    }

    private int currentOrientation() {
        return InstrumentationRegistry.getContext().getResources().getConfiguration().orientation;
    }

}
