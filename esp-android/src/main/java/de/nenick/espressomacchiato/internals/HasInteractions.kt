package de.nenick.espressomacchiato.internals

import androidx.test.espresso.ViewInteraction

interface HasInteractions {
    /** Write custom actions or checks on this view in plain espresso style. */
    fun onView() : ViewInteraction
}