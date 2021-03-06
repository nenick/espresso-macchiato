package de.nenick.espressomacchiato.sampleapp.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import de.nenick.espressomacchiato.sampleapp.R
import org.junit.Test

/** sample test using page objects  */
class LoginEspressoTest {//: EspressoTestCase<LoginActivity>() {

    @Test
    fun testWrongLogin() {
        LoginPage {

            onView(withId(R.id.confirm)).check(matches(isDisplayed()))
            ConfirmButton { checkIsDisabled() }

            UsernameInput { performTypeText("MyUserName") }
            PasswordInput { performTypeText("*****") }

            //EspDevice.root().closeSoftKeyboard()

            ConfirmButton { performClick() }
            ErrorDialog { Title { checkText("Wrong username or password.") } }
        }
    }
}