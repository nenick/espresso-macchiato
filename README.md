[![Circle CI](https://img.shields.io/circleci/project/nenick/espresso-macchiato/master.svg)](https://circleci.com/gh/nenick/espresso-macchiato)
[![Codacy Badge](https://img.shields.io/codacy/c6cc5e2303234780b4ae3118d93eb35f/master.svg)](https://www.codacy.com/app/nico_kuechler/espresso-macchiato)
[![codecov.io](https://img.shields.io/codecov/c/github/nenick/espresso-macchiato/master.svg)](https://codecov.io/github/nenick/espresso-macchiato?branch=master)
[![Download](https://img.shields.io/bintray/v/nenick/maven/espresso-macchiato.svg)](https://bintray.com/nenick/maven/espresso-macchiato/_latestVersion)

# Espresso Macchiato

Collection of fluent styled Android Espresso page object pattern elements and testing tools.

*Espresso Macchiato: A dollop of foamed milk with the texture of melted ice cream.*

* [The idea](#theidea)
* [Plain espresso example vs page object pattern](#espressoexample)
* [Add espresso macchiato library to your project](#addlibrary)
* [Contribute](#contribute)

<a name="theidea"></a>
## The idea

**Main target is to hide most of the hamcrest style checks and actions behind a fluent style.**
The hamcrest style is not easy to read and not very intuitive to use compared with a fluent style.
So the learning phase for testing with espresso may take a long time.
Specially when you miss some experience with hamcrest, espresso and common android layouts (NavigationView, ActionBar).

**Page Pattern:** The current visible screen is called a page.
Each page has specific elements (Drawer, Button, List, etc).
And each element has different actions (click, check, scroll, etc).

**Second target is to provide some useful tools for testing.**
Tools like clear your application data, contacts, permissions, etc.

<a name="espressoexample"></a>
## Plain espresso example vs page object pattern

Simple example test protocol for login process:

* Login button is initial disabled
* User type his name
* User type wrong password
* User click login button
* Error message is shown

Test done with plane Android Espresso

    onView(withId(R.id.buttonConfirm)).check(isDisabled()));
    onView(withId(R.id.editTextUsername)).perform(typeText("MyUserName"));
    onView(withId(R.id.editTextPassword)).perform(typeText("*****"));
    onView(withId(R.id.buttonConfirm)).perform(click()));
    onView(withId(R.id.textViewErrorMessage)).check(matches(withText("Username or password not correct.")));

Test done with Espresso Macchiato

    EspButton.byId(R.id.buttonConfirm).checkIsDisabled();
    EspEditText.byId(R.id.editTextUsername).replaceText("MyUserName");
    EspEditText.byId(R.id.editTextPassword).replaceText("*****");
    EspButton.byId(R.id.buttonConfirm).click();
    EspTextView.byId(R.id.textViewErrorMessage).checkTextIs("Username or password not correct.");

Test done with page object pattern

    loginPage.confirm().assertIsDisabled();
    loginPage.username().replaceText("MyUserName");
    loginPage.password().replaceText("*****");
    loginPage.confirm().click();
    loginPage.errorMessage().assertTextIs("Username or password not correct.");

Page implementation with Espresso Macchiato

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

<a name="addlibrary"></a>
## Add espresso macchiato library to your project

add custom repository

    maven { url = "https://dl.bintray.com/nenick/maven/" }

add test dependency

    // see bintray badged for latest version
    androidTestCompile ('de.nenick:espresso-macchiato:x.x.x')

add test dependency when your need permission handling

    androidTestCompile 'com.android.support.test.uiautomator:uiautomator-v18:2.1.2'

<a name="contribute"></a>
## Contribute

Always write some tests to prove the functionality and stability for new elements and tools.

#### Check before you push

Run basic tests to have a high chance that automatic build and test will not fail.

    ./test-rpoject.sh

#### Create release

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
Now its time to update this README file.