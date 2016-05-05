package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;

public class EspRecyclerView extends EspView {

    public static EspRecyclerView byId(int resourceId) {
        return new EspRecyclerView(resourceId);
    }

    public EspRecyclerView(int resourceId) {
        super(resourceId);
    }

    public EspRecyclerView(Matcher<View> base) {
        super(base);
    }

    public void assertItemCountIs(final int expectedCount) {
        findView().check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                RecyclerView recyclerView = (RecyclerView) view;
                int currentCount = recyclerView.getAdapter().getItemCount();
                if(currentCount != expectedCount) {
                    throw new AssertionError("RecyclerView with item count " + currentCount + " expected to have " + expectedCount + " items.");
                }
            }
        });
    }
}
