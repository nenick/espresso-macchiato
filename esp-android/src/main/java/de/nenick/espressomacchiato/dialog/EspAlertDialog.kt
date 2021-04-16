package de.nenick.espressomacchiato.dialog

import androidx.test.espresso.matcher.RootMatchers.isDialog
import de.nenick.espressomacchiato.tools.EspResourceTool.Companion.androidId
import de.nenick.espressomacchiato.internals.OpenForExtensions
import de.nenick.espressomacchiato.widget.EspButton
import de.nenick.espressomacchiato.widget.EspTextView

@Suppress("FunctionName", "UNCHECKED_CAST")
@OpenForExtensions
class EspAlertDialog(block: EspAlertDialog.() -> Unit = {}) : EspDialog(block as EspDialog.() -> Unit) {

    fun Title(block: EspTextView.() -> Unit = {}) = EspTextView(androidId("alertTitle"), isDialog(), block)
    fun Message(block: EspTextView.() -> Unit = {}) = EspTextView(android.R.id.message, isDialog(), block)
    fun PositiveButton(block: EspButton.() -> Unit = {}) = EspAlertDialogButton(android.R.id.button1, block)
    fun NegativeButton(block: EspButton.() -> Unit = {}) = EspAlertDialogButton(android.R.id.button2, block)
    fun NeutralButton(block: EspButton.() -> Unit = {}) = EspAlertDialogButton(android.R.id.button3, block)
}