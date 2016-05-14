package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.action.ViewActions;
import android.view.View;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.matchers.EspAllOfBuilder;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EspEditText extends EspTextView {

    public static EspEditText byId(int resourceId) {
        return new EspEditText(resourceId);
    }

    public static EspEditText byText(String text) {
        return new EspEditText(withText(text));
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

    public EspEditText(EspEditText template) {
        super(template);
    }

    public void replaceText(String newText) {
       findView().perform(ViewActions.replaceText(newText));
    }

    public void assertHintTextIs(String expectedText) {
        findView().check(matches(withHint(expectedText)));
    }
}
