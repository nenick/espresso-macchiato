package de.nenick.espressomacchiato.assertions;

import android.content.res.Configuration;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import android.view.View;

public class OrientationAssertion implements ViewAssertion {

    private final int expectedOrientation;

    public static ViewAssertion isOrientationPortrait() {
        return new OrientationAssertion(Configuration.ORIENTATION_PORTRAIT);
    }

    public static ViewAssertion isOrientationLandscape() {
        return new OrientationAssertion(Configuration.ORIENTATION_LANDSCAPE);
    }

    private OrientationAssertion(int expectedOrientation) {
        this.expectedOrientation = expectedOrientation;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        int currentOrientation = currentOrientation();
        if (currentOrientation != expectedOrientation) {
            String errorMessage = "expected device orientation "
                    + orientationAsString(expectedOrientation)
                    + " but was " + orientationAsString(currentOrientation);
            throw new AssertionError(errorMessage);
        }
    }

    private int currentOrientation() {
        return InstrumentationRegistry.getContext().getResources().getConfiguration().orientation;
    }

    public static String orientationAsString(int orientation) {
        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                return "PORTRAIT";
            case Configuration.ORIENTATION_LANDSCAPE:
                return "LANDSCAPE";
            default:
                return "UNDEFINED";
        }
    }
}
