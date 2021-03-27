package de.nenick.espressomacchiato.androidx.drawer

import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.Root
import androidx.test.espresso.matcher.ViewMatchers
import de.nenick.espressomacchiato.androidx.drawerinteraction.EspDrawerActions
import de.nenick.espressomacchiato.androidx.drawerinteraction.EspDrawerAssertions
import de.nenick.espressomacchiato.view.EspView
import org.hamcrest.Matcher

@Suppress("UNCHECKED_CAST")
class EspDrawer(
        viewMatcher: Matcher<View>,
        rootMatchers: Matcher<Root>? = null,
        interactions: EspDrawer.() -> Unit = {}
) :
        EspView(viewMatcher, rootMatchers, interactions as EspView.() -> Unit),
        EspDrawerActions,
        EspDrawerAssertions {

    constructor(@IdRes viewId: Int, interactions: EspDrawer.() -> Unit = {})
            : this(ViewMatchers.withId(viewId), null, interactions)

    constructor(@IdRes viewId: Int, rootMatchers: Matcher<Root>, interactions: EspDrawer.() -> Unit = {})
            : this(ViewMatchers.withId(viewId), rootMatchers, interactions)

    constructor(viewMatcher: Matcher<View>, interactions: EspDrawer.() -> Unit = {})
            : this(viewMatcher, null, interactions)
}