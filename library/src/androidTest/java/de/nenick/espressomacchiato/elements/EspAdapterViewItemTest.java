package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LongListActivity;
import de.nenick.espressotools.EspressoTestCase;

public class EspAdapterViewItemTest extends EspressoTestCase<LongListActivity> {

    private EspAdapterViewItem espAdapterViewItem = EspAdapterViewItem.byText(LongListActivity.lastListItemText, LongListActivity.dataSourceTextColumn);
    private EspTextView espTextView = EspTextView.byId(LongListActivity.selectedRotTextViewId);

    @Test
    public void testAssertListItemIsNotShown() {
        espAdapterViewItem.assertNotExist();
    }

    @Test
    public void testScrollTo() {
        espAdapterViewItem.scrollTo();
        espAdapterViewItem.assertIsVisible();
    }

    @Test
    public void testClickRow() {
        espAdapterViewItem.scrollTo();
        espAdapterViewItem.click();
        espTextView.assertTextIs(String.valueOf(LongListActivity.lastListItemId));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testByIdObsolete() {
        exception.expect(UnsupportedOperationException.class);
        EspListViewItem.byId(0);
    }
}