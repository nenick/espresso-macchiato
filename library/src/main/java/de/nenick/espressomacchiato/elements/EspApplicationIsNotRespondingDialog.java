package de.nenick.espressomacchiato.elements;

import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import de.nenick.espressomacchiato.tools.EspResourceTool;

public class EspApplicationIsNotRespondingDialog {

    public static EspApplicationIsNotRespondingDialog build() {
        return new EspApplicationIsNotRespondingDialog();
    }

    public void dismissIfShown() {
        // uiautomator is only available since android v18
        // early android version emulator rarely show ANR dialog
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {

            try {
                Class.forName("android.support.test.uiautomator.UiDevice");
            } catch (ClassNotFoundException e) {
                Log.w("EspressoMacchiato", "Missing uiautomator classes to dismiss possible ANR dialog.");
                return;
            }

            if (dialogIsShownWith(EspResourceTool.stringResourceByName("anr_process", ".*").replace("?", "\\?"))) {
                click(EspResourceTool.stringResourceByName("wait")); // sometimes a system process isn't responding on emulator and this must be confirmed
            }

            if(dialogIsShownWith(EspResourceTool.stringResourceByName("aerr_application", ".*"))) {
                click(EspResourceTool.stringResourceByName("ok")); // sometimes a system process does crash on emulator and this must be confirmed
            }
        }
    }

    private boolean dialogIsShownWith(String expectedMessage) {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject dialog = device.findObject(new UiSelector().textMatches(expectedMessage));
        return dialog.exists();
    }

    private void click(String target) {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject button = device.findObject(new UiSelector().text(target));

        try {
            button.click();
        } catch (UiObjectNotFoundException e) {
            // already previously checked for exist
            throw new IllegalStateException(e);
        }
    }
}
