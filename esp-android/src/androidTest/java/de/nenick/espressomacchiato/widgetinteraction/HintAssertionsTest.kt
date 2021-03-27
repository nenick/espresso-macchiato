package de.nenick.espressomacchiato.widgetinteraction

import android.widget.EditText
import android.widget.TextView
import de.nenick.espressomacchiato.test.core.DefaultInteractionTest
import de.nenick.espressomacchiato.view.EspView
import org.junit.Test

class HintAssertionsTest : DefaultInteractionTest() {

    private val defaultMessage = "Hello"

    private lateinit var messageView: TextView
    private val messageViewId = android.R.id.text1
    private val espMessageView = object : EspView(messageViewId), HintAssertions {}

    @Test
    fun checkHint() {
        givenEditText()
        espMessageView.checkHint(defaultMessage)
    }

    private fun givenEditText() = runOnMainSync {
        messageView = EditText(context)
        messageView.id = messageViewId
        messageView.hint = defaultMessage
        addViewToRoot(messageView)
    }
}