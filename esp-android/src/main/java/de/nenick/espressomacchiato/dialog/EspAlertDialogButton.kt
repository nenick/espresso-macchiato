package de.nenick.espressomacchiato.dialog

import android.os.Build
import androidx.test.espresso.matcher.RootMatchers
import de.nenick.espressomacchiato.widget.EspButton

@Suppress("UNCHECKED_CAST")
class EspAlertDialogButton(id: Int, interactions: EspAlertDialogButton.() -> Unit = {}) : EspButton(id, RootMatchers.isDialog(), interactions as EspButton.() -> Unit) {

    override fun performClick() {
        fixForFastDialogTests()
        super.performClick()
    }

    private fun fixForFastDialogTests() {
        // When fast open/close multiple dialogs, the click is not always performed properly.
        // You see the button, but you don't see any click event happen.
        // https://github.com/android/android-test/issues/444
        // Issue was only observed on the following android versions.
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            // Weird, but is enough to just give the dialog a short moment to recognize the
            // click properly. Perhaps the click listener isn't added yet, missing focusable?
            // 20ms - still flaky on fast emulator
            // 50ms - still flaky on slow emulator
            Thread.sleep(100)
        }
    }
}