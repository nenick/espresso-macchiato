package de.nenick.espressomacchiato.testtools

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test

abstract class BaseActivityTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<BaseActivity>()

    val context = InstrumentationRegistry.getInstrumentation().context!!

    fun addViewToRoot(view: View) { addViewToLayout(view, BaseActivity.rootLayout) }

    fun addViewToLayout(view: View, @IdRes targetLayoutId: Int) {
        activityScenarioRule.scenario.onActivity {
            val layout = it.findViewById<ViewGroup>(targetLayoutId)
            layout.addView(view)
        }
    }
}