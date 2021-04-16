package de.nenick.espressomacchiato.dialog

import android.app.AlertDialog
import android.os.Build
import androidx.test.ext.junit.rules.activityScenarioRule
import de.nenick.espressomacchiato.test.core.BaseActivity
import de.nenick.espressomacchiato.test.core.BaseActivityTest
import org.junit.Assert.assertEquals
import org.junit.Test

class EspAlertDialogTest : BaseActivityTest<BaseActivity>() {

    override val activityScenarioRule = activityScenarioRule<BaseActivity>()

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
            val dialogBuilder = AlertDialog.Builder(it)
                    .setTitle("My title")
                    .setMessage("My message")
                    .setNegativeButton("Negative") { _, _ -> clicked = "Negative" }
                    .setNeutralButton("Neutral") { _, _ -> clicked = "Neutral" }
                    .setPositiveButton("Positive") { _, _ -> clicked = "Positive" }

            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                dialogBuilder.apply {
                    setOnDismissListener { dismissed = true }
                    show()
                }
            } else {
                // On and pre Jelly Bean the builder does not have the setOnDismissListener but the dialog has.
                // https://stackoverflow.com/questions/16970866/why-does-android-nosuchmethodexception-occurs-at-alertdialog-builders-setondism
                dialogBuilder.create().apply {
                    setOnDismissListener { dismissed = true }
                    show()
                }
            }
        }
    }
}