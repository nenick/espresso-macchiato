package de.nenick.espressomacchiato.tools;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

/** Basic test */
public class EspWaitTest extends EspressoTestCase<BaseActivity> {

    @Before
    public void setup() {
        // just for coverage
        new EspWait();
    }

    @Test
    public void testForDelayInterrupt() throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EspWait.forDelay(30000);
                } catch (IllegalStateException e) {
                    // avoid application crash which would abort test run
                }
            }
        });
        thread.start();
        EspWait.forDelay(500);
        thread.interrupt();
    }
}