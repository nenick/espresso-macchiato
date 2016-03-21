package de.nenick.espressomacchiato.elements;

import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EspTextView extends EspView {

    public static EspTextView byId(int resourceId) {
        return new EspTextView(resourceId);
    }

    public EspTextView(int resourceId) {
        super(resourceId);
    }

    public EspTextView(Matcher<View> baseMatcher) {
        super(baseMatcher);
    }

    public void assertTextIs(String expectedText) {
        findView().check(matches(withText(expectedText)));
    }
}
