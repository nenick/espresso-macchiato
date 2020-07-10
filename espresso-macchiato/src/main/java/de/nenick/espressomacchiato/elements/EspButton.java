package de.nenick.espressomacchiato.elements;

import android.view.View;

import org.hamcrest.Matcher;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Actions and assertions for a Button.
 *
 * @since Espresso Macchiato 0.1
 */
public class EspButton extends EspView {

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @return New element instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.1
     */
    public static EspButton byId(int resourceId) {
        return new EspButton(resourceId);
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
    public static EspButton byText(String text) {
        return new EspButton(withText(text));
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
    public static EspButton byText(int resourceId) {
        return new EspButton(withText(resourceId));
    }

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @since Espresso Macchiato 0.1
     */
    public EspButton(int resourceId) {
        super(resourceId);
    }

    /**
     * Create new element instance with custom base matcher.
     *
     * @param base Matcher for this element.
     *
     * @since Espresso Macchiato 0.2
     */
    public EspButton(Matcher<View> base) {
        super(base);
    }

    /**
     * Create new instance based on given element matcher.
     *
     * @param template Pre configured element matcher.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspButton(EspButton template) {
        super(template);
    }

    /**
     * Checks that the current text matches the expected text.
     *
     * @param expected Expected text.
     *
     * @since Espresso Macchiato 0.1
     */
    public void assertTextIs(String expected) {
        findView().check(matches(withText(expected)));
    }

    /**
     * Checks that the current text matches the expected text.
     *
     * @param resourceId Expected text.
     *
     * @since Espresso Macchiato 0.6
     */
    public void assertTextIs(int resourceId) {
        findView().check(matches(withText(resourceId)));
    }
}
