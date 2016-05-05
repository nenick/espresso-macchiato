package de.nenick.espressomacchiato.elements.support;

import android.view.View;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.assertions.support.RecyclerViewItemCountAssertion;
import de.nenick.espressomacchiato.elements.EspView;

public class EspRecyclerView extends EspView {

    public static EspRecyclerView byId(int resourceId) {
        return new EspRecyclerView(resourceId);
    }

    public EspRecyclerView(int resourceId) {
        super(resourceId);
    }

    public EspRecyclerView(Matcher<View> base) {
        super(base);
    }

    public void assertItemCountIs(final int expectedCount) {
        findView().check(new RecyclerViewItemCountAssertion(expectedCount));
    }

}
