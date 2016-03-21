package de.nenick.espressomacchiato.elements;

import android.view.View;

import org.hamcrest.Matcher;

public class EspPage extends EspView {

    public static EspPage byId(int resourceId) {
        return new EspPage(resourceId);
    }

    public EspPage(int resourceId) {
        super(resourceId);
    }

    public EspPage(Matcher<View> baseMatcher) {
        super(baseMatcher);
    }
}
