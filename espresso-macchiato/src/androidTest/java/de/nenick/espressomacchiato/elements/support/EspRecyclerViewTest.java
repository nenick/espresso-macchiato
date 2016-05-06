package de.nenick.espressomacchiato.elements.support;

import org.junit.Test;

import de.nenick.espressomacchiato.elements.EspTextView;
import de.nenick.espressomacchiato.test.views.LongRecyclerActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

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
    public void testItemByVisibleIndex() {
        espRecyclerView.itemByVisibleIndex(0).assertIsDisplayedOnScreen();
    }

    @Test
    public void testItemByIndex() {
        espRecyclerView.itemByItemIndex(0).assertIsDisplayedOnScreen();
    }

    class MyEspRecyclerView extends EspRecyclerView {
        public MyEspRecyclerView(EspRecyclerView template) {
            super(template);
        }
    }
}