package de.nenick.espressomacchiato.android.material.appbarinteraction

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import de.nenick.espressomacchiato.internals.HasChecks
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import kotlin.math.abs


interface CollapsingAssertions : HasChecks {

    fun checkIsCollapsed() = check(isCollapsed())
    fun checkIsExpanded() = check(isExpanded())

    @Suppress("UNCHECKED_CAST")
    private fun isCollapsed() = object : TypeSafeMatcher<AppBarLayout>() {
        override fun describeTo(description: Description) {
            description.appendText("is expanded")
        }

        override fun matchesSafely(item: AppBarLayout): Boolean {
            // https://stackoverflow.com/questions/32213783/detecting-when-appbarlayout-collapsingtoolbarlayout-is-completely-expanded
            val collapsingToolbarLayout = searchCollapsingToolbarLayout(item)
            val toolbar = searchToolbar(collapsingToolbarLayout)
            val layoutParams = item.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = layoutParams.behavior as AppBarLayout.Behavior
            return item.height - abs(behavior.topAndBottomOffset) == toolbar.height
        }

        private fun searchToolbar(collapsingToolbarLayout: CollapsingToolbarLayout): Toolbar {
            for (i in 0..collapsingToolbarLayout.childCount) {
                val child = collapsingToolbarLayout.getChildAt(i)
                if (child is Toolbar) return child
            }
            throw IllegalStateException("no toolbar found")
        }

        private fun searchCollapsingToolbarLayout(item: AppBarLayout) = item.getChildAt(0) as CollapsingToolbarLayout
    } as Matcher<View>

    @Suppress("UNCHECKED_CAST")
    private fun isExpanded() = object : TypeSafeMatcher<AppBarLayout>() {
        override fun describeTo(description: Description) {
            description.appendText("is expanded")
        }

        override fun matchesSafely(item: AppBarLayout): Boolean {
            // https://stackoverflow.com/questions/32213783/detecting-when-appbarlayout-collapsingtoolbarlayout-is-completely-expanded
            val layoutParams = item.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = layoutParams.behavior as AppBarLayout.Behavior
            return behavior.topAndBottomOffset == 0
        }
    } as Matcher<View>
}