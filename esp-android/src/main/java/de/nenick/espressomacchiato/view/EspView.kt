package de.nenick.espressomacchiato.view

import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.Root
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import de.nenick.espressomacchiato.EspGlobalSettings
import de.nenick.espressomacchiato.internals.*
import de.nenick.espressomacchiato.internals.IsActivityInForegroundMatcher.Companion.assertActivityInForeground
import org.hamcrest.Matcher

// Scope marker reduces the amount methods when views get nested e.g. the page style.
// Result is in sub lambda blocks you don't have access to the parent lambda block methods.
@ScopeDslMarker
@OpenForExtensions
class EspView(

        /** The logic to locate this view. */
        val viewMatcher: Matcher<View>,

        /**
         * Optional to located views inside different roots than the activity.
         *
         * E.g. dialogs, see [RootMatchers] for more details.
         */
        private val rootMatchers: Matcher<Root>? = null,

        /** Short way to call assertions and actions on this view. */
        interactions: EspView.() -> Unit = {}

) : HasChecks, HasActions, HasInteractions {

    /** Short way to locate this view by his id, the most common way to do that. */
    constructor(@IdRes viewId: Int, interactions: EspView.() -> Unit = {})
            : this(withId(viewId), null, interactions)

    /** Short way to locate this view by his id within different root (e.g. dialogs). */
    constructor(@IdRes viewId: Int, rootMatchers: Matcher<Root>, interactions: EspView.() -> Unit = {})
            : this(withId(viewId), rootMatchers, interactions)

    /** Short way to locate this view by custom view matcher. */
    constructor(viewMatcher: Matcher<View>, interactions: EspView.() -> Unit = {})
            : this(viewMatcher, null, interactions)

    init {
        // The compile may be right to complain about it but the possible issues did not happen yet.
        // This supports the fancy EspView(id) { check(something) } style.
        @Suppress("LeakingThis")
        interactions(this)
    }

    override fun onView(): ViewInteraction {
        val viewInteraction = Espresso.onView(viewMatcher)

        if (rootMatchers != null) {
            viewInteraction.inRoot(rootMatchers)
        } else if (EspGlobalSettings.activityForegroundCheckEnabled) {
            assertActivityInForeground("Found something different than an activity in foreground. Is this a wrong situation or did you forgot to add a root matcher e.g. isDialog?")
        }

        return viewInteraction
    }

    override fun check(matcher: Matcher<View>) {
        onView().check(ViewAssertions.matches(matcher))
    }

    override fun perform(action: ViewAction) {
        onView().perform(action)
    }

}
