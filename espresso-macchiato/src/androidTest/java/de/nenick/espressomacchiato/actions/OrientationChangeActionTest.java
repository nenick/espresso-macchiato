package de.nenick.espressomacchiato.actions;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

/** Basic test */
public class OrientationChangeActionTest extends EspressoTestCase<BaseActivity> {

    private OrientationChangeAction orientationChangeAction = OrientationChangeAction.orientationLandscape();
    private Activity activityWithUnknownPackage;

    @Test
    public void testPerformFailure() {
        exception.expect(IllegalStateException.class);
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                activityWithUnknownPackage = new Activity() {
                    @Override
                    public String getPackageName() {
                        return "unknown package";
                    }
                };
            }
        });

        orientationChangeAction.hasActivityFixedOrientation(activityWithUnknownPackage);
    }
}