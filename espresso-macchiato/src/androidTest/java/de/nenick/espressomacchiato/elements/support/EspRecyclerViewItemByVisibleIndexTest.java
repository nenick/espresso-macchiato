package de.nenick.espressomacchiato.elements.support;

import org.junit.Test;

import de.nenick.espressomacchiato.elements.EspTextView;
import de.nenick.espressomacchiato.test.views.LongRecyclerActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EspRecyclerViewItemByVisibleIndexTest extends EspressoTestCase<LongRecyclerActivity> {
    private EspRecyclerView espRecyclerView = EspRecyclerView.byId(LongRecyclerActivity.recyclerViewId);

    @Test
    public void testItemChildMatcher() {
        EspRecyclerViewItem item = espRecyclerView.itemByVisibleIndex(0);
        EspTextView espTextView = new EspTextView(item.baseMatcherForItemChild(withId(LongRecyclerActivity.itemTextViewId)));
        espTextView.assertTextIs("item: 0");
    }

    @Test
    public void testScrollToFailureNotSupported() {
        exception.expect(UnsupportedOperationException.class);
        exception.expectMessage("Method only supported when item accessed byItemIndex");

        espRecyclerView.itemByVisibleIndex(20).scrollTo();
    }
}