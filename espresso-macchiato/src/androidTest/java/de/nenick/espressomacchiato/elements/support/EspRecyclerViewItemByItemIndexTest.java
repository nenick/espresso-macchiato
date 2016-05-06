package de.nenick.espressomacchiato.elements.support;

import android.support.test.espresso.NoMatchingViewException;

import org.hamcrest.Matchers;
import org.junit.Test;

import de.nenick.espressomacchiato.elements.EspTextView;
import de.nenick.espressomacchiato.test.views.LongRecyclerActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EspRecyclerViewItemByItemIndexTest extends EspressoTestCase<LongRecyclerActivity> {
    private EspRecyclerView espRecyclerView = EspRecyclerView.byId(LongRecyclerActivity.recyclerViewId);

    @Test
    public void testScrollTo() {
        espRecyclerView.itemByItemIndex(LongRecyclerActivity.recyclerViewItemCount - 1).scrollTo();
        espRecyclerView.itemByItemIndex(20).scrollTo();

        EspRecyclerViewItem item = espRecyclerView.itemByVisibleIndex(0);
        EspTextView espTextView = new EspTextView(item.baseMatcherForItemChild(withId(LongRecyclerActivity.itemTextViewId)));
        espTextView.assertTextIs("item: 20");
    }

    @Test
    public void testScrollToFailureWhenLessItems() {
        exception.expect(NoMatchingViewException.class);
        exception.expectMessage("No views in hierarchy found matching: (with id: de.nenick.espressomacchiato.test:id/list and Adapter has minimal <101> items (to access requested index <100>)");
        espRecyclerView.itemByItemIndex(LongRecyclerActivity.recyclerViewItemCount).scrollTo();
    }
}