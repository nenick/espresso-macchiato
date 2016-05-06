package de.nenick.espressomacchiato.elements;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.view.View;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.matchers.EspAllOfBuilder;
import de.nenick.espressomacchiato.matchers.TextViewMatcher;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EspTextView extends EspView {

    public static EspTextView byId(int resourceId) {
        return new EspTextView(resourceId);
    }

    public static EspTextView byText(String text) {
        return new EspTextView(withText(text));
    }

    public static EspAllOfBuilder<? extends EspTextView> byAll() {
        return new EspAllOfBuilder<EspTextView>() {};
    }

    public EspTextView(int resourceId) {
        super(resourceId);
    }

    public EspTextView(Matcher<View> base) {
        super(base);
    }

    public void assertTextIs(String expected) {
        findView().check(matches(withText(expected)));
    }

    public void assertTextColorIs(@ColorInt int color) {
        findView().check(matches(TextViewMatcher.withTextColor(color)));
    }

    public void assertTextColorResIs(@ColorRes int color) {
        findView().check(matches(TextViewMatcher.withTextColorRes(color)));
    }
}
