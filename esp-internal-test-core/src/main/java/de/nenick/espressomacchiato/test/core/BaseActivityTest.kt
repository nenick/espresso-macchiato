package de.nenick.espressomacchiato.test.core

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry

abstract class BaseActivityTest<TEST_ACTIVITY : Activity> {

    abstract val activityScenarioRule: ActivityScenarioRule<TEST_ACTIVITY>

    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    val context = InstrumentationRegistry.getInstrumentation().targetContext!!

    fun addViewToRoot(view: View) {
        addViewToLayout(view, BaseActivity.rootLayout)
    }

    private fun addViewToLayout(view: View, @IdRes targetLayoutId: Int) {
        activityScenarioRule.scenario.onActivity {
            val layout = it.findViewById<ViewGroup>(targetLayoutId)
            layout.addView(view)
        }
    }

    fun runOnMainSync(runner: (activity: Activity) -> Unit) {
        activityScenarioRule.scenario.onActivity { runner(it) }
    }
}