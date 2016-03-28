package de.nenick.espressomacchiato.elements;

import android.view.View;

import org.hamcrest.Matcher;

public class EspListView extends EspAdapterView {

    public static EspListView byId(int resourceId) {
        return new EspListView(resourceId);
    }

    public EspListView(int resourceId) {
        super(resourceId);
    }

    public EspListView(Matcher<View> base) {
        super(base);
    }
}
