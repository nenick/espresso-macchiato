package de.nenick.espressomacchiato.elements;

import android.support.design.internal.NavigationMenuItemView;
import android.view.View;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.matchers.ChildRecursiveMatcher;

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
        super(allOf(instanceOf(NavigationMenuItemView.class), withChildGroups(withText(itemText)), isDisplayed()));
    }

    public void assertIsSelected() {
        findView().check(matches(withChild(isChecked())));
    }

    public void assertIsNotSelected() {
        findView().check(matches(withChild(isNotChecked())));
    }

    private static Matcher<View> withChildGroups(final Matcher<View> childMatcher) {
        return new ChildRecursiveMatcher(childMatcher);
    }
}
