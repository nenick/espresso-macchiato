package de.nenick.espressomacchiato.sampleapp.test

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.runner.AndroidJUnitRunner
import de.nenick.espressomacchiato.dialog.EspAlertDialog
import de.nenick.espressomacchiato.sampleapp.LoginActivity
import de.nenick.espressomacchiato.sampleapp.R
import de.nenick.espressomacchiato.screenshot.EspScreenshotRule
import de.nenick.espressomacchiato.viewinteraction.EnabledAssertions
import de.nenick.espressomacchiato.widget.EspButton
import de.nenick.espressomacchiato.widget.EspEditText
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class LoginTest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<LoginActivity>()

    @get:Rule
    val screenshotRule = EspScreenshotRule()

    @Test
    fun espressoPure() {
        onView(withId(R.id.confirm)).check(matches(not(isEnabled())))

        onView(withId(R.id.username)).perform(typeText("MyUserName"))
        onView(withId(R.id.password)).perform(typeText("*****"))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.confirm)).perform(click())

        // Android id to reference AlterDialog alertTitle is not accessible.
        onView(withText("Error")).inRoot(isDialog()).check(matches(isDisplayed()))
        onView(withId(android.R.id.message)).inRoot(isDialog()).check(matches(withText("Wrong username or password.")))
        onView(withId(android.R.id.button1)).inRoot(isDialog()).perform(click())
    }

    class StatefulButton(id: Int) : EspButton(id), EnabledAssertions

    @Test
    fun espressoMacchiato() {
        StatefulButton(R.id.confirm).checkIsDisabled()

        EspEditText(R.id.username).performTypeText("MyUserName")
        EspEditText(R.id.password).performTypeText("*****")
        Espresso.closeSoftKeyboard()

        StatefulButton(R.id.confirm).performClick()

        EspAlertDialog().Title().checkText("Error")
        EspAlertDialog().Message().checkText("Wrong username or password.")
        EspAlertDialog().PositiveButton().performClick()
    }

    @Test
    fun espressoMacchiatoPageObjectPattern() {
        LoginPage {
            ConfirmButton { checkIsDisabled() }

            UsernameInput { performTypeText("MyUserName") }
            PasswordInput { performTypeText("*****") }
            Espresso.closeSoftKeyboard()

            ConfirmButton { performClick() }

            ErrorDialog {
                Title { checkText("Error") }
                Message { checkText("Wrong username or password.") }
                PositiveButton { performClick() }
            }
        }
    }
}