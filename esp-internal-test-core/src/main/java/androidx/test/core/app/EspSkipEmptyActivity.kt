package androidx.test.core.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.runner.lifecycle.ActivityLifecycleCallback
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import de.nenick.espressomacchiato.internals.SimpleTimer
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

internal class EspSkipEmptyActivity : InstrumentationActivityInvoker() {

    companion object {
        fun apply(target: ActivityScenarioRule<*>) {
            val scenarioField = target.javaClass.getDeclaredField("scenario")
            scenarioField.isAccessible = true
            val scenario = scenarioField.get(target)
            val activityInvokerField = scenario.javaClass.getDeclaredField("activityInvoker")
            activityInvokerField.isAccessible = true
            activityInvokerField.set(scenario, EspSkipEmptyActivity())
        }

        private const val FINISH_EMPTY_ACTIVITIES =
            "androidx.test.core.app.InstrumentationActivityInvoker.FINISH_EMPTY_ACTIVITIES"
    }

    override fun finishActivity(activity: Activity) {
        // Super.finishActivity() would start an EmptyActivity to ensure that activity onFinish
        // callback is called. We should only activate this documented fix where we really need it.
        // Start/Stop EmptyActivity has a huge performance impact on mostly all android versions.
        // Perhaps with new espresso release this will be obsolete. https://github.com/android/android-test/issues/411

        getInstrumentation().runOnMainSync { activity.finish() }

        // But now on some android version this issue happens more often and have to enforce wait for activity is finished.
        // java.lang.AssertionError: Activity never becomes requested state "[DESTROYED]" (last lifecycle transition = "PAUSED")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            ensureActivityGetsDestroyed(activity)
        }
    }

    /** A lock that is used to block the main thread until the Activity becomes a requested state.  */
    private val lock = ReentrantLock()

    /** A condition object to be notified when the activity state changes.  */
    private val stateChangedCondition = lock.newCondition()
    private lateinit var finishActivity: Activity

    private val activityLifecycleObserver = ActivityLifecycleCallback { activity: Activity, stage: Stage ->
        if(activity == finishActivity && stage == Stage.DESTROYED) {
            lock.lock()
            try {
                stateChangedCondition.signal()
            } finally {
                lock.unlock()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun ensureActivityGetsDestroyed(activity: Activity) {
        lock.lock()
        try {
            finishActivity = activity
            ActivityLifecycleMonitorRegistry.getInstance().addLifecycleCallback(activityLifecycleObserver)
            stateChangedCondition.await(1000, TimeUnit.MILLISECONDS)
        } finally {
            ActivityLifecycleMonitorRegistry.getInstance().removeLifecycleCallback(activityLifecycleObserver)
            lock.unlock();
        }

        var timerUseWorkaround = SimpleTimer(1000)
        val timer = SimpleTimer(3000)
        while (!activity.isDestroyed) {
            if (timer.isTimeout()) {
                // Perhaps the origin finish fix will do his job.
                Log.w("EspressoMacchiato", "$activity didn't finish yet. Call origin EmptyActivity workaround.")
                super.finishActivity(activity)
                break
            }

            if (timerUseWorkaround.isTimeout()) {
                Log.w("EspressoMacchiato", "$activity didn't finish yet. Use EmptyActivity workaround.")
                // Mostly that what super.finishActivity() would do.
                // TODO Calling stopActivity is just a workaround to call startEmptyActivitySync().
                //      This could fail if activity just becomes destroyed. Perhaps call it through
                //      reflection then to avoid race condition. It's better to start the EmptyActivity
                //      instead of producing false test failures.
                stopActivity(activity)
                ApplicationProvider.getApplicationContext<Context>().sendBroadcast(Intent(FINISH_EMPTY_ACTIVITIES))
                timerUseWorkaround = SimpleTimer(1000)
            }

            Thread.sleep(50)
        }
    }
}