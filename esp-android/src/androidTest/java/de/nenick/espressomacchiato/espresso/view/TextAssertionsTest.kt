package de.nenick.espressomacchiato.espresso.view

import android.widget.TextView
import de.nenick.espressomacchiato.view.EspView
import de.nenick.espressomacchiato.test.R
import de.nenick.espressomacchiato.testtools.BaseActivity
import de.nenick.espressomacchiato.testtools.BaseActivityTest
import org.junit.Test

class TextAssertionsTest : BaseActivityTest() {

    private val defaultMessage = context.getString(R.string.name)

    private lateinit var messageView: TextView
    private val messageViewId = android.R.id.text1
    private val espMessageView = object : EspView(messageViewId), TextAssertions {}

    @Test
    fun checkTestByString() {
        givenTextView()
        espMessageView.checkText(defaultMessage)
    }

    @Test
    fun checkTestById() {
        givenTextView()
        espMessageView.checkText(R.string.name)
    }

    private fun givenTextView() {
        messageView = TextView(context)
        messageView.id = messageViewId
        messageView.text = defaultMessage
        replaceViewInLayout(messageView, BaseActivity.rootLayout)
    }
}