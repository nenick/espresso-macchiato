package de.nenick.espressomacchiato.widget

import android.widget.Button
import de.nenick.espressomacchiato.test.core.ElementTest
import org.junit.Assert.assertTrue
import org.junit.Test

class EspButtonTest : ElementTest<EspButton>() {
    private val buttonId = android.R.id.primary
    private val button = Button(context).apply {
        id = buttonId
        text = "Click Me"
        setOnClickListener { clicked = true }
    }
    private var clicked = false

    @Test
    fun example() {
        setViewToRoot(button)

        EspButton(buttonId) {
            checkText("Click Me")
            performClick()
        }
        assertTrue(clicked)
    }
}