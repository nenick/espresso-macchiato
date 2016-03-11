package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.action.ViewActions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;

public class EspView {

    protected final int contentResource;

    public EspView(int contentResource) {
        this.contentResource = contentResource;
    }

    public void assertIsVisible() {
        onView(withId(contentResource)).check(matches(isDisplayed()));
    }

    public void assertIsHidden() {
        onView(withId(contentResource)).check(matches(not(isDisplayed())));
    }

    public void assertIsEnabled() {
        onView(withId(contentResource)).check(matches(isDisplayed()));
    }

    public void assertIsDisabled() {
        onView(withId(contentResource)).check(matches(not(isEnabled())));
    }

    public void click() {
        onView(withId(contentResource)).perform(ViewActions.click());
    }
}
