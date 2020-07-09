package de.nenick.espressomacchiato.matchers;

import androidx.annotation.Nullable;
import androidx.test.espresso.matcher.ViewMatchers;
import android.view.View;
import android.widget.AdapterView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

public class EspAdapterViewItemMatcher {

    private Matcher<View> adapterViewMatcher;

    public static EspAdapterViewItemMatcher withAdapterView(Matcher<View> matcher) {
        return new EspAdapterViewItemMatcher(matcher);
    }

    public EspAdapterViewItemMatcher(Matcher<View> adapterViewMatcher) {
        this.adapterViewMatcher = adapterViewMatcher;
    }

    public Matcher<View> atChildIndex(int index) {
        return internalAtChildIndex(index, null);
    }

    public Matcher<View> atChildIndexOnView(int index, Matcher<View> childMatcher) {
        return internalAtChildIndex(index, childMatcher);
    }

    private Matcher<View> internalAtChildIndex(final int index, final @Nullable Matcher<View> childMatcher) {
        return new TypeSafeMatcher<View>() {

            private View itemView;

            public void describeTo(Description description) {
                adapterViewMatcher.describeTo(description);
            }

            public boolean matchesSafely(View view) {
                if (itemView == null && (itemView = findExpectedItemView(view)) == null) {
                    return false;
                }

                if (childMatcher == null) {
                    return view == itemView;
                }

                Matcher<View> descendantOfSelectedItem = ViewMatchers.isDescendantOfA(Matchers.equalTo(itemView));
                return Matchers.allOf(descendantOfSelectedItem, childMatcher).matches(view);
            }

            private View findExpectedItemView(View view) {
                if (!adapterViewMatcher.matches(view)) {
                    return null;
                }

                AdapterView adapterView = (AdapterView) view;
                if (adapterView.getChildCount() < index) {
                    throw new AssertionError("Requested child at index " + index + " but adapter view has only " + adapterView.getChildCount() + " visible childs.");
                }

                return adapterView.getChildAt(index);
            }
        };
    }
}
