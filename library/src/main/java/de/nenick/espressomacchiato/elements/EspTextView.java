package de.nenick.espressomacchiato.elements;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EspTextView extends EspView {

    public EspTextView(int contentResource) {
        super(contentResource);
    }

    public void assertTextIs(String expectedText) {
        findView().check(matches(withText(expectedText)));
    }
}
