package de.nenick.espressomacchiato.elements;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static org.junit.Assert.assertNotNull;

public class EspDialogTest extends EspressoTestCase<BaseActivity> {

    @Test
    @SuppressWarnings("deprecation")
    public void testByIdObsolete() {
        exception.expect(UnsupportedOperationException.class);
        EspDialog.byId(0);
    }

    @Test
    public void testBuild() {
        EspDialog espDialog = EspDialog.spec().build();
        assertNotNull(espDialog);
    }
}
