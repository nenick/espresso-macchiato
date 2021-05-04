package de.nenick.espressomacchiato.test.core

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import de.nenick.espressomacchiato.screenshot.EspScreenshotRule
import org.junit.After
import org.junit.Rule
import org.junit.rules.RuleChain

abstract class BaseActivityTest<TEST_ACTIVITY : Activity> {

    abstract val activityScenarioRule: ActivityScenarioRule<TEST_ACTIVITY>
    private val screenshotRule = EspScreenshotRule()

    @get:Rule
    val chain by lazy {
        RuleChain.emptyRuleChain()
            .around(activityScenarioRule)
            .around(screenshotRule)!!
    }

    val context = InstrumentationRegistry.getInstrumentation().targetContext!!

    @After
    fun baseTearDown() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2) {
            // Would otherwise randomly crash the instrumentation test run.
            SecurityExceptionFix.apply()
        }
    }

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