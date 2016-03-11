# espresso-macchiato
*A dollop of foamed milk with the texture of melted ice cream.*

## Espresso extension

Target is to hide most of the hamcrest style checks and actions.

### The issue with Espresso and Hamcrest

Hamcrest style is hard to read and not very intuitive to use.
The learning phase is very long when you have not much experience with hamcrest and android layouts.

## The Idea

(Add some Page Pattern links ...)

The current visible content is called a page.
Each page has specific elements like Drawer, Actionbar, Button, List, ...
Each elements has different actions like click, check, scroll, ...

## Plain espresso example vs page object pattern

Simple example for login

* Login button is initial disabled
* User type his name
* User type wrong password
* User click login button
* Error message is shown

espresso

    onView(withId(R.id.buttonConfirm)).check(isDisabled()));
    onView(withId(R.id.textViewUsername)).perform(typeText("MyUserName"));
    onView(withId(R.id.textViewPassword)).perform(typeText("*****"));
    onView(withId(R.id.buttonConfirm)).perform(click()));
    onView(withId(R.id.textViewError)).check(matches(withText("Username or password not correct.")));

espresso-macchiato

    new EspButton(R.id.buttonConfirm).checkIsDisabled();
    new EspTextView(R.id.textViewUsername).typeText("MyUserName");
    new EspTextView(R.id.textViewPassword).typeText("*****");
    new EspButton(R.id.buttonConfirm).click();
    new EspTextView(R.id.textViewError).checkTextIs("Username or password not correct.");

page object pattern

    loginPage.confirm().checkIsDisabled();
    loginPage.username().typeText("MyUserName")
    loginPage.password().typeText("*****")
    loginPage.confirm().click()
    loginPage.errormessage().checkTextIs("Username or password not correct.")

page implementation

    public class LoginPage {
        public EspButton confirn() {
            return new EspButton(R.id.buttonConfirm);
        }
        public EspTextView username() {
            return new EspTextView(R.id.textViewUsername);
        }
        public EspTextView password() {
            return new EspTextView(R.id.textViewPassword);
        }
        public EspTextView errormessage() {
            return new EspTextView(R.id.textViewError);
        }
    }