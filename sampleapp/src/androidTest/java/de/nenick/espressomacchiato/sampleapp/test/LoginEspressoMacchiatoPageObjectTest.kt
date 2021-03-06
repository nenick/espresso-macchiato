package de.nenick.espressomacchiato.sampleapp.test

import de.nenick.espressomacchiato.elements.EspDevice
import de.nenick.espressomacchiato.sampleapp.LoginActivity
import de.nenick.espressomacchiato.testbase.EspressoTestCase
import org.junit.Test

/** sample test using page objects  */
class LoginEspressoMacchiatoPageObjectTest : EspressoTestCase<LoginActivity?>() {

    @Test
    fun testWrongLogin() {
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