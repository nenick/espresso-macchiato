package de.nenick.espressomacchiato.viewinteraction

import android.view.View
import de.nenick.espressomacchiato.view.EspView
import de.nenick.espressomacchiato.test.core.AssertionTest
import junit.framework.AssertionFailedError
import org.junit.Test

class VisibilityAssertionsTest : AssertionTest() {

    private lateinit var view: View
    private val viewId = android.R.id.message
    private val espView = object : EspView(viewId), VisibilityAssertions {}

    @Test
    fun checkDoesNotExist() {
        espView.checkDoesNotExist()
    }

    @Test
    fun checkDoesNotExistFailure() {
        givenSimpleView()

        expectedException.expect(AssertionFailedError::class.java)
        expectedException.expectMessage("Expected: is <false>\n     Got: <true>")

        espView.checkDoesNotExist()
    }

    private fun givenSimpleView() {
        view = View(context)
        view.id = viewId
        setViewToRoot(view)
    }
}