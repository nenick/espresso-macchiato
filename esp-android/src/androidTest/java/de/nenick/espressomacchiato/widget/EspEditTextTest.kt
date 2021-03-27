package de.nenick.espressomacchiato.widget

import android.widget.EditText
import de.nenick.espressomacchiato.test.core.DefaultElementTest
import org.junit.Test

class EspEditTextTest : DefaultElementTest<EspEditText>() {
    private val editTextId = android.R.id.primary
    private lateinit var editText: EditText

    @Test
    fun example() {
        givenEditText()

        EspEditText(editTextId) {
            checkText("")
            performTypeText("Hello")
            checkText("Hello")
        }
    }

    fun givenEditText() = runOnMainSync {
        editText = EditText(context).apply {
            id = editTextId
        }
        addViewToRoot(editText)
    }
}