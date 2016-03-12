package de.nenick.espressomacchiato.sampleapp.test;

import org.junit.Test;

public class LoginTest extends EspressoTestCase {

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
