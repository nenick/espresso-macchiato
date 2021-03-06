package de.nenick.espressomacchiato.internals

import android.view.View
import org.hamcrest.Matcher

interface HasChecks {
    /** Short way for check assertions on this view. */
    fun check(matcher: Matcher<View>)
}