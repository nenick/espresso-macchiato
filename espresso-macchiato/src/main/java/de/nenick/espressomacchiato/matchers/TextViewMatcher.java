package de.nenick.espressomacchiato.matchers;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.test.InstrumentationRegistry;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class TextViewMatcher {

    public static Matcher<View> withTextColorRes(@ColorRes final int color) {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return withTextColor(targetContext.getResources().getColor(color, targetContext.getTheme()));
        } else {
            //noinspection deprecation - no alternative for pre Marshmallow devices
            return withTextColor(targetContext.getResources().getColor(color));
        }
    }

    public static Matcher<View> withTextColor(@ColorInt final int color) {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("text color ");
                description.appendValue(color);
            }

            @Override
            protected boolean matchesSafely(View item) {
                //noinspection SimplifiableIfStatement - for more readable code
                if(!(item instanceof TextView)) {
                    return false;
                }

                return ((TextView)item).getCurrentTextColor() == color;
            }
        };
    }
}
