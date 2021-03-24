package de.nenick.espressomacchiato.espresso.view

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


interface TextPropertyAssertions {
    fun onView(): ViewInteraction

    fun checkTextColor(@ColorRes color: Int) = onView().check(matches(hasTextColor(color)))!!

    // e.g. 0xff000000 hexAlphaColor
    fun checkTextColorCode(@ColorInt color: Int) = onView().check(matches(withTextColor(color)))!!

    private fun withTextColor(@ColorInt color: Int): Matcher<View> {
        val matcher = object : TypeSafeMatcher<TextView>() {
            override fun describeTo(description: Description) {
                description.appendText("text color ")
                description.appendValue(color)
            }

            override fun matchesSafely(item: TextView): Boolean {
                return item.currentTextColor == color
            }
        }
        @Suppress("UNCHECKED_CAST")
        return matcher as Matcher<View>
    }
}