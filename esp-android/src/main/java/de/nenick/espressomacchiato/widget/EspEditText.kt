package de.nenick.espressomacchiato.widget

import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.Root
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import de.nenick.espressomacchiato.espresso.view.TextActions
import de.nenick.espressomacchiato.espresso.view.TextAssertions
import de.nenick.espressomacchiato.internals.OpenForExtensions
import de.nenick.espressomacchiato.view.EspView
import org.hamcrest.Matcher

@Suppress("UNCHECKED_CAST")
@OpenForExtensions
class EspEditText(
        viewMatcher: Matcher<View>,
        rootMatchers: Matcher<Root>? = null,
        interactions: EspEditText.() -> Unit = {}
) :
        EspView(viewMatcher, rootMatchers, interactions as EspView.() -> Unit),
        TextActions,
        TextAssertions {

    constructor(@IdRes viewId: Int, interactions: EspEditText.() -> Unit = {})
            : this(withId(viewId), null, interactions)

    constructor(@IdRes viewId: Int, rootMatchers: Matcher<Root>, interactions: EspEditText.() -> Unit = {})
            : this(withId(viewId), rootMatchers, interactions)
}