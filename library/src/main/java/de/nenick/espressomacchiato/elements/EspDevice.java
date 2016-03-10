package de.nenick.espressomacchiato.elements;

import android.content.Context;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;

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
}
