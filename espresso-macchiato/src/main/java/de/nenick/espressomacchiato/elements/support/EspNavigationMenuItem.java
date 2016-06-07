package de.nenick.espressomacchiato.elements.support;

import android.support.design.internal.NavigationMenuItemView;
import android.view.View;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.elements.EspView;
import de.nenick.espressomacchiato.matchers.EspChildRecursiveMatcher;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * Checks and assertions for a NavigationView menu item.
 *
 * @since Espresso Macchiato 0.1
 */
public class EspNavigationMenuItem extends EspView {

    /**
     * Create new instance matching an element with given text.
     *
     * @param text Identifier for this element.
     *
     * @since Espresso Macchiato 0.1
     */
    public static EspNavigationMenuItem byText(String text) {
        return new EspNavigationMenuItem(text);
    }

    /**
     * Create new instance matching an element with given text.
     *
     * @param itemText Identifier for this element.
     *
     * @since Espresso Macchiato 0.1
     */
    public EspNavigationMenuItem(String itemText) {
        super(allOf(instanceOf(NavigationMenuItemView.class), EspChildRecursiveMatcher.withChildRecursive(withText(itemText)), isDisplayed()));
    }

    /**
     * Create new element instance with custom base matcher.
     *
     * @param base Matcher for this element.
     *
     * @since Espresso Macchiato 0.2
     */
    public EspNavigationMenuItem(Matcher<View> base) {
        super(base);
    }

    /**
     * Create new instance based on given element matcher.
     *
     * @param template Pre configured element matcher.
     *
     * @since Espresso Macchiato 0.5
     */
    public EspNavigationMenuItem(EspNavigationMenuItem template) {
        super(template);
    }

    /**
     * Check that the menu item is in state selected.
     *
     * @since Espresso Macchiato 0.1
     */
    public void assertIsSelected() {
        findView().check(matches(withChild(isChecked())));
    }

    /**
     * Check that the menu item is not in state selected.
     *
     * @since Espresso Macchiato 0.1
     */
    public void assertIsNotSelected() {
        findView().check(matches(withChild(isNotChecked())));
    }
}
