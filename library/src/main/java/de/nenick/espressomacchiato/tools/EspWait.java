package de.nenick.espressomacchiato.tools;

import android.support.test.InstrumentationRegistry;

public class EspWait {

    public static void forDelay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void forIdle() {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }
}
