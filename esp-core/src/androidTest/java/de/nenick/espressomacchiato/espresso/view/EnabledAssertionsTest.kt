package de.nenick.espressomacchiato.espresso.view

import android.widget.Button
import androidx.test.platform.app.InstrumentationRegistry
import de.nenick.espressomacchiato.testtools.AssertionTest
import de.nenick.espressomacchiato.testtools.BaseActivity
import de.nenick.espressomacchiato.view.EspView
import junit.framework.AssertionFailedError
import org.junit.Test

class EnabledAssertionsTest : AssertionTest() {

    private lateinit var statefulView: Button
    private val statefulViewId = android.R.id.primary
    private val espStatefulView = object : EspView(statefulViewId), EnabledAssertions {}

    @Test
    fun checkIsEnabled() {
        givenStatefulView()
        espStatefulView.checkIsEnabled()

        runOnMainSync { statefulView.isEnabled = false }

        expectedException.expect(AssertionFailedError::class.java)
        expectedException.expectMessage("'is enabled' doesn't match the selected view")
        espStatefulView.checkIsEnabled()
    }

    @Test
    fun checkIsDisabled() {
        givenStatefulView()
        runOnMainSync { statefulView.isEnabled = false }
        espStatefulView.checkIsDisabled()

        runOnMainSync { statefulView.isEnabled = true }

        expectedException.expect(AssertionFailedError::class.java)
        expectedException.expectMessage("'not is enabled' doesn't match the selected view")
        espStatefulView.checkIsDisabled()
    }

    private fun givenStatefulView() {
        statefulView = Button(context)
        statefulView.id = statefulViewId
        statefulView.text = "click me"
        replaceViewInLayout(statefulView, BaseActivity.rootLayout)
    }
}