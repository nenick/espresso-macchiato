package androidx.test.core.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import de.nenick.espressomacchiato.internals.SimpleTimer

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

        private const val FINISH_BOOTSTRAP_ACTIVITY =
            "androidx.test.core.app.InstrumentationActivityInvoker.FINISH_BOOTSTRAP_ACTIVITY"
    }

    // TODO Check if BootstrapActivity could also be skipped.

    override fun finishActivity(activity: Activity) {
        // Would start an EmptyActivity to ensure other activities are finish callback is called.
        // We should only activate this documented fix where we really have it. Start/Stop EmptyActivity
        // has a huge performance impact on mostly all android versions.
        // Perhaps with new espresso release this will be obsolete. https://github.com/android/android-test/issues/411
        // super.finishActivity(activity)

        // Origin lines without the EmptyActivity fix. Since doc say not necessary after api 19.
        getInstrumentation().runOnMainSync { activity.finish() }
        getApplicationContext<Context>().sendBroadcast(Intent(FINISH_BOOTSTRAP_ACTIVITY))

        // But now on android 23 / 24 it this issue happens more often and have to enforce wait activity is finished.
        // java.lang.AssertionError: Activity never becomes requested state "[DESTROYED]" (last lifecycle transition = "PAUSED")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            waitForActivityDestroyed(activity)
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun waitForActivityDestroyed(activity: Activity) {
        val timer = SimpleTimer(1000)
        while (!activity.isDestroyed) {

            if(timer.isTimeout()) {
                // Perhaps the origin finish fix will do his job.
                super.finishActivity(activity)
                break
            }

            if (activity.isFinishing) {
                Thread.sleep(20)
            } else {
                getInstrumentation().runOnMainSync { activity.finish() }
            }
        }
    }
}