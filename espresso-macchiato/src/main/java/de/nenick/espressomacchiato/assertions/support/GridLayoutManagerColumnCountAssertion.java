package de.nenick.espressomacchiato.assertions.support;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class GridLayoutManagerColumnCountAssertion implements ViewAssertion {
    private final int expectedColumnCount;

    public GridLayoutManagerColumnCountAssertion(int expectedColumnCount) {
        this.expectedColumnCount = expectedColumnCount;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            int spanCount = gridLayoutManager.getSpanCount();
            if (spanCount != expectedColumnCount) {
                String errorMessage = "expected column count " + expectedColumnCount
                        + " but was " + spanCount;
                throw new AssertionError(errorMessage);
            }
        } else {
            throw new IllegalStateException("no grid layout manager");
        }
    }
}
