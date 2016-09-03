package de.nenick.espressomacchiato.assertions.support;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class LayoutManagerItemVisibilityAssertion implements ViewAssertion {
    private final int index;
    private final boolean shouldBeVisible;

    public static LayoutManagerItemVisibilityAssertion isHidden(int index) {
        return new LayoutManagerItemVisibilityAssertion(index, false);
    }

    public LayoutManagerItemVisibilityAssertion(int index, boolean shouldBeVisible) {
        this.index = index;
        this.shouldBeVisible = shouldBeVisible;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
            int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

            boolean indexInRange = firstVisibleItem <= index && index <= lastVisibleItem;
            if ((shouldBeVisible && !indexInRange) || (!shouldBeVisible && indexInRange)) {
                String errorMessage = "expected item " + index + " to " +
                        (shouldBeVisible ? "" : "not") + " be visible, but was" +
                        (indexInRange ? "" : " not") + " visible";
                throw new AssertionError(errorMessage);

            }
        }
    }
}
