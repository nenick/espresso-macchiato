package de.nenick.espressomacchiato.widget

import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.Root
import androidx.test.espresso.matcher.ViewMatchers.withId
import de.nenick.espressomacchiato.widgetinteraction.TextAssertions
import de.nenick.espressomacchiato.internals.OpenForExtensions
import de.nenick.espressomacchiato.view.EspView
import org.hamcrest.Matcher

@Suppress("UNCHECKED_CAST")
@OpenForExtensions
class EspTextView(
        viewMatcher: Matcher<View>,
        rootMatchers: Matcher<Root>? = null,
        interactions: EspTextView.() -> Unit = {}
) :
        EspView(viewMatcher, rootMatchers, interactions as EspView.() -> Unit),
        TextAssertions {

    constructor(@IdRes viewId: Int, interactions: EspTextView.() -> Unit = {})
            : this(withId(viewId), null, interactions)

    constructor(@IdRes viewId: Int, rootMatchers: Matcher<Root>, interactions: EspTextView.() -> Unit = {})
            : this(withId(viewId), rootMatchers, interactions)
}