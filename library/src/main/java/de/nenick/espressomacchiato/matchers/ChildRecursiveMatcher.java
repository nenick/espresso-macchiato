package de.nenick.espressomacchiato.matchers;

import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ChildRecursiveMatcher extends TypeSafeMatcher<View> {
    private final Matcher<View> childMatcher;

    public ChildRecursiveMatcher(Matcher<View> childMatcher) {
        this.childMatcher = childMatcher;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has child: ");
        childMatcher.describeTo(description);
    }

    @Override
    public boolean matchesSafely(View view) {
        if (!(view instanceof ViewGroup)) {
            return false;
        }

        ViewGroup group = (ViewGroup) view;
        for (int i = 0; i < group.getChildCount(); i++) {
            View childView = group.getChildAt(i);
            if (childMatcher.matches(childView)) {
                return true;
            }
            // check also recursively
            if (matchesSafely(childView)) {
                return true;
            }
        }

        return false;
    }
}
