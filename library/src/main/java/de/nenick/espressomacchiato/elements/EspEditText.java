package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.action.ViewActions;
import android.view.View;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.matchers.EspAllOfBuilder;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EspEditText extends EspView {

    public static EspEditText byId(int resourceId) {
        return new EspEditText(resourceId);
    }

    public static EspAllOfBuilder<EspEditText> byAll() {
        return new EspAllOfBuilder<EspEditText>() {};
    }

    public EspEditText(int resourceId) {
        super(resourceId);
    }

    public EspEditText(Matcher<View> base) {
        super(base);
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
