package de.nenick.espressomacchiato.widget

import android.widget.EditText
import de.nenick.espressomacchiato.test.core.DefaultElementTest
import org.junit.Test

class EspEditTextTest : DefaultElementTest<EspEditText>() {
    private val editTextId = android.R.id.primary
    private val editText = EditText(context).apply {
        id = editTextId
    }

    @Test
    fun example() {
        addViewToRoot(editText)

        EspEditText(editTextId) {
            checkText("")
            performTypeText("Hello")
            checkText("Hello")
        }
    }
}