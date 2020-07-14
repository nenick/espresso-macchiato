package de.nenick.espressomacchiato.elements;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.nenick.espressomacchiato.matchers.EspAllOfBuilder;
import de.nenick.espressomacchiato.matchers.support.EspIsDisplayedMatcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isSelected;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
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
     * Checks if view has a currently displayed child with given text.
     *
     * @param expectedText Text which should be displayed.
     *
     * @since Espresso Macchiato 0.6
     */
    public Matcher<View> withDisplayedChild(String expectedText) {
        return withChild(allOf(withText(expectedText), isDisplayed()));
    }

    /**
     * Checks if view has a currently displayed child with given text.
     *
     * @param expectedText Text which should be displayed.
     *
     * @since Espresso Macchiato 0.6
     */
    public Matcher<View> withDisplayedChild(int expectedText) {
        return withChild(allOf(withText(expectedText), isDisplayed()));
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
     * True when the view is in state invisible, gone or currently not displayed on screen.
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
     * Check if at least a small part of the view is visible on screen.
     *
     * Does fail if the view is not displayed. Success if partially or full visible.
     *
     * @since Espresso Macchiato 0.4
     */
    public void assertIsPartiallyDisplayedOnScreen() {
        findView().check(matches(allOf(EspIsDisplayedMatcher.isDisplayingAtLeast(1))));
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
     * Check that the view is in state selected.
     *
     * @since Espresso Macchiato 0.6
     */
    public void assertIsSelected() {
        findView().check(matches(isSelected()));
    }

    /**
     * Check that the view is not in state selected.
     *
     * @since Espresso Macchiato 0.6
     */
    public void assertIsNotSelected() {
        findView().check(matches(not(isSelected())));
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
     * Perform double click on the view.
     *
     * @since Espresso Macchiato 0.1
     */
    public void doubleClick() {
        findView(isDisplayed()).perform(ViewActions.doubleClick());
    }

    /**
     * Perform long click on the view.
     *
     * @since Espresso Macchiato 0.6
     */
    public void longClick() {
        findView(isDisplayed()).perform(ViewActions.longClick());
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
     * Perform scrollTo view
     *
     * @since Espresso Macchiato 0.6
     */
    public void scrollTo() {
        findView().perform(ViewActions.scrollTo());
    }

    /**
     * Find view to perform actions or assertions.
     *
     * @param additional Provide extra matcher additional to the base matcher.
     *
     * @return View interaction to perform actions or assertions.
     *
     * @since Espresso Macchiato 0.1
     */
    protected ViewInteraction findView(List<Matcher<View>> additional) {
        ArrayList<Matcher<? super View>> allMatcher = new ArrayList<>();
        allMatcher.add(baseMatcher);
        allMatcher.addAll(additional);
        return onView(allOf(allMatcher));
    }

    /**
     * Convenience method for {@link #findView(List)}
     *
     * @since Espresso Macchiato 0.6
     */
    @SafeVarargs
    protected final ViewInteraction findView(Matcher<View>... matcher) {
        return findView(createMatcherList(matcher));
    }

    @SafeVarargs
    @NonNull
    protected final ArrayList<Matcher<View>> createMatcherList(Matcher<View>... matcher) {
        return new ArrayList<>(Arrays.asList(matcher));
    }

    protected Matcher<View> baseMatcher() {
        return baseMatcher;
    }
}
