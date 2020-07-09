package de.nenick.espressomacchiato.assertions.support;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class LayoutManagerTypeAssertion implements ViewAssertion {

    private final Class<? extends RecyclerView.LayoutManager> layoutManagerClass;

    public LayoutManagerTypeAssertion(Class<? extends RecyclerView.LayoutManager> layoutManagerClass) {
        this.layoutManagerClass = layoutManagerClass;
    }

    public static ViewAssertion isGridLayoutManager() {
        return new LayoutManagerTypeAssertion(GridLayoutManager.class);
    }

    public static ViewAssertion isLinearLayoutManager() {
        return new LayoutManagerTypeAssertion(LinearLayoutManager.class);
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManagerClass != layoutManager.getClass()) {
            String errorMessage = "expected " + layoutManagerClass.getSimpleName()
                    + " but was " + layoutManager.getClass().getSimpleName();
            throw new AssertionError(errorMessage);
        }
    }
}
