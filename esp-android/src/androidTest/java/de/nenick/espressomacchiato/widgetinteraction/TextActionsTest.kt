package de.nenick.espressomacchiato.widgetinteraction

import android.widget.EditText
import android.widget.TextView
import androidx.test.espresso.action.ViewActions
import de.nenick.espressomacchiato.EspGlobalSettings
import de.nenick.espressomacchiato.view.EspView
import de.nenick.espressomacchiato.test.R
import de.nenick.espressomacchiato.testtools.BaseActivity
import de.nenick.espressomacchiato.testtools.BaseActivityTest
import org.junit.Test

class TextActionsTest : BaseActivityTest() {

    private val defaultMessage = context.getString(R.string.name)

    private lateinit var messageView: TextView
    private val messageViewId = android.R.id.text1
    private val espMessageView = object : EspView(messageViewId), TextActions, TextAssertions {}

    @Test
    fun performReplaceText() {
        givenEditText()
        espMessageView.performReplaceText("changed")
        espMessageView.checkText("changed")
    }

    @Test
    fun performTypeText() {
        givenEditText()
        espMessageView.performTypeText(" added")
        espMessageView.checkText("$defaultMessage added")
    }

    @Test
    fun performTypeTextSupportsDifferentStrategy() = EspGlobalSettings.temporaryEspSettingsChange {
        EspGlobalSettings.typeTextStrategy = ViewActions::replaceText

        givenEditText()
        espMessageView.performTypeText(" added")
        espMessageView.checkText("$defaultMessage added")
    }

    @Test
    fun checkClearText() {
        givenEditText()
        espMessageView.performClearText()
        espMessageView.checkText("")
    }

    private fun givenEditText() {
        messageView = EditText(context)
        messageView.id = messageViewId
        messageView.text = defaultMessage
        replaceViewInLayout(messageView, BaseActivity.rootLayout)
    }
}