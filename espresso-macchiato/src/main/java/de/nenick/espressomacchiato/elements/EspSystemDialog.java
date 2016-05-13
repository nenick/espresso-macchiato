package de.nenick.espressomacchiato.elements;

import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

public abstract class EspSystemDialog {

    public void dismissIfShown() {
        // uiautomator is only available since android v18
        // early android version emulator rarely show ANR dialog
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {

            try {
                throwIfUiAutomatorNotExist();
            } catch (ClassNotFoundException e) {
                Log.w("EspressoMacchiato", "Missing uiautomator classes to dismiss possible ANR dialog.");
                return;
            }

            dismissIfShownInternal();
        }
    }

    protected void throwIfUiAutomatorNotExist() throws ClassNotFoundException {
        Class.forName("android.support.test.uiautomator.UiDevice");
    }

    protected abstract void dismissIfShownInternal();

    protected boolean dialogIsShownWith(String expectedMessage) {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject dialog = device.findObject(new UiSelector().textMatches(expectedMessage));
        return dialog.exists();
    }

    protected void click(String target) {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject button = device.findObject(new UiSelector().text(target));

        try {
            button.click();
        } catch (UiObjectNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
