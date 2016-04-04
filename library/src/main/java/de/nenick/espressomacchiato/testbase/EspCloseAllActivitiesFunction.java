package de.nenick.espressomacchiato.testbase;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.core.deps.guava.base.Throwables;
import android.support.test.espresso.core.deps.guava.collect.Sets;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitor;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.util.Log;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Callable;
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
        while (closeActivity(instrumentation)) {
            if (i++ > NUMBER_OF_RETRIES) {
                throw new AssertionError("Limit of retries excesses");
            }
            Thread.sleep(200);
        }
    }

    private static <X> X callOnMainSync(Instrumentation instrumentation, final Callable<X> callable) throws Exception {
        final AtomicReference<X> retAtomic = new AtomicReference<>();
        final AtomicReference<Throwable> exceptionAtomic = new AtomicReference<>();
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                try {
                    retAtomic.set(callable.call());
                } catch (Throwable e) {
                    exceptionAtomic.set(e);
                }
            }
        });
        final Throwable exception = exceptionAtomic.get();
        if (exception != null) {
            Throwables.propagateIfInstanceOf(exception, Exception.class);
            throw Throwables.propagate(exception);
        }
        return retAtomic.get();
    }

    private static Set<Activity> getActivitiesInStages(Stage... stages) {
        final Set<Activity> activities = Sets.newHashSet();
        final ActivityLifecycleMonitor instance = ActivityLifecycleMonitorRegistry.getInstance();
        for (Stage stage : stages) {
            final Collection<Activity> activitiesInStage = instance.getActivitiesInStage(stage);
            if (activitiesInStage != null) {
                activities.addAll(activitiesInStage);
            }
        }
        return activities;
    }

    private static boolean closeActivity(Instrumentation instrumentation) throws Exception {
        final Boolean activityClosed = callOnMainSync(instrumentation, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final Set<Activity> activities = getActivitiesInStages(Stage.RESUMED,
                        Stage.STARTED, Stage.PAUSED,
                        /* Activities in android v8 never reach destroyed after calling finish Stage.STOPPED ,*/
                        Stage.CREATED);
                activities.removeAll(getActivitiesInStages(Stage.DESTROYED));
                if (activities.size() > 0) {
                    final Activity activity = activities.iterator().next();
                    if (!activity.isFinishing()) {
                        Log.i("espressotools", "activity not finished " + activity);
                        activity.finish();
                    } else {
                        Log.i("espressotools", "activity in finishing state " + activity);
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });
        if (activityClosed) {
            instrumentation.waitForIdleSync();
        }
        return activityClosed;
    }
}
