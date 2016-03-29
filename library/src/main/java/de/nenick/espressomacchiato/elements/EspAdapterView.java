package de.nenick.espressomacchiato.elements;

import android.view.View;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.assertions.AdapterViewItemCountAssertion;

public class EspAdapterView extends EspView {

    public static EspAdapterView byId(int resourceId) {
        return new EspAdapterView(resourceId);
    }

    public EspAdapterView(int resourceId) {
        super(resourceId);
    }

    public EspAdapterView(Matcher<View> base) {
        super(base);
    }

    public void assertItemCountIs(int expected) {
        findView().check(new AdapterViewItemCountAssertion(expected));
    }

}
