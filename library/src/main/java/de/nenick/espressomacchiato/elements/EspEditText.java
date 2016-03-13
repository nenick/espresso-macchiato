package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.action.ViewActions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EspEditText extends EspView {

    public EspEditText(int contentResource) {
        super(contentResource);
    }

    public void replaceText(String newText) {
        onView(withId(contentResource)).perform(ViewActions.replaceText(newText));
    }

    public void assertTextIs(String expectedText) {
        onView(withId(contentResource)).check(matches(withText(expectedText)));
    }
}
