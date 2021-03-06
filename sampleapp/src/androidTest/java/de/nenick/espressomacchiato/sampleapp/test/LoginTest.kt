package de.nenick.espressomacchiato.sampleapp.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import de.nenick.espressomacchiato.dialog.EspAlertDialog
import de.nenick.espressomacchiato.espresso.view.EnabledAssertions
import de.nenick.espressomacchiato.sampleapp.R
import de.nenick.espressomacchiato.widget.EspButton
import de.nenick.espressomacchiato.widget.EspEditText
import org.junit.Test

/** sample test using page objects  */
class LoginTest {//: EspressoTestCase<LoginActivity>() {

    @Test
    fun espressoPure() {
        onView(withId(R.id.confirm)).check(matches(isDisplayed()))

        onView(withId(R.id.username)).perform(typeText("MyUserName"))
        onView(withId(R.id.password)).perform(typeText("*****"))
        //EspDevice.root().closeSoftKeyboard()

        onView(withId(R.id.confirm)).perform(click())
        // Android id to reference AlterDialog title is not accessible.
        onView(withText("Wrong username or password.")).inRoot(isDialog()).check(matches(isDisplayed()))
    }

    class StatefulButton(id: Int) : EspButton(id), EnabledAssertions

    @Test
    fun espressoMacchiato() {
        StatefulButton(R.id.confirm).checkIsDisabled()

        EspEditText(R.id.username).performTypeText("MyUserName")
        EspEditText(R.id.password).performTypeText("*****")
        //EspDevice.root().closeSoftKeyboard()

        StatefulButton(R.id.confirm).performClick()
        EspAlertDialog().Title().checkText("Wrong username or password.")
    }

    @Test
    fun espressoMacchiatoPagePattern() {
        LoginPage {
            ConfirmButton { checkIsDisabled() }

            UsernameInput { performTypeText("MyUserName") }
            PasswordInput { performTypeText("*****") }
            //EspDevice.root().closeSoftKeyboard()

            ConfirmButton { performClick() }
            ErrorDialog { Title { checkText("Wrong username or password.") } }
        }
    }
}