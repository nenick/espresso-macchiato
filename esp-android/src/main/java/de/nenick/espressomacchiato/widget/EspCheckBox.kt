package de.nenick.espressomacchiato.widget

import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.Root
import androidx.test.espresso.matcher.ViewMatchers
import de.nenick.espressomacchiato.widgetinteraction.CheckAssertions
import de.nenick.espressomacchiato.viewinteraction.ClickActions
import de.nenick.espressomacchiato.widgetinteraction.TextAssertions
import de.nenick.espressomacchiato.internals.OpenForExtensions
import de.nenick.espressomacchiato.view.EspView
import org.hamcrest.Matcher

@Suppress("UNCHECKED_CAST")
@OpenForExtensions
class EspCheckBox(
        viewMatcher: Matcher<View>,
        rootMatchers: Matcher<Root>? = null,
        interactions: EspCheckBox.() -> Unit = {}
) :
        EspView(viewMatcher, rootMatchers, interactions as EspView.() -> Unit),
        ClickActions,
        TextAssertions,
        CheckAssertions {

    constructor(@IdRes viewId: Int, interactions: EspCheckBox.() -> Unit = {})
            : this(ViewMatchers.withId(viewId), null, interactions)

    constructor(@IdRes viewId: Int, rootMatchers: Matcher<Root>, interactions: EspCheckBox.() -> Unit = {})
            : this(ViewMatchers.withId(viewId), rootMatchers, interactions)

    constructor(viewMatcher: Matcher<View>, interactions: EspCheckBox.() -> Unit = {})
            : this(viewMatcher, null, interactions)
}