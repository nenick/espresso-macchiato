package de.nenick.espressomacchiato.elements;

import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

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

            click(getStringResourceByName("wait"));
            click(getStringResourceByName("ok")); // sometimes a system process does crash on emulator and this must be confirmed
        }
    }

    protected void click(String target) {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject button = device.findObject(new UiSelector().text(target));

        if(button.exists()) {
            try {
                button.click();
            } catch (UiObjectNotFoundException e) {
                // already previously checked for exist
                throw new IllegalStateException(e);
            }
        }
    }

    private String getStringResourceByName(String name) {
        int resId = InstrumentationRegistry.getContext().getResources().getIdentifier(name, "string", "android");
        return InstrumentationRegistry.getContext().getString(resId);
    }
}
