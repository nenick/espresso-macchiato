package de.nenick.espressomacchiato.viewinteraction

import android.R
import android.widget.Button
import android.widget.TextView
import de.nenick.espressomacchiato.view.EspView
import de.nenick.espressomacchiato.test.core.BaseActivity
import de.nenick.espressomacchiato.test.core.BaseActivityTest
import de.nenick.espressomacchiato.widgetinteraction.TextAssertions
import org.junit.Test

class ClickActionsTest : BaseActivityTest() {

    private val notClickedMessage = "not clicked yet"
    private val clickedMessage = "view was clicked"
    private val doubleClickedMessage = "view was double clicked"
    private val longClickedMessage = "view was long clicked"

    private lateinit var clickableView: Button
    private val clickableViewId = R.id.button1
    private val espClickableView = object : EspView(clickableViewId), ClickActions {}
    private var clickCounter = 0

    private lateinit var messageView: TextView
    private val messageViewId = R.id.text1
    private val espMessageView = object : EspView(messageViewId), TextAssertions {}

    @Test
    fun performClick() {
        givenClickFeedbackView()
        givenClickableView()
        givenClickListener()

        espMessageView.checkText(notClickedMessage)
        espClickableView.performClick()
        espMessageView.checkText(clickedMessage)
    }

    @Test
    fun performDoubleClick() {
        givenClickFeedbackView()
        givenClickableView()
        givenDoubleClickListener()

        espMessageView.checkText(notClickedMessage)
        espClickableView.performDoubleClick()
        espMessageView.checkText(doubleClickedMessage)

        givenResetClickFeedbackView()

        espMessageView.checkText(notClickedMessage)
        espClickableView.performClick()
        espMessageView.checkText(notClickedMessage)
        espClickableView.performClick()
        espMessageView.checkText(doubleClickedMessage)
    }

    @Test
    fun performLongClick() {
        givenClickFeedbackView()
        givenClickableView()
        givenLongClickListener()

        espMessageView.checkText(notClickedMessage)
        espClickableView.performLongClick()
        espMessageView.checkText(longClickedMessage)
    }

    private fun givenResetClickFeedbackView() {
        clickCounter = 0
        messageView.text = notClickedMessage
    }

    private fun givenClickableView() {
        clickableView = Button(context)
        clickableView.id = clickableViewId
        clickableView.text = "click me"
        replaceViewInLayout(clickableView, BaseActivity.rootLayout)
    }

    private fun givenClickListener() {
        clickableView.setOnClickListener { messageView.text = clickedMessage }
    }

    private fun givenLongClickListener() {
        clickableView.setOnLongClickListener { messageView.text = longClickedMessage; true }
    }

    private fun givenDoubleClickListener() {
        clickableView.setOnClickListener { if (++clickCounter == 2) messageView.text = doubleClickedMessage }
    }

    private fun givenClickFeedbackView() {
        messageView = TextView(context)
        messageView.id = messageViewId
        replaceViewInLayout(messageView, BaseActivity.rootLayout)
        givenResetClickFeedbackView()
    }
}