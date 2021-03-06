package de.nenick.espressomacchiato.dialog

import de.nenick.espressomacchiato.espresso.dialog.DialogVisibilityAssertions
import de.nenick.espressomacchiato.internals.OpenForExtensions

@Suppress("FunctionName")
@OpenForExtensions
class EspDialog(block: EspDialog.() -> Unit = {}) :
        DialogVisibilityAssertions {

    init {
        @Suppress("LeakingThis")
        block(this)
    }
}