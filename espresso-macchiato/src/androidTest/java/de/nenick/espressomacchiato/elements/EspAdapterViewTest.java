package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LongListActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

public class EspAdapterViewTest extends EspressoTestCase<LongListActivity> {

    private EspAdapterView espAdapterView = EspAdapterView.byId(LongListActivity.listViewId);

    @Test
    public void testById() {
        espAdapterView.assertIsVisible();
    }

    @Test
    public void testCustomBaseMatcher() {
        //espAdapterView = new EspAdapterView(allOf(withId(LongListActivity.listViewId), isDisplayed()));
        espAdapterView.assertIsVisible();
    }

    @Test
    public void testAssertItemCountIs() {
        espAdapterView.assertItemCountIs(LongListActivity.listViewItemCount);
    }

    @Test
    public void testItemByVisibleIndex() {
        espAdapterView.itemByVisibleIndex(0).assertIsDisplayedOnScreen();
    }
}