package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LongListActivity;
import de.nenick.espressotools.EspressoTestCase;

public class EspListViewItemTest extends EspressoTestCase<LongListActivity> {

    private EspListViewItem espListViewItem = EspListViewItem.byText(LongListActivity.lastListItemText, LongListActivity.dataSourceTextColumn);

    @Test
    public void testByText() {
        espListViewItem.scrollTo();
        espListViewItem.assertIsVisible();
    }
}