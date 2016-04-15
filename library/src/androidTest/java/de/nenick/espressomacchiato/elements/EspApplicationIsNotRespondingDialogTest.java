package de.nenick.espressomacchiato.elements;

import android.support.v7.app.AlertDialog;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;
import de.nenick.espressomacchiato.tools.EspResourceTool;

public class EspApplicationIsNotRespondingDialogTest extends EspressoTestCase<BaseActivity> {

    @Before
    public void setup() {
        // ui automator only available with android v18, so nothing would close this dialogs
        skipTestIfBelowAndroidMarshmallow();
    }

    @Test
    public void testWithoutDialogs() {
        EspApplicationIsNotRespondingDialog.build().dismissIfShown();
        // expected is that no error is thrown
    }

    @Test
    public void testArn() {
        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setMessage(EspResourceTool.stringResourceByName("anr_process", "TestArn"))
                .setPositiveButton(EspResourceTool.stringResourceByName("wait"), null));
        EspAlertDialog.build().assertIsDisplayedOnScreen();

        EspApplicationIsNotRespondingDialog.build().dismissIfShown();

        EspAlertDialog.build().assertNotExist();
    }

    @Test
    public void testAerr() {
        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setMessage(EspResourceTool.stringResourceByName("aerr_application", "TestAerr"))
                .setPositiveButton(EspResourceTool.stringResourceByName("ok"), null));
        EspAlertDialog.build().assertIsDisplayedOnScreen();

        EspApplicationIsNotRespondingDialog.build().dismissIfShown();

        EspAlertDialog.build().assertNotExist();
    }
}