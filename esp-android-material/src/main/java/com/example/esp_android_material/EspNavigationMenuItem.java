package com.example.esp_android_material;

import android.view.View;

import com.google.android.material.internal.NavigationMenuItemView;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.matchers.EspChildRecursiveMatcher;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * Checks and assertions for a NavigationView menu item.
 *
 * @since Espresso Macchiato 0.1
 */
public class EspNavigationMenuItem {

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
        super(CoreMatchers.allOf(CoreMatchers.instanceOf(NavigationMenuItemView.class), EspChildRecursiveMatcher.withChildRecursive(ViewMatchers.withText(itemText)), ViewMatchers.isDisplayed()));
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
        findView().check(ViewAssertions.matches(ViewMatchers.withChild(ViewMatchers.isChecked())));
    }

    /**
     * Check that the menu item is not in state selected.
     *
     * @since Espresso Macchiato 0.1
     */
    public void assertIsNotSelected() {
        findView().check(ViewAssertions.matches(ViewMatchers.withChild(ViewMatchers.isNotChecked())));
    }
}
