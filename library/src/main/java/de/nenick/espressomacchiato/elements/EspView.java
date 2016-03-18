package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Arrays;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;

public class EspView {

    protected final Matcher<View> baseMatcher;

    public static EspView byId(int resourceId) {
        return new EspView(resourceId);
    }

    public EspView(int resourceId) {
        this.baseMatcher = withId(resourceId);
    }

    public EspView(Matcher<View> baseMatcher) {
        this.baseMatcher = baseMatcher;
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
        findView(isDisplayed()).perform(ViewActions.click());
    }

    @SafeVarargs
    protected final ViewInteraction findView(Matcher<View>... additionalMatcher) {
        ArrayList<Matcher<? super View>> allMatcher = new ArrayList<>();
        allMatcher.add(baseMatcher);
        allMatcher.addAll(Arrays.asList(additionalMatcher));
        return onView(allOf(allMatcher));
    }
}
