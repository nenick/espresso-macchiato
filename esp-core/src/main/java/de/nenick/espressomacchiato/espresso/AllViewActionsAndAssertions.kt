package de.nenick.espressomacchiato.espresso

import de.nenick.espressomacchiato.espresso.view.ClickActions
import de.nenick.espressomacchiato.espresso.view.GestureActions
import de.nenick.espressomacchiato.espresso.view.PositionAssertions
import de.nenick.espressomacchiato.espresso.view.EnabledAssertions
import de.nenick.espressomacchiato.espresso.view.HintAssertions
import de.nenick.espressomacchiato.espresso.view.SelectedAssertions
import de.nenick.espressomacchiato.espresso.view.TextActions
import de.nenick.espressomacchiato.espresso.view.TextAssertions
import de.nenick.espressomacchiato.espresso.view.VisibilityAssertions

interface AllViewActionsAndAssertions :
        ClickActions,
        EnabledAssertions,
        GestureActions,
        HintAssertions,
        PositionAssertions,
        SelectedAssertions,
        TextActions,
        TextAssertions,
        VisibilityAssertions {
}