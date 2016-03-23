package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressotools.EspressoTestCase;

public class EspDialogTest extends EspressoTestCase<BaseActivity> {

    @Test
    @SuppressWarnings("deprecation")
    public void testByIdObsolete() {
        exception.expect(UnsupportedOperationException.class);
        EspDialog.byId(0);
    }
}
