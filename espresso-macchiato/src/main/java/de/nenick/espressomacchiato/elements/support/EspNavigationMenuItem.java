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

public class EspNavigationMenuItem extends EspView {

    public static EspNavigationMenuItem byText(String text) {
        return new EspNavigationMenuItem(text);
    }

    public EspNavigationMenuItem(String itemText) {
        super(allOf(instanceOf(NavigationMenuItemView.class), EspChildRecursiveMatcher.withChildRecursive(withText(itemText)), isDisplayed()));
    }

    public EspNavigationMenuItem(Matcher<View> base) {
        super(base);
    }

    public EspNavigationMenuItem(EspNavigationMenuItem template) {
        super(template);
    }

    public void assertIsSelected() {
        findView().check(matches(withChild(isChecked())));
    }

    public void assertIsNotSelected() {
        findView().check(matches(withChild(isNotChecked())));
    }
}
