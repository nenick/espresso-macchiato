package de.nenick.espressomacchiato.assertions.support;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
