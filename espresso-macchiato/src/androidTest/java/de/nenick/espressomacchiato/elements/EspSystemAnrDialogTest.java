package de.nenick.espressomacchiato.elements;

import android.app.AlertDialog;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;
import de.nenick.espressomacchiato.tools.EspResourceTool;

/** Basic tests */
public class EspSystemAnrDialogTest extends EspressoTestCase<BaseActivity> {

    @Before
    public void setup() {
        // ui automator only available with android v18, so nothing would close this dialogs
        skipTestIfBelowAndroidMarshmallow();
    }

    @Test
    public void testWithoutDialogs() {
        EspSystemAnrDialog.build().dismissIfShown();
        // expected is that no error is thrown
    }

    @Test
    public void testArn() {
        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setMessage(EspResourceTool.stringResourceByName("anr_process", "TestArn"))
                .setPositiveButton(EspResourceTool.stringResourceByName("wait"), null));
        EspAlertDialog.build().assertIsDisplayedOnScreen();

        EspSystemAnrDialog.build().dismissIfShown();

        EspAlertDialog.build().assertNotExist();
    }
}