package de.nenick.espressomacchiato.elements;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EspButton extends EspView {

    public static EspButton byId(int resourceId) {
        return new EspButton(resourceId);
    }

    public EspButton(int resourceId) {
        super(resourceId);
    }

    public void assertTextIs(String expectedText) {
        findView().check(matches(withText(expectedText)));
    }
}
