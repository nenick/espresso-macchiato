package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;

public class EspView {

    protected final int resourceId;

    public static EspView byId(int resourceId) {
        return new EspView(resourceId);
    }

    public EspView(int resourceId) {
        this.resourceId = resourceId;
    }

    public void assertIsVisible() {
        findView().check(matches(isDisplayed()));
    }

    public void assertIsHidden() {
        findView().check(matches(not(isDisplayed())));
    }

    public void assertIsEnabled() {
        findView().check(matches(isDisplayed()));
    }

    public void assertIsDisabled() {
        findView().check(matches(not(isEnabled())));
    }

    public void click() {
        findView().perform(ViewActions.click());
    }

    protected ViewInteraction findView() {
        return onView(withId(resourceId));
    }
}
