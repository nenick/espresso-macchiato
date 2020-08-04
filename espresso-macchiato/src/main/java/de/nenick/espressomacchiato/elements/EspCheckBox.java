package de.nenick.espressomacchiato.elements;

import android.view.View;

import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matcher;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

public class EspCheckBox extends EspView {

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @since Espresso Macchiato 0.6
     */
    public EspCheckBox(int resourceId) {
        super(resourceId);
    }

    /**
     * Create new element instance with custom base matcher.
     *
     * @param base Matcher for this element.
     *
     * @since Espresso Macchiato 0.6
     */
    public EspCheckBox(Matcher<View> base) {
        super(base);
    }

    /**
     * Create new instance based on given element matcher.
     *
     * @param template Pre configured element matcher.
     *
     * @since Espresso Macchiato 0.6
     */
    public EspCheckBox(EspCheckBox template) {
        super(template);
    }

    /**
     * Create new instance matching an element with given resourceId.
     *
     * @param resourceId Identifier for this element.
     *
     * @return New element instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.6
     */
    public static EspCheckBox byId(int resourceId) {
        return new EspCheckBox(resourceId);
    }

    /**
     * Create new instance matching an element with given text.
     *
     * @param text Identifier for this element.
     *
     * @return New element instance for actions and assertions.
     *
     * @since Espresso Macchiato 0.6
     */
    public static EspCheckBox byText(String text) {
        return new EspCheckBox(withText(text));
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
    public static EspCheckBox byText(int resourceId) {
        return new EspCheckBox(withText(resourceId));
    }

    /**
     * Checks that the checkbox is checked.
     *
     * @since Espresso Macchiato 0.6
     */
    public void assertIsChecked() {
        findView().check(matches(ViewMatchers.isChecked()));
    }

    /**
     * Checks that the checkbox is not checked.
     *
     * @since Espresso Macchiato 0.6
     */
    public void assertIsNotChecked() {
        findView().check(matches(not(ViewMatchers.isChecked())));
    }

}
