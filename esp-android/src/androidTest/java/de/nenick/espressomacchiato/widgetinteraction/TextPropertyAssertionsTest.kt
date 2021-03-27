package de.nenick.espressomacchiato.widgetinteraction

import android.graphics.Color
import android.widget.TextView
import androidx.annotation.ColorRes
import de.nenick.espressomacchiato.view.EspView
import de.nenick.espressomacchiato.test.core.DefaultInteractionTest
import org.junit.Test

class TextPropertyAssertionsTest : DefaultInteractionTest() {

    private val colorBlackHex = Color.parseColor("#ff000000")
    @ColorRes
    private val colorBlackRes = android.R.color.black

    private lateinit var messageView: TextView
    private val messageViewId = android.R.id.text1
    private val espMessageView = object : EspView(messageViewId), TextPropertyAssertions {}

    @Test
    fun checkTextColorByHex() {
        givenTextView()
        espMessageView.checkTextColorCode(colorBlackHex)
    }

    @Test
    fun checkTestColorById() {
        givenTextView()
        espMessageView.checkTextColor(colorBlackRes)
    }

    private fun givenTextView() {
        messageView = TextView(context)
        messageView.id = messageViewId
        messageView.text = "hello"
        messageView.setTextColor(context.getColor(android.R.color.black))
        addViewToRoot(messageView)
    }
}