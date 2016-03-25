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

    public EspAdapterView(Matcher<View> baseMatcher) {
        super(baseMatcher);
    }

    public void assertItemCountIs(final int expectedCount) {
        findView().check(new AdapterViewItemCountAssertion(expectedCount));
    }

}
