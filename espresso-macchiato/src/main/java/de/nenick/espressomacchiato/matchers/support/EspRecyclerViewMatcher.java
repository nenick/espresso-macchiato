package de.nenick.espressomacchiato.matchers.support;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

public class EspRecyclerViewMatcher {

    private final int recyclerViewId;

    public static EspRecyclerViewMatcher withRecyclerView(int recyclerViewId) {
        return new EspRecyclerViewMatcher(recyclerViewId);
    }

    public EspRecyclerViewMatcher(int recyclerViewId) {
        this.recyclerViewId = recyclerViewId;
    }

    public Matcher<View> atChildIndex(final int position) {
        return atChildIndexOnView(position, null);
    }

    public Matcher<View> atChildIndexOnView(final int index, final Matcher<View> childMatcher) {

        return new TypeSafeMatcher<View>() {
            private View itemView;

            public void describeTo(Description description) {

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
                if(!ViewMatchers.withId(recyclerViewId).matches(view)){
                    return null;
                }

                RecyclerView recyclerView = (RecyclerView) view;
                if (recyclerView.getChildCount() < index) {
                    throw new AssertionError("Requested child at index " + index + " but recycler view has only " + recyclerView.getChildCount() + " visible childs.");
                }

                return recyclerView.getChildAt(index);
            }
        };
    }

    public static Matcher<View> withMinimalItemCount(final int minimalCount) {
        return new TypeSafeMatcher<View>() {

            @Override
            protected boolean matchesSafely(View item) {
                //noinspection SimplifiableIfStatement - for more readable code
                if (!(item instanceof RecyclerView)) {
                    return false;
                }
                return ((RecyclerView) item).getAdapter().getItemCount() >= minimalCount;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Adapter has minimal ");
                description.appendValue(minimalCount);
                description.appendText(" items (to access requested index ");
                description.appendValue(minimalCount - 1);
                description.appendText(")");
            }
        };
    }

}
