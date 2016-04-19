package de.nenick.espressomacchiato.elements;

import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EspTextView extends EspView {

    public static EspTextView byId(int resourceId) {
        return new EspTextView(resourceId);
    }

    public static EspTextView byText(String text) {
        return new EspTextView(withText(text));
    }

    public EspTextView(int resourceId) {
        super(resourceId);
    }

    public EspTextView(Matcher<View> base) {
        super(base);
    }

    public void assertTextIs(String expected) {
        findView().check(matches(withText(expected)));
    }
}
