package de.nenick.espressomacchiato.dialog

import de.nenick.espressomacchiato.dialoginteraction.DialogVisibilityAssertions
import de.nenick.espressomacchiato.internals.OpenForExtensions
import de.nenick.espressomacchiato.internals.ScopeDslMarker

// Scope marker reduces the amount methods when views get nested e.g. the page style.
// Result is in sub lambda blocks you don't have access to the parent lambda block methods.
@ScopeDslMarker
@Suppress("FunctionName")
@OpenForExtensions
class EspDialog(block: EspDialog.() -> Unit = {}) :
        DialogVisibilityAssertions {

    init {
        @Suppress("LeakingThis")
        block(this)
    }
}