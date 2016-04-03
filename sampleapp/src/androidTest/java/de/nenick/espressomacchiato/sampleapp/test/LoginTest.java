package de.nenick.espressomacchiato.sampleapp.test;

import org.junit.Test;

import de.nenick.espressomacchiato.sampleapp.LoginActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

public class LoginTest extends EspressoTestCase<LoginActivity> {

    private EspLoginPage loginPage = new EspLoginPage();

    @Test
    public void testWrongLogin() {
        loginPage.confirm().assertIsDisabled();
        loginPage.username().replaceText("MyUserName");
        loginPage.password().replaceText("*****");
        loginPage.confirm().click();
        loginPage.errorMessage().assertTextIs("Username or password not correct.");
    }
}
