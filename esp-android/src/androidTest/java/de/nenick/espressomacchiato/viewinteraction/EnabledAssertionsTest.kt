package de.nenick.espressomacchiato.viewinteraction

import android.widget.Button
import de.nenick.espressomacchiato.test.core.AssertionTest
import de.nenick.espressomacchiato.test.core.BaseActivity
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
        addViewToRoot(statefulView)
    }
}