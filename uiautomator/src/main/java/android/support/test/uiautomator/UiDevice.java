package android.support.test.uiautomator;

import android.app.Instrumentation;

import java.io.File;

public class UiDevice {

    public static UiDevice getInstance(Instrumentation context) {
        return new UiDevice();
    }

    public UiObject findObject(UiSelector selector) {
        return new UiObject();
    }

    public boolean takeScreenshot(File storePath) {
        return false;
    }
}
