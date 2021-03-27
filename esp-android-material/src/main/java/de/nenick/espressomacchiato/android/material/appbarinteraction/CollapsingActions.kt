package de.nenick.espressomacchiato.android.material.appbarinteraction

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import com.google.android.material.appbar.AppBarLayout
import de.nenick.espressomacchiato.internals.HasActions
import org.hamcrest.Matcher

interface CollapsingActions: HasActions {

    fun  performCollapse() = perform(collapse())
    fun  performExpand() = perform(expand())

    private fun collapse(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(AppBarLayout::class.java)
            }

            override fun getDescription(): String {
                return "Collapse AppBarLayout inside of a CollapsingToolbarLayout."
            }

            override fun perform(uiController: UiController, view: View) {
                val appBarLayout = view as AppBarLayout
                appBarLayout.setExpanded(false, false)
            }
        }
    }

    private fun expand(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(AppBarLayout::class.java)
            }

            override fun getDescription(): String {
                return "Expand AppBarLayout inside of a CollapsingToolbarLayout."
            }

            override fun perform(uiController: UiController, view: View) {
                val appBarLayout = view as AppBarLayout
                appBarLayout.setExpanded(true, false)
            }
        }
    }
}