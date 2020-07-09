package androidx.test.uiautomator;

import android.app.Instrumentation;

import java.io.File;

/** Attention only stubbed */
public class UiDevice {

    /**
     * Attention only stubbed
     *
     * @param context Attention only stubbed
     *
     * @return Attention only stubbed
     */
    public static UiDevice getInstance(Instrumentation context) {
        return new UiDevice();
    }

    /**
     * Attention only stubbed
     *
     * @param selector Attention only stubbed
     *
     * @return Attention only stubbed
     */
    public UiObject findObject(UiSelector selector) {
        return new UiObject();
    }

    /**
     * Attention only stubbed
     *
     * @param storePath Attention only stubbed
     *
     * @return Attention only stubbed
     */
    public boolean takeScreenshot(File storePath) {
        return false;
    }
}
