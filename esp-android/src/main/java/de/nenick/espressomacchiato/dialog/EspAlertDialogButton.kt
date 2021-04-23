package de.nenick.espressomacchiato.dialog

import android.os.Build
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.RootMatchers
import de.nenick.espressomacchiato.internals.ClickWithRetryAction
import de.nenick.espressomacchiato.widget.EspButton

@Suppress("UNCHECKED_CAST")
class EspAlertDialogButton(
    id: Int,
    interactions: EspAlertDialogButton.() -> Unit = {}
) : EspButton(id, RootMatchers.isDialog(), interactions as EspButton.() -> Unit) {

    override fun performClick() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            // When fast open/close multiple dialogs, the click is not always performed properly.
            // You see the button, but you don't see any click event happen.
            // https://github.com/android/android-test/issues/444
            perform(ClickWithRetryAction())
            Espresso.onIdle()
        } else {
            super.performClick()
        }
    }
}