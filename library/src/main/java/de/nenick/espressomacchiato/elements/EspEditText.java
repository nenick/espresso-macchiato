package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.action.ViewActions;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EspEditText extends EspView {

    public static EspEditText byId(int resourceId) {
        return new EspEditText(resourceId);
    }

    public EspEditText(int resourceId) {
        super(resourceId);
    }

    public EspEditText(Matcher<View> baseMatcher) {
        super(baseMatcher);
    }

    public void replaceText(String newText) {
       findView().perform(ViewActions.replaceText(newText));
    }

    public void assertTextIs(String expectedText) {
        findView().check(matches(withText(expectedText)));
    }

    public void assertHintTextIs(String expectedText) {
        findView().check(matches(withHint(expectedText)));
    }
}
