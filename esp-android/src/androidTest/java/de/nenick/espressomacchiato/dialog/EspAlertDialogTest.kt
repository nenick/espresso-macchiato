package de.nenick.espressomacchiato.dialog

import android.app.AlertDialog
import de.nenick.espressomacchiato.testtools.BaseActivityTest
import org.junit.Assert.assertEquals
import org.junit.Test

class EspAlertDialogTest : BaseActivityTest() {

    private var clicked = ""
    private var dismissed = false

    @Test
    fun example() {
        showDialog()
        EspAlertDialog {
            checkIsDisplayed()

            Title { checkText("My title") }
            Message { checkText("My message") }

            PositiveButton {
                checkText("Positive")
                performClick()
            }

            checkNotDisplayed()
        }
        assertEquals("Positive", clicked)

        showDialog()
        EspAlertDialog().NegativeButton {
            checkText("Negative")
            performClick()
        }
        assertEquals("Negative", clicked)

        showDialog()
        EspAlertDialog().NeutralButton {
            checkText("Neutral")
            performClick()
        }
        assertEquals("Neutral", clicked)
    }

    private fun showDialog() {
        runOnMainSync {
            AlertDialog.Builder(it)
                    .setTitle("My title")
                    .setMessage("My message")
                    .setNegativeButton("Negative") { _, _ -> clicked = "Negative" }
                    .setNeutralButton("Neutral") { _, _ -> clicked = "Neutral" }
                    .setPositiveButton("Positive") { _, _ -> clicked = "Positive" }
                    .setOnDismissListener { dismissed = true }
                    .show()
        }
    }
}