package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Arrays;

import de.nenick.espressomacchiato.matchers.EspAllOfBuilder;
import de.nenick.espressomacchiato.matchers.support.EspIsDisplayedMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.not;

/**
 * Basic view actions and assertions for a common view.
 *
 * Used as base class for mostly all view elements.
 * Extending this element is a good starting point if you need to create a new view element.
 *
 * @since Espresso Macchiato 0.1
 */
public class EspView {

    private final Matcher<View> baseMatcher;

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @return New element instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.1
     */
    public static EspView byId(int resourceId) {
        return new EspView(resourceId);
    }

    /**
     * Create an allOf matcher builder for this element.
     *
     * @return New allOf matcher builder.
     *
     * @since Espresso Macchiato 0.4
     */
    public static EspAllOfBuilder<? extends EspView> byAll() {
        return new EspAllOfBuilder<EspView>() {
        };
    }

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @since Espresso Macchiato 0.1
     */
    public EspView(int resourceId) {
        this.baseMatcher = withId(resourceId);
    }

    /**
     * Create new element instance with custom base matcher.
     *
     * @param base Matcher for this element.
     *
     * @since Espresso Macchiato 0.2
     */
    public EspView(Matcher<View> base) {
        this.baseMatcher = base;
    }

    /**
     * Create new instance based on given element matcher.
     *
     * @param template Pre configured element matcher.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspView(EspView template) {
        this.baseMatcher = template.baseMatcher;
    }

    /**
     * Check that a view is currently full shown on the screen so the user can see it.
     *
     * A view can exist and is in state visible but not currently displayed.
     * When the view height or width is greater than the screen it would still match.
     *
     * @since Espresso Macchiato 0.3
     */
    public void assertIsDisplayedOnScreen() {
        findView().check(matches(EspIsDisplayedMatcher.isDisplayingAtLeast(100)));
    }

    /**
     * Check that the view is in state visible.
     *
     * @since Espresso Macchiato 0.3
     */
    public void assertIsVisible() {
        findView().check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    /**
     * Check that you can't see the view.
     *
     * True when the view is in state invisible or gone or current not visible on screen.
     *
     * @since Espresso Macchiato 0.3
     */
    public void assertIsHidden() {
        findView().check(matches(anyOf(
                withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE),
                withEffectiveVisibility(ViewMatchers.Visibility.GONE),
                not(EspIsDisplayedMatcher.isDisplayingAtLeast(1)))));
    }

    /**
     * Check only a small part of the view is visible on screen.
     *
     * Does fail if the view is fully displayed.
     *
     * @since Espresso Macchiato 0.4
     */
    public void assertIsPartiallyDisplayedOnly() {
        findView().check(matches(allOf(EspIsDisplayedMatcher.isDisplayingAtLeast(1), not(EspIsDisplayedMatcher.isDisplayingAtLeast(100)))));
    }

    /**
     * Check that no view matches the given matcher in the view hierarchy.
     *
     * @since Espresso Macchiato 0.1
     */
    public void assertNotExist() {
        findView().check(doesNotExist());
    }

    /**
     * Check that the view is in state enabled.
     *
     * @since Espresso Macchiato 0.1
     */
    public void assertIsEnabled() {
        findView().check(matches(isEnabled()));
    }

    /**
     * Check that the view is in state disabled.
     *
     * @since Espresso Macchiato 0.1
     */
    public void assertIsDisabled() {
        findView().check(matches(not(isEnabled())));
    }

    /**
     * Perform click on the view.
     *
     * @since Espresso Macchiato 0.1
     */
    public void click() {
        findView(isDisplayed()).perform(ViewActions.click());
    }

    /**
     * Perform swipe up on the view.
     *
     * @since Espresso Macchiato 0.4
     */
    public void swipeUp() {
        findView().perform(ViewActions.swipeUp());
    }

    /**
     * Perform swipe down on the view.
     *
     * @since Espresso Macchiato 0.4
     */
    public void swipeDown() {
        findView().perform(ViewActions.swipeDown());
    }

    /**
     * Find view to perform actions or assertions.
     *
     * @param additional Provide extra matcher additional to the base matcher.
     *
     * @return View interaction to perform actions or assertions.
     */
    @SafeVarargs
    protected final ViewInteraction findView(Matcher<View>... additional) {
        ArrayList<Matcher<? super View>> allMatcher = new ArrayList<>();
        allMatcher.add(baseMatcher);
        allMatcher.addAll(Arrays.asList(additional));
        return onView(allOf(allMatcher));
    }
}
