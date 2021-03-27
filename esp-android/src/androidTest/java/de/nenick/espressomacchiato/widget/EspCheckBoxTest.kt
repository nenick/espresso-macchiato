package de.nenick.espressomacchiato.widget

import android.widget.CheckBox
import de.nenick.espressomacchiato.test.core.ElementTest
import org.junit.Test

class EspCheckBoxTest : ElementTest<EspButton>() {
    private val checkBoxId = android.R.id.primary
    private val checkBox = CheckBox(context).apply {
        id = checkBoxId
        text = "Check Me"
    }
    private var clicked = false

    @Test
    fun example() {
        setViewToRoot(checkBox)

        EspCheckBox(checkBoxId) {
            checkIsNotChecked()
            checkText("Check Me")
            performClick()
            checkIsChecked()
        }
    }
}