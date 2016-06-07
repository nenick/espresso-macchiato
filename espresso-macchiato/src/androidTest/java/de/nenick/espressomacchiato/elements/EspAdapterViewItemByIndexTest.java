package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LongListActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

/** Basic tests */
public class EspAdapterViewItemByIndexTest extends EspressoTestCase<LongListActivity> {

    private EspAdapterView espAdapterView = EspAdapterView.byId(LongListActivity.listViewId);

    @Test
    public void testScrollTo() {
        espAdapterView.itemByIndex(99).scrollTo();
        espAdapterView.itemByIndex(20).scrollTo();

        EspTextView espTextView = new EspTextView(espAdapterView.itemByVisibleIndex(0).baseMatcherForItemChild(withId(LongListActivity.itemTextViewId)));
        espTextView.assertTextIs("item: 20");
    }

    @Test
    public void testBaseMatcherForItemChildNotSupported() {
        exception.expect(UnsupportedOperationException.class);
        exception.expectMessage("Method only supported when item accessed byVisibleIndex");
        espAdapterView.itemByIndex(0).baseMatcherForItemChild(withId(LongListActivity.itemTextViewId));
    }
}