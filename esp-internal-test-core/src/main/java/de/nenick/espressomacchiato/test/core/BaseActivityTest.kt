package de.nenick.espressomacchiato.test.core

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule

abstract class BaseActivityTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<BaseActivity>()

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