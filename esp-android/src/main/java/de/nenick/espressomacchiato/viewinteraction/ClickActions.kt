package de.nenick.espressomacchiato.viewinteraction

import android.os.Build
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import de.nenick.espressomacchiato.internals.ClickWithRetryAction
import de.nenick.espressomacchiato.internals.HasActions

interface ClickActions : HasActions {
    fun performClick() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            // Clicks aren't performed properly sometimes.
            // https://github.com/android/android-test/issues/444
            perform(ClickWithRetryAction())
            Espresso.onIdle()
        } else {
            perform(click())
        }
    }

    fun performDoubleClick() = perform(doubleClick())
    fun performLongClick() = perform(longClick())
}