package de.nenick.espressomacchiato.elements.basics

import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.Root
import androidx.test.espresso.matcher.ViewMatchers.withId
import de.nenick.espressomacchiato.espresso.view.ClickActions
import de.nenick.espressomacchiato.espresso.view.TextAssertions
import de.nenick.espressomacchiato.internals.HasInteractions
import de.nenick.espressomacchiato.internals.OpenForExtensions
import org.hamcrest.Matcher

@OpenForExtensions
class EspButton(
        viewMatcher: Matcher<View>,
        rootMatchers: Matcher<Root>? = null,
        interactions: EspButton.() -> Unit = {}
) :
        EspView(viewMatcher, rootMatchers, interactions as EspView.() -> Unit),
        ClickActions,
        TextAssertions {

    constructor(@IdRes viewId: Int = 0, interactions: EspButton.() -> Unit = {})
            : this(viewId, null, interactions)

    constructor(@IdRes viewId: Int = 0, rootMatchers: Matcher<Root>? = null, interactions: EspButton.() -> Unit = {})
            : this(withId(viewId), rootMatchers, interactions)
}