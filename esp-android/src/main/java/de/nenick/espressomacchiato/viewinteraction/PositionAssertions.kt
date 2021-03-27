package de.nenick.espressomacchiato.viewinteraction

import androidx.test.espresso.assertion.PositionAssertions.*
import de.nenick.espressomacchiato.view.EspView
import de.nenick.espressomacchiato.internals.HasInteractions

interface PositionAssertions : HasInteractions {
    fun checkIsLeftOf(view: EspView) = onView().check(isCompletelyLeftOf(view.viewMatcher))
    fun checkIsRightOf(view: EspView) = onView().check(isCompletelyRightOf(view.viewMatcher))
    fun checkIsAbove(view: EspView) = onView().check(isCompletelyBelow(view.viewMatcher))
    fun checkIsBelow(view: EspView) = onView().check(isCompletelyAbove(view.viewMatcher))
}