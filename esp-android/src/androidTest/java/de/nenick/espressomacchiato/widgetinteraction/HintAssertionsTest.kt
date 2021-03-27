package de.nenick.espressomacchiato.widgetinteraction

import android.widget.EditText
import android.widget.TextView
import de.nenick.espressomacchiato.view.EspView
import de.nenick.espressomacchiato.test.core.BaseActivity
import de.nenick.espressomacchiato.test.core.BaseActivityTest
import org.junit.Test

class HintAssertionsTest : BaseActivityTest() {

    private val defaultMessage = "Hello"

    private lateinit var messageView: TextView
    private val messageViewId = android.R.id.text1
    private val espMessageView = object : EspView(messageViewId), HintAssertions {}

    @Test
    fun checkHint() {
        givenEditText()
        espMessageView.checkHint(defaultMessage)
    }

    private fun givenEditText() {
        messageView = EditText(context)
        messageView.id = messageViewId
        messageView.hint = defaultMessage
        replaceViewInLayout(messageView, BaseActivity.rootLayout)
    }
}