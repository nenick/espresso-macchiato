package de.nenick.espressomacchiato.elements.support;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LongRecyclerActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

/** Basic tests */
public class EspRecyclerViewItemTest extends EspressoTestCase<LongRecyclerActivity> {

    @Test
    public void testForCoverage() {
        EspRecyclerViewItem.Mode.valueOf("byItemIndex");
        EspRecyclerViewItem.Mode.values();
    }

    @Test
    public void testTemplateConstructor() {
        MyEspRecyclerViewItem myEspRecyclerViewItem = new MyEspRecyclerViewItem(EspRecyclerViewItem.byItemIndex(LongRecyclerActivity.recyclerViewId, 0));
        myEspRecyclerViewItem.assertIsDisplayedOnScreen();
    }

    class MyEspRecyclerViewItem extends EspRecyclerViewItem {

        public MyEspRecyclerViewItem(EspRecyclerViewItem template) {
            super(template);
        }
    }
}