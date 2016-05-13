package de.nenick.espressomacchiato.elements;

import android.app.Activity;

import org.junit.Test;

import de.nenick.espressomacchiato.testbase.EspressoTestBase;

public class EspSystemDialogTest extends EspressoTestBase {

    @Override
    public Activity getActivity() {
        return null;
    }

    @Test
    public void testMissingUiAutomator() {
        EspSystemDialog espSystemDialog = new EspSystemDialog() {
            @Override
            protected void dismissIfShownInternal() {
                throw new UnsupportedOperationException();
            }
        };

        espSystemDialog.dismissIfShown();
        // should not fail
    }
}