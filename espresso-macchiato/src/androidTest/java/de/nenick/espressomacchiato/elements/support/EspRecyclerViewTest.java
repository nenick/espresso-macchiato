package de.nenick.espressomacchiato.elements.support;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LongRecyclerActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

/** Basic tests */
public class EspRecyclerViewTest extends EspressoTestCase<LongRecyclerActivity> {
    private EspRecyclerView espRecyclerView = EspRecyclerView.byId(LongRecyclerActivity.recyclerViewId);

    @Test
    public void testById() {
        espRecyclerView.assertIsVisible();
    }

    @Test
    public void testAssertItemCountIs() {
        espRecyclerView.assertItemCountIs(LongRecyclerActivity.recyclerViewItemCount);
    }

    @Test
    public void testTemplateConstructor() {
        MyEspRecyclerView myEspRecyclerView = new MyEspRecyclerView(EspRecyclerView.byId(LongRecyclerActivity.recyclerViewId));
        myEspRecyclerView.assertIsDisplayedOnScreen();
    }

    @Test
    public void testItemByIndex() {
        espRecyclerView.itemByIndex(0).assertIsDisplayedOnScreen();
    }

    /** Element extension */
    class MyEspRecyclerView extends EspRecyclerView {
        public MyEspRecyclerView(EspRecyclerView template) {
            super(template);
        }
    }
}