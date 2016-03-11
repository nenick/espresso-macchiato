package de.nenick.espressomacchiato.elements;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EspTextView extends EspView {

    public EspTextView(int contentResource) {
        super(contentResource);
    }

    public void assertTextIs(String expectedText) {
        onView(withId(contentResource)).check(matches(withText(expectedText)));
    }
}
