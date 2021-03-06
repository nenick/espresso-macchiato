package de.nenick.espressomacchiato.widget

import android.widget.Button
import de.nenick.espressomacchiato.testtools.ElementTest
import org.junit.Test

class EspEditTextTest : ElementTest<EspEditText>() {
    private val editTextId = android.R.id.primary
    private val editText = Button(context).apply {
        id = editTextId
    }

    @Test
    fun example() {
        setViewToRoot(editText)

        EspEditText(editTextId) {
            checkText("")
            performTypeText("Hello")
            checkText("Hello")
        }
    }
}