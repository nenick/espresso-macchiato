package de.nenick.espressomacchiato.elements;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.core.deps.guava.base.Preconditions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.Arrays;

import de.nenick.espressomacchiato.matchers.EspAllOfBuilder;
import de.nenick.espressomacchiato.matchers.support.EspIsDisplayedMatcher;

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
        return new EspAllOfBuilder<EspView>() {
        };
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

    /**
     * Assert you can see the whole view.
     * <p>
     * When the view height or width is greater than the screen it would still match.
     */
    public void assertIsDisplayedOnScreen() {
        findView().check(matches(EspIsDisplayedMatcher.isDisplayingAtLeast(100)));
    }

    public void assertIsVisible() {
        findView().check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    /**
     * Assert that you can't see the view.
     * <p>
     * Does only work if the view is still part of the view hierarchy.
     */
    public void assertIsHidden() {
        findView().check(matches(anyOf(
                withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE),
                withEffectiveVisibility(ViewMatchers.Visibility.GONE),
                not(EspIsDisplayedMatcher.isDisplayingAtLeast(1)))));
    }

    /**
     * Assert you can only see a small part of the view.
     * <p>
     * Does fail if the view is fully displayed.
     */
    public void assertIsPartiallyDisplayedOnly() {
        findView().check(matches(allOf(EspIsDisplayedMatcher.isDisplayingAtLeast(1), not(EspIsDisplayedMatcher.isDisplayingAtLeast(100)))));
    }

    /**
     * Assert that view is not a part of the view hierarchy.
     */
    public void assertNotExist() {
        findView().check(doesNotExist());
    }

    public void assertIsEnabled() {
        findView().check(matches(isEnabled()));
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

    public void swipeUp() {
        findView().perform(ViewActions.swipeUp());
    }

    public void swipeDown() {
        findView().perform(ViewActions.swipeDown());
    }


}
