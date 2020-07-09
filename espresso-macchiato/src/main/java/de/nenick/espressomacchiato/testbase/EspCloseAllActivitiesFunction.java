package de.nenick.espressomacchiato.testbase;

import android.app.Activity;
import android.app.Instrumentation;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Avoid the "Could not launch intent Intent within 45 seconds" error.
 * <p>
 * source: https://code.google.com/p/android-test-kit/issues/detail?id=66
 */
public class EspCloseAllActivitiesFunction {

    public static void apply(Instrumentation instrumentation) throws Exception {
        final int NUMBER_OF_RETRIES = 100;
        int i = 0;
        while (closeActivities(instrumentation)) {
            if (i++ > NUMBER_OF_RETRIES) {
                Log.w("EspressoMacchiato", "Not all activities are finished.");
                break;
            }
            Thread.sleep(200);
        }
    }

    public static Set<Activity> getActivitiesInStages(Stage... stages) {
        final Set<Activity> activities = new HashSet<>();
        final ActivityLifecycleMonitor instance = ActivityLifecycleMonitorRegistry.getInstance();
        for (Stage stage : stages) {
            activities.addAll(instance.getActivitiesInStage(stage));
        }
        return activities;
    }

    private static boolean closeActivities(Instrumentation instrumentation) throws Exception {
        final AtomicReference<Boolean> activityClosed = new AtomicReference<>();
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                /* Activities in android v8 never get destroyed, only stay in Stage.STOPPED ,*/
                final Set<Activity> activities = getActivitiesInStages(Stage.RESUMED, Stage.STARTED, Stage.PAUSED, Stage.CREATED);
                activities.removeAll(getActivitiesInStages(Stage.DESTROYED));

                if (activities.size() > 0) {
                    for (Activity activity : activities) {
                        if (activity.isFinishing()) {
                            Log.i("espressotools", "activity in finishing state " + activity);
                        } else {
                            Log.i("espressotools", "activity not finished " + activity);
                            activity.finish();
                        }
                    }
                    activityClosed.set(true);
                } else {
                    activityClosed.set(false);
                }
            }
        });

        if (activityClosed.get()) {
            instrumentation.waitForIdleSync();
        }
        return activityClosed.get();
    }
}
