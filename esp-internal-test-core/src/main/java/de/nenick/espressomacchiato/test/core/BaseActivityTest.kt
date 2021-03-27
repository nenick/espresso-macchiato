package de.nenick.espressomacchiato.test.core

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import de.nenick.espressomacchiato.test.core.BaseActivity
import org.junit.Rule

abstract class BaseActivityTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<BaseActivity>()

    private val instrumentation = InstrumentationRegistry.getInstrumentation()
    val context = InstrumentationRegistry.getInstrumentation().context!!

    fun setViewToRoot(view: View) {
        replaceViewInLayout(view, BaseActivity.rootLayout)
    }

    fun replaceViewInLayout(view: View, @IdRes targetLayoutId: Int) {
        activityScenarioRule.scenario.onActivity {
            val layout = it.findViewById<ViewGroup>(targetLayoutId)
            layout.removeAllViews()
            layout.addView(view)
        }
    }

    fun runOnMainSync(runner: (activity: Activity) -> Unit) {
        activityScenarioRule.scenario.onActivity { runner(it) }
    }
}