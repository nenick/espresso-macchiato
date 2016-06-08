package android.support.test.uiautomator;

import android.app.Instrumentation;

import java.io.File;

/** Attention only stubbed */
public class UiDevice {

    /** Attention only stubbed */
    public static UiDevice getInstance(Instrumentation context) {
        return new UiDevice();
    }

    /** Attention only stubbed */
    public UiObject findObject(UiSelector selector) {
        return new UiObject();
    }

    /** Attention only stubbed */
    public boolean takeScreenshot(File storePath) {
        return false;
    }
}
