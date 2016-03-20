[![Circle CI](https://img.shields.io/circleci/project/nenick/espresso-macchiato/master.svg)](https://circleci.com/gh/nenick/espresso-macchiato)
[![Codacy Badge](https://img.shields.io/codacy/c6cc5e2303234780b4ae3118d93eb35f/master.svg)](https://www.codacy.com/app/nico_kuechler/espresso-macchiato)
[![codecov.io](https://img.shields.io/codecov/c/github/nenick/espresso-macchiato/master.svg)](https://codecov.io/github/nenick/espresso-macchiato?branch=master)
[![Download](https://api.bintray.com/packages/nenick/maven/espresso-macchiato/images/download.svg)](https://bintray.com/nenick/maven/espresso-macchiato/_latestVersion)

<!---
We use original bintray badged until issue is fixed https://github.com/badges/shields/issues/658
[![Download](https://img.shields.io/bintray/v/nenick/maven/espresso-macchiato.svg)](https://bintray.com/nenick/maven/espresso-macchiato/_latestVersion)
-->


# espresso-macchiato
*A dollop of foamed milk with the texture of melted ice cream.*

repository

    maven { url = "https://dl.bintray.com/nenick/maven/" }

dependency

    androidTestCompile ('de.nenick:espresso-macchiato:0.1.1@aar')


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

    EspButton.byId(R.id.buttonConfirm).checkIsDisabled();
    EspEditText.byId(R.id.editTextUsername).replaceText("MyUserName");
    EspEditText.byId(R.id.editTextPassword).replaceText("*****");
    EspButton.byId(R.id.buttonConfirm).click();
    EspTextView.byId(R.id.textViewErrorMessage).checkTextIs("Username or password not correct.");

page object pattern

    loginPage.confirm().assertIsDisabled();
    loginPage.username().replaceText("MyUserName");
    loginPage.password().replaceText("*****");
    loginPage.confirm().click();
    loginPage.errorMessage().assertTextIs("Username or password not correct.");

page implementation

    public class LoginPage {

        public EspButton confirn() {
            return EspButton.byId(R.id.buttonConfirm);
        }

        public EspEditText username() {
            return EspTextView.byId(R.id.textViewUsername);
        }

        public EspEditText password() {
            return EspTextView.byId(R.id.textViewPassword);
        }

        public EspTextView errorMessage() {
            return EspTextView.byId(R.id.textViewErrorMessage);
        }
    }

## Contribute

Always write some tests to prove the functionality and stability for new elements.

### create release

Finish RELEASE_NOTES.md and run following command from your PC (change version!):

    ./gradlew :library:release -Prelease.version=1.2.0

This command will do and trigger:

1. do: check current git status for not synchronized changes
2. do: create new release tag with given release.version
3. do: push release tag to github
4. trigger: circle ci to build and test the release tag
5. trigger: circle ci to push the release artifacts to github
6. trigger: circle ci to push the release artifacts to bintray

With the last step the release is ready and can be used.