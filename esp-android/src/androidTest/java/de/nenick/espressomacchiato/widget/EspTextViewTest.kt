package de.nenick.espressomacchiato.widget

import android.widget.Button
import de.nenick.espressomacchiato.test.core.DefaultElementTest
import org.junit.Test

class EspTextViewTest : DefaultElementTest<EspTextView>() {
    private val textViewId = android.R.id.primary
    private val textView = Button(context).apply {
        id = textViewId
        text = "Hello"
    }

    @Test
    fun example() {
        addViewToRoot(textView)

        EspTextView(textViewId) {
            checkText("Hello")
        }
    }
}