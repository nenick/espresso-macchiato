package com.example.esp_system_dialogs;

import android.app.Activity;

import org.junit.Test;

import de.nenick.espressomacchiato.testbase.EspressoTestBase;

/** Basic tests */
public class EspSystemDialogTest extends EspressoTestBase {

    @Override
    public Activity getActivity() {
        return null;
    }

    @Test
    public void testMissingUiAutomator() {
        EspSystemDialog espSystemDialog = new EspSystemDialog() {
            @Override
            protected void throwIfUiAutomatorNotExist() throws ClassNotFoundException {
                throw new ClassNotFoundException();
            }

            @Override
            protected void dismissIfShownInternal() {
                throw new UnsupportedOperationException();
            }
        };

        espSystemDialog.dismissIfShown();
        // should not fail
    }
}