package de.nenick.espressomacchiato.elements;

import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EspButton extends EspView {

    public static EspButton byId(int resourceId) {
        return new EspButton(resourceId);
    }

    public static EspButton byText(String text) {
        return new EspButton(withText(text));
    }

    public EspButton(int resourceId) {
        super(resourceId);
    }

    public EspButton(Matcher<View> base) {
        super(base);
    }

    public void assertTextIs(String expected) {
        findView().check(matches(withText(expected)));
    }
}
