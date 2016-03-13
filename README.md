[![Circle CI](https://circleci.com/gh/nenick/espresso-macchiato.svg?style=shield)](https://circleci.com/gh/nenick/espresso-macchiato)
[![Codacy Badge](https://api.codacy.com/project/badge/grade/c6cc5e2303234780b4ae3118d93eb35f)](https://www.codacy.com/app/nico_kuechler/espresso-macchiato)
[![codecov.io](https://codecov.io/github/nenick/espresso-macchiato/coverage.svg?branch=master)](https://codecov.io/github/nenick/espresso-macchiato?branch=master)

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
    onView(withId(R.id.editTextUsername)).perform(typeText("MyUserName"));
    onView(withId(R.id.editTextPassword)).perform(typeText("*****"));
    onView(withId(R.id.buttonConfirm)).perform(click()));
    onView(withId(R.id.textViewErrorMessage)).check(matches(withText("Username or password not correct.")));

espresso-macchiato

    new EspButton(R.id.buttonConfirm).checkIsDisabled();
    new EspEditText(R.id.editTextUsername).replaceText("MyUserName");
    new EspEditText(R.id.editTextPassword).replaceText("*****");
    new EspButton(R.id.buttonConfirm).click();
    new EspTextView(R.id.textViewErrorMessage).checkTextIs("Username or password not correct.");

page object pattern

    loginPage.confirm().assertIsDisabled();
    loginPage.username().replaceText("MyUserName");
    loginPage.password().replaceText("*****");
    loginPage.confirm().click();
    loginPage.errorMessage().assertTextIs("Username or password not correct.");

page implementation

    public class LoginPage {

        public EspButton confirn() {
            return new EspButton(R.id.buttonConfirm);
        }

        public EspEditText username() {
            return new EspTextView(R.id.textViewUsername);
        }

        public EspEditText password() {
            return new EspTextView(R.id.textViewPassword);
        }

        public EspTextView errorMessage() {
            return new EspTextView(R.id.textViewErrorMessage);
        }
    }

## Contribute

### create release

Finish RELEASE_NOTES.md and run following command from your PC (change version!):

    ./gradlew :library:release -Prelease.version=1.2.0

This command will do and trigger:

1. do: check current git status for not synchronized changes
2. do: create new release tag with given release.version
3. do: push release tag to github
4. trigger: circle ci to build and test the release tag
5. trigger: circle ci to push the release artifacts to bintray
5. trigger: circle ci to push the release artifacts to github

With the last step the release is ready and can be used.