package de.nenick.espressomacchiato.elements.support;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import junit.framework.AssertionFailedError;

import org.junit.Ignore;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LongRecyclerActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

/** Basic tests */
public class EspRecyclerViewLayoutManagerTest extends EspressoTestCase<LongRecyclerActivity> {

    private static final int GRID_COLUMN_COUNT = 5;
    private EspRecyclerView espRecyclerView = EspRecyclerView.byId(LongRecyclerActivity.recyclerViewId);

    @Test
    @Ignore("looks like fixed with 24.2.0")
    public void testAssertLinearLayout() {
        espRecyclerView.layoutManager().assertLinearLayoutManager();

        givenGridLayoutManager();

        exception.expect(AssertionFailedError.class);
        exception.expectMessage("expected LinearLayoutManager but was GridLayoutManager");
        espRecyclerView.layoutManager().assertLinearLayoutManager();
    }

    @Test
    @Ignore("looks like fixed with 24.2.0")
    public void testAssertGridLayout() {
        givenGridLayoutManager();
        espRecyclerView.layoutManager().assertGridLayoutManager();

        exception.expect(AssertionFailedError.class);
        exception.expectMessage("expected GridLayoutManager but was LinearLayoutManager");

        givenLinearLayoutManager();
        espRecyclerView.layoutManager().assertGridLayoutManager();
    }

    @Test
    @Ignore("looks like fixed with 24.2.0")
    public void testAssertGridColumnCount() {
        givenGridLayoutManager();
        espRecyclerView.layoutManager().assertColumnCount(GRID_COLUMN_COUNT);
    }

    private void givenLinearLayoutManager() {
        performOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((RecyclerView) getActivity().findViewById(LongRecyclerActivity.recyclerViewId)).setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });
    }

    private void givenGridLayoutManager() {
        performOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((RecyclerView) getActivity().findViewById(LongRecyclerActivity.recyclerViewId)).setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });
        performOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((RecyclerView) getActivity().findViewById(LongRecyclerActivity.recyclerViewId)).setLayoutManager(new GridLayoutManager(getActivity(), GRID_COLUMN_COUNT));
            }
        });
    }

}