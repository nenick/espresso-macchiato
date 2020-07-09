package de.nenick.espressomacchiato.elements;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import android.view.View;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.matchers.EspAllOfBuilder;
import de.nenick.espressomacchiato.matchers.EspTextViewMatcher;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Actions and assertions for a TextView.
 *
 * @since Espresso Macchiato 0.1
 */
public class EspTextView extends EspView {

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @return New element instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.1
     */
    public static EspTextView byId(int resourceId) {
        return new EspTextView(resourceId);
    }

    /**
     * Create new instance matching an element with given text.
     *
     * @param text Identifier for this element.
     *
     * @return New element instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.2
     */
    public static EspTextView byText(String text) {
        return new EspTextView(withText(text));
    }

    /**
     * Create an allOf matcher builder for this element.
     *
     * @return New allOf matcher builder.
     *
     * @since Espresso Macchiato 0.4
     */
    public static EspAllOfBuilder<? extends EspTextView> byAll() {
        return new EspAllOfBuilder<EspTextView>() {
        };
    }

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @since Espresso Macchiato 0.1
     */
    public EspTextView(int resourceId) {
        super(resourceId);
    }

    /**
     * Create new element instance with custom base matcher.
     *
     * @param base Matcher for this element.
     *
     * @since Espresso Macchiato 0.2
     */
    public EspTextView(Matcher<View> base) {
        super(base);
    }

    /**
     * Create new instance based on given element matcher.
     *
     * @param template Pre configured element matcher.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspTextView(EspTextView template) {
        super(template);
    }

    /**
     * Check that the element has the expected text.
     *
     * @param expected Expected view text.
     *
     * @since Espresso Macchiato 0.1
     */
    public void assertTextIs(String expected) {
        findView().check(matches(withText(expected)));
    }

    /**
     * Check that the element has the expected text color.
     *
     * @param color Expected view text color value.
     *
     * @since Espresso Macchiato 0.5
     */
    public void assertTextColorIs(@ColorInt int color) {
        findView().check(matches(EspTextViewMatcher.withTextColor(color)));
    }

    /**
     * Check that the element has the expected text color.
     *
     * @param color Expected view text color resource id.
     *
     * @since Espresso Macchiato 0.5
     */
    public void assertTextColorResIs(@ColorRes int color) {
        findView().check(matches(EspTextViewMatcher.withTextColorRes(color)));
    }
}
