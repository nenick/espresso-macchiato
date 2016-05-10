package de.nenick.espressomacchiato.elements;

import junit.framework.AssertionFailedError;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LongListActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

public class EspAdapterViewItemByVisibleIndexTest extends EspressoTestCase<LongListActivity> {

    private EspAdapterView espAdapterView = EspAdapterView.byId(LongListActivity.listViewId);
    private EspTextView espTextView = EspTextView.byId(LongListActivity.selectedRotTextViewId);

    @Test
    public void testItemByVisibleIndexFailureLessItems() {
        exception.expect(AssertionFailedError.class);
        exception.expectMessage("Requested child at index 20 but adapter view has only ");
        exception.expectMessage(" visible childs.");

        espAdapterView.itemByVisibleIndex(20).assertIsDisplayedOnScreen();
    }


    @Test
    public void testScrollToFailureNotSupported() {
        exception.expect(UnsupportedOperationException.class);
        exception.expectMessage("Method only supported when item accessed byItemIndex");

        espAdapterView.itemByVisibleIndex(20).scrollTo();
    }

    @Test
    public void testAssertIsDisplayed() {
        espAdapterView.itemByVisibleIndex(0).assertIsDisplayedOnScreen();
    }

    @Test
    public void testClickRow() {
        espAdapterView.itemByVisibleIndex(0).click();
        espTextView.assertTextIs(String.valueOf(0));
    }
}