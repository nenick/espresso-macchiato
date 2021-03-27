package de.nenick.espressomacchiato.android.material.appbar

import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.matcher.ViewMatchers
import com.google.android.material.appbar.AppBarLayout
import de.nenick.espressomacchiato.android.material.appbarinteraction.CollapsingActions
import de.nenick.espressomacchiato.android.material.appbarinteraction.CollapsingAssertions
import de.nenick.espressomacchiato.internals.OpenForExtensions
import de.nenick.espressomacchiato.view.EspView
import de.nenick.espressomacchiato.widget.EspTextView
import org.hamcrest.Matchers

@Suppress("TestFunctionName", "UNCHECKED_CAST")
@OpenForExtensions
class EspCollapsibleToolbar(
        interactions: EspCollapsibleToolbar.() -> Unit
) :
        EspView(ViewMatchers.isAssignableFrom(AppBarLayout::class.java), interactions as EspView.() -> Unit),
        CollapsingActions,
        CollapsingAssertions {

    fun Title(interactions: EspTextView.() -> Unit) = EspTextView(Matchers.allOf(
            ViewMatchers.isDescendantOfA(ViewMatchers.isAssignableFrom(Toolbar::class.java)),
            ViewMatchers.isAssignableFrom(TextView::class.java)),
            interactions
    )
}