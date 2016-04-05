package de.nenick.espressomacchiato.elements;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

public class EspApplicationIsNotRespondingDialog {

    public static EspApplicationIsNotRespondingDialog build() {
        return new EspApplicationIsNotRespondingDialog();
    }

    public void dismissIfShown() {
        click(getStringResourceByName("wait"));
    }

    protected void click(String target) {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject button = device.findObject(new UiSelector().text(target));

        try {
            button.click();
        } catch (UiObjectNotFoundException e) {
            // just ignore when anr dialog is not shown
        }
    }

    private String getStringResourceByName(String name) {
        int resId = InstrumentationRegistry.getContext().getResources().getIdentifier(name, "string", "android");
        return InstrumentationRegistry.getContext().getString(resId);
    }
}
