package de.nenick.espressomacchiato.widget

import android.widget.CheckBox
import de.nenick.espressomacchiato.test.core.DefaultElementTest
import org.junit.Test

class EspCheckBoxTest : DefaultElementTest<EspButton>() {
    private val checkBoxId = android.R.id.primary
    private val checkBox = CheckBox(context).apply {
        id = checkBoxId
        text = "Check Me"
    }

    @Test
    fun example() {
        addViewToRoot(checkBox)

        EspCheckBox(checkBoxId) {
            checkIsNotChecked()
            checkText("Check Me")
            performClick()
            checkIsChecked()
        }
    }
}