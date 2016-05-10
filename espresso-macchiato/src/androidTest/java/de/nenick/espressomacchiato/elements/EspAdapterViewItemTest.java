package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.LongListActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

public class EspAdapterViewItemTest extends EspressoTestCase<LongListActivity> {

    @Test
    public void testForCoverage() {
        EspAdapterViewItem.Mode.valueOf("byItemIndex");
        EspAdapterViewItem.Mode.values();

    }

    @Test
    @SuppressWarnings("deprecation")
    public void testByAllNotSupported() {
        exception.expect(UnsupportedOperationException.class);
        EspAdapterViewItem.byAll();
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testByIdNotSupported() {
        exception.expect(UnsupportedOperationException.class);
        EspAdapterViewItem.byId(0);
    }
}