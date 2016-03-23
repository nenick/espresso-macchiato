package de.nenick.espressomacchiato.tools;

public class EspWait {

    public static void forDelay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
