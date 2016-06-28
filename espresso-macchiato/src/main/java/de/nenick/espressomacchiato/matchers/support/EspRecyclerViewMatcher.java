package de.nenick.espressomacchiato.matchers.support;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import junit.framework.AssertionFailedError;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

public class EspRecyclerViewMatcher {

    private final Matcher<View> recyclerViewMatcher;
    private boolean allowItemNotFound = false;
    private boolean failWhenViewExist = false;

    public static EspRecyclerViewMatcher withRecyclerView(Matcher<View> recyclerViewMatcher) {
        return new EspRecyclerViewMatcher(recyclerViewMatcher);
    }

    public EspRecyclerViewMatcher(Matcher<View> recyclerViewMatcher) {
        this.recyclerViewMatcher = recyclerViewMatcher;
    }

    public EspRecyclerViewMatcher ignoreItemNotFound(boolean allow) {
        allowItemNotFound = allow;
        return this;
    }

    public Matcher<View> atIndex(final int position) {
        return atChildIndexOnView(position, null);
    }

    public Matcher<View> atChildIndexOnView(final int index, @Nullable final Matcher<View> childMatcher) {

        return new TypeSafeMatcher<View>() {
            Resources resources = null;
            View itemView;

            public void describeTo(Description description) {
                recyclerViewMatcher.describeTo(description);
                if (childMatcher != null) {
                    childMatcher.describeTo(description);
                }
            }

            public boolean matchesSafely(View view) {
                this.resources = view.getResources();

                if (itemView == null && (itemView = findExpectedItemView(view, index)) == null) {
                    return false;
                }

                if (childMatcher == null) {
                    return view == itemView;
                }

                Matcher<View> descendantOfSelectedItem = ViewMatchers.isDescendantOfA(Matchers.equalTo(itemView));
                return Matchers.allOf(descendantOfSelectedItem, childMatcher).matches(view);
            }

            /**
             * Checks all current displayed items if they match with expected item.
             */
            private
            @Nullable
            View findExpectedItemView(View possibleRecyclerView, int expectedAdapterIndex) {
                // we need the recycler view to access all current displayed items
                if (!recyclerViewMatcher.matches(possibleRecyclerView)) {
                    return null;
                }
                RecyclerView recyclerView = (RecyclerView) possibleRecyclerView;

                // compare children current adapter index with expected adapter index
                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    View recyclerViewChild = recyclerView.getChildAt(i);
                    if (recyclerView.getChildAdapterPosition(recyclerViewChild) == expectedAdapterIndex) {
                        if(failWhenViewExist) {
                            throw new AssertionFailedError("Requested item should be hidden but is displayed.");
                        }

                        return recyclerViewChild;
                    }
                }

                if (allowItemNotFound) {
                    return null;
                }

                throw new AssertionFailedError("Requested item is currently not displayed. Try first scrollTo() to make the item visible.");

            }
        };
    }

    public static Matcher<View> withMinimalAdapterItemCount(final int minimalCount) {
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
