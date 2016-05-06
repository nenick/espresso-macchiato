package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Arrays;

import de.nenick.espressomacchiato.matchers.EspAllOfBuilder;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.not;

public class EspView {

    private final Matcher<View> baseMatcher;

    public static EspView byId(int resourceId) {
        return new EspView(resourceId);
    }

    public static EspAllOfBuilder<? extends EspView> byAll() {
        return new EspAllOfBuilder<EspView>() {};
    }

    public EspView(int resourceId) {
        this.baseMatcher = withId(resourceId);
    }

    public EspView(Matcher<View> base) {
        this.baseMatcher = base;
    }

    public EspView(EspView template) {
        this.baseMatcher = template.baseMatcher();
    }

    public void assertIsDisplayedOnScreen() {
        findView().check(matches(isCompletelyDisplayed()));
    }

    public void assertIsVisible() {
        findView().check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    public void assertIsHidden() {
        findView().check(matches(anyOf(
                withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE),
                withEffectiveVisibility(ViewMatchers.Visibility.GONE))));
    }

    public void assertNotExist() {
        findView().check(doesNotExist());
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
    protected final ViewInteraction findView(Matcher<View>... additional) {
        ArrayList<Matcher<? super View>> allMatcher = new ArrayList<>();
        allMatcher.add(baseMatcher);
        allMatcher.addAll(Arrays.asList(additional));
        return onView(allOf(allMatcher));
    }

    public Matcher<View> baseMatcher() {
        return baseMatcher;
    }
}
