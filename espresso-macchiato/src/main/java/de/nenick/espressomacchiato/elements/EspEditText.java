package de.nenick.espressomacchiato.elements;

import androidx.test.espresso.action.ViewActions;
import android.view.View;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.matchers.EspAllOfBuilder;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Actions and assertions for a EditText.
 *
 * @since Espresso Macchiato 0.1
 */
public class EspEditText extends EspTextView {

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @return New element instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.1
     */
    public static EspEditText byId(int resourceId) {
        return new EspEditText(resourceId);
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
    public static EspEditText byText(String text) {
        return new EspEditText(withText(text));
    }

    /**
     * Create new instance matching an element with given text.
     *
     * @param resourceId Identifier for this element.
     *
     * @return New element instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.6
     */
    public static EspEditText byText(int resourceId) {
        return new EspEditText(withText(resourceId));
    }

    /**
     * Create an allOf matcher builder for this element.
     *
     * @return New allOf matcher builder.
     *
     * @since Espresso Macchiato 0.4
     */
    public static EspAllOfBuilder<EspEditText> byAll() {
        return new EspAllOfBuilder<EspEditText>() {
        };
    }

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @since Espresso Macchiato 0.3
     */
    public EspEditText(int resourceId) {
        super(resourceId);
    }

    /**
     * Create new element instance with custom base matcher.
     *
     * @param base Matcher for this element.
     *
     * @since Espresso Macchiato 0.2
     */
    public EspEditText(Matcher<View> base) {
        super(base);
    }

    /**
     * Create new instance based on given element matcher.
     *
     * @param template Pre configured element matcher.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspEditText(EspEditText template) {
        super(template);
    }

    /**
     * Set newText to the element.
     *
     * Existing text is full replaced by this action.
     *
     * @param newText New view text.
     *
     * @since Espresso Macchiato 0.1
     */
    public void replaceText(String newText) {
        findView().perform(ViewActions.replaceText(newText));
    }

    /**
     * Check that the element hint has the expected text.
     *
     * @param expectedText Expected hint text.
     *
     * @since Espresso Macchiato 0.3
     */
    public void assertHintTextIs(String expectedText) {
        findView().check(matches(withHint(expectedText)));
    }
}
