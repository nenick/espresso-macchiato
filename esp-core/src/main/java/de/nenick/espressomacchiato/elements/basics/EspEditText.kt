package de.nenick.espressomacchiato.elements.basics

import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.Root
import androidx.test.espresso.matcher.ViewMatchers
import de.nenick.espressomacchiato.espresso.view.TextActions
import de.nenick.espressomacchiato.espresso.view.TextAssertions
import de.nenick.espressomacchiato.internals.OpenForExtensions
import org.hamcrest.Matcher

@OpenForExtensions
class EspEditText(
        viewMatcher: Matcher<View>,
        rootMatchers: Matcher<Root>? = null,
        interactions: EspEditText.() -> Unit = {}
) :
        EspView(viewMatcher, rootMatchers, interactions as EspView.() -> Unit),
        TextActions,
        TextAssertions {

    constructor(@IdRes viewId: Int = 0, interactions: EspEditText.() -> Unit = {})
            : this(viewId, null, interactions)

    constructor(@IdRes viewId: Int = 0, rootMatchers: Matcher<Root>? = null, interactions: EspEditText.() -> Unit = {})
            : this(ViewMatchers.withId(viewId), rootMatchers, interactions)
}