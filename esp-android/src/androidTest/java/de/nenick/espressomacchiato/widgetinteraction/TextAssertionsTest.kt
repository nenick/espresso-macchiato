package de.nenick.espressomacchiato.widgetinteraction

import android.widget.TextView
import de.nenick.espressomacchiato.test.core.DefaultInteractionTest
import de.nenick.espressomacchiato.view.EspView
import org.junit.Test

class TextAssertionsTest : DefaultInteractionTest() {

    private val defaultMessage = "Hello"

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
        espMessageView.checkText(defaultMessage)
    }

    private fun givenTextView() {
        messageView = TextView(context)
        messageView.id = messageViewId
        messageView.text = defaultMessage
        addViewToRoot(messageView)
    }
}