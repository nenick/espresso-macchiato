package de.nenick.espressomacchiato.assertions.support;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
