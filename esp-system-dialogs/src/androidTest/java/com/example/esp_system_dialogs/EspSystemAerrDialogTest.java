package com.example.esp_system_dialogs;

import android.app.AlertDialog;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;
import de.nenick.espressomacchiato.tools.EspResourceTool;

/** Basic tests */
public class EspSystemAerrDialogTest extends EspressoTestCase<BaseActivity> {

    @Before
    public void setup() {
        // ui automator only available with android v18, so nothing would close this dialogs
        skipTestIfBelowAndroidMarshmallow();
    }

    @Test
    public void testWithoutDialogs() {
        EspSystemAerrDialog.build().dismissIfShown();
        // expected is that no error is thrown
    }

    @Test
    public void testWrongButtonText() {
        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setMessage(EspResourceTool.stringResourceByName("aerr_application", "TestAerr"))
                .setPositiveButton("wrong", null));

        exception.expect(IllegalStateException.class);
        EspSystemAerrDialog.build().dismissIfShown();
    }

    @Test
    public void testAerr() {
        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setMessage(EspResourceTool.stringResourceByName("aerr_application", "TestAerr"))
                .setPositiveButton(EspResourceTool.stringResourceByName("ok"), null));
        EspAlertDialog.build().assertIsDisplayedOnScreen();

        EspSystemAerrDialog.build().dismissIfShown();
        EspAlertDialog.build().assertNotExist();
    }
}