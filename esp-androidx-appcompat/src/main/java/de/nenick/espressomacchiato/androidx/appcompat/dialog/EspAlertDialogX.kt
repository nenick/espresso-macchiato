package de.nenick.espressomacchiato.androidx.appcompat.dialog

import androidx.test.espresso.matcher.RootMatchers.isDialog
import de.nenick.espressomacchiato.dialog.EspDialog
import de.nenick.espressomacchiato.internals.OpenForExtensions
import de.nenick.espressomacchiato.widget.EspButton
import de.nenick.espressomacchiato.widget.EspTextView

@Suppress("FunctionName", "UNCHECKED_CAST")
@OpenForExtensions
class EspAlertDialogX(block: EspAlertDialogX.() -> Unit = {}) : EspDialog(block as EspDialog.() -> Unit) {
    fun Title(block: EspTextView.() -> Unit = {}) = EspTextView(androidx.appcompat.R.id.alertTitle, isDialog(), block)
    fun Message(block: EspTextView.() -> Unit = {}) = EspTextView(android.R.id.message, isDialog(), block)
    fun PositiveButton(block: EspButton.() -> Unit = {}) = EspButton(android.R.id.button1, isDialog(), block)
    fun NegativeButton(block: EspButton.() -> Unit = {}) = EspButton(android.R.id.button2, isDialog(), block)
    fun NeutralButton(block: EspButton.() -> Unit = {}) = EspButton(android.R.id.button3, isDialog(), block)
}