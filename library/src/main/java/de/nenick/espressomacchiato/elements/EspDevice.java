package de.nenick.espressomacchiato.elements;

import android.app.Activity;
import android.content.Context;
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

public class EspDevice {

    public void clickBackButton() {
        Espresso.pressBack();
    }

    public void rotate() {
        Context context = InstrumentationRegistry.getTargetContext();
        int currentOrientation = context.getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            onView(isRoot()).perform(orientationLandscape());
        } else {
            onView(isRoot()).perform(orientationPortrait());
        }
    }

    public void assertOrientationIsPortrait() {
        onView(isRoot()).check(orientation(Configuration.ORIENTATION_PORTRAIT, "expected portrait"));
    }

    public void assertOrientationIsLandscape() {
        onView(isRoot()).check(orientation(Configuration.ORIENTATION_LANDSCAPE, "expected landscape"));
    }

    @NonNull
    private static ViewAssertion orientation(final int expectedOrientation, final String errorMessage) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if(noViewFoundException != null) {
                    throw noViewFoundException;
                }

                final Activity activity = (Activity) view.getContext();

                if (activity.getResources().getConfiguration().orientation != expectedOrientation) {
                    throw new AssertionError(errorMessage);
                }
            }
        };
    }
}
