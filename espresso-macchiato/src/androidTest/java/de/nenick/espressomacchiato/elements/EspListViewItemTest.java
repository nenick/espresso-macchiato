package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LongListActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

/** Basic tests */
public class EspListViewItemTest extends EspressoTestCase<LongListActivity> {

    @Test
    @SuppressWarnings({"unused", "UnusedAssignment"})
    public void testJustForCoverage() {
        EspListViewItem espListViewItem;
        espListViewItem = EspListViewItem.byItemIndex(0, 0);
        espListViewItem = EspListViewItem.byVisibleIndex(0, 0);
        new MyEspListViewItem(EspAdapterViewItem.byItemIndex(0, 0));
    }

    /** Element extension */
    class MyEspListViewItem extends EspListViewItem {
        public MyEspListViewItem(EspAdapterViewItem template) {
            super(template);
        }
    }
}