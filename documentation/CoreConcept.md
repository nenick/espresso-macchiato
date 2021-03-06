# Core Concepts Explanation

### Configure Elements to Your Needs

All elements are just prepared with the most common actions and assertions.
This keeps the method count on a low level when you use the code autocompletion.

## Wordings

### Element

Android has a wide set of different kinds of views. In Espresso Macchiato we call them **elements**.
Those elements contain the most common actions and assertions for the matching view. 
See also [Overview Prepared Elements](ElementsPrepared.md) or [Create Custom Elements](ElementsCustom.md)

### Action

Means to perform an interaction with a view e.g. `ViewActions.click()`.
In Espresso you would do something like that ...

`Espresso.onView(Matcher<View>).perform(ViewAction)`

In Espresso Macchiato more something like that ...

`EspButton(Matcher<View>).performAction()`

### Assertion

Means to check an aspect on a view e.g. `ViewMatchers.withText(String)`.
In Espresso you would do something like that ...

`Espresso.onView(Matcher<View>).check(matches(Matcher<View>))`

In Espresso Macchiato more something like that ...

`EspButton(Matcher<View>).checkAssertion()`