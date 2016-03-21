package de.nenick.espressomacchiato.elements;

import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

public class EspPermissionDialog {

    public static EspPermissionDialog build() {
        return new EspPermissionDialog();
    }

    public void allow() {
        click("Allow");
    }

    public void deny() {
        click("Deny");
    }

    protected void click(String target) {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
            UiObject button = device.findObject(new UiSelector().text(target));
            if (button.exists()) {
                try {
                    button.click();
                } catch (UiObjectNotFoundException e) {
                    throw new IllegalStateException(e);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
