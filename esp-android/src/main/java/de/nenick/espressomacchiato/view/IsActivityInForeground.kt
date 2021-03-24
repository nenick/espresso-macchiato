package de.nenick.espressomacchiato.view

import android.view.WindowManager
import androidx.test.espresso.Espresso
import androidx.test.espresso.Root
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import java.lang.IllegalStateException

class IsActivityInForeground(private val result: Result) : TypeSafeMatcher<Root?>() {

    companion object {
        fun assertActivityInForeground(errorMessage: String) {
            val result = Result()
            Espresso.onView(ViewMatchers.isRoot()).inRoot(IsActivityInForeground(result)).check(ViewAssertions.matches(ViewMatchers.isRoot()))
            if (!result.foundActivityInForeground) {
                throw IllegalStateException(errorMessage)
            }
        }
    }

    data class Result(var foundActivityInForeground: Boolean = true)

    override fun describeTo(description: Description) {
        description.appendText("is dialog")
    }

    override fun matchesSafely(root: Root?): Boolean {
        if (root == null) return false

        // Origin logic comes from espresso IsDialog matcher.
        val type = root.windowLayoutParams.get().type
        if (type != WindowManager.LayoutParams.TYPE_BASE_APPLICATION
                && type < WindowManager.LayoutParams.LAST_APPLICATION_WINDOW) {
            val windowToken = root.decorView.windowToken
            val appToken = root.decorView.applicationWindowToken

            @Suppress("RedundantIf")
            if (windowToken === appToken) {
                // windowToken == appToken means this window isn't contained by any other windows.
                // if it was a window for an activity, it would have TYPE_BASE_APPLICATION.
                // therefore it must be a dialog box.
                result.foundActivityInForeground = false
            }
        }

        // Always return true to immediately proceed. Otherwise espresso would repeating search until timeout.
        return true
    }
}