# Create Custom Elements

The default element setup contains only the most common action and assertions which may be not
enough for you. Or this library does not contain a matching element for a specific android view.

### Prepared Actions and Assertions

We provide a wide variety of prepared actions and assertions which you can use easily to create
our own elements to our needs. See also [Actions and Assertions]()

```kotlin
class MyEditText : EspView(R.id.myEditText), TextActions, TextAssertions

MyEditText().checkText("Hello Espresso Macchiato")
```

Also there is a "short" way to build your view element to your needs. But it does only work if
the anonymous class is inside the same file as the test code. Otherwise the test code does
not know the added interface methods. For code sharing use the the sub class style, 
not the object style.

```kotlin
val myEditText = object : EspView(R.id.something), TextActions, TextAssertions {}

myEditText.checkText("Hello Espresso Macchiato")
```

### Custom Actions and Assertions

You can also write your own actions and assertions additionally to the prepared ones.

```kotlin
class MyEditText : EspView(R.id.myEditText) {
    // Do just "anything" with plain Espresso view interactions.
    fun performTypeText(text: String) = onView().perform(ViewActions.typeText(text))
    fun checkIsDisplayed() = onView().check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    // Actions with the convenience method perform(ViewAction).
    fun performClick() = perform(ViewActions.click())

    // Assertions with the convenience method check(Matcher<View>).
    fun checkText(text: String) = check(ViewMatchers.withText(text))
}

MyEditText().apply {
    checkIsDisplayed()
    performClick()
    performTypeText("Hello Espresso Macchiato")
    checkText("Hello Espresso Macchiato")
}
```

### Lambda Receiver Style

It looks also very nice and reduce boilerplate when you prepare the elements for the lambda receiver style.

```kotlin
class MyView(block: MyView.() -> Unit) : EspView(R.id.myView) {
    init { block(this) }
}

MyView {
    checkSomeAssertion()
    performSomeAction()
}

```

Also, it's possible to reduce a little of the boilerplate for custom elements. Code checks will 
complain about this style but yet the "possible" issues didn't occur with this library.

```kotlin
@Suppress("UNCHECKED_CAST")
class MyView(interactions: MyView.() -> Unit) : EspView(R.id.myView, interactions as EspView.() -> Unit)

MyView {
    checkSomeAssertion()
    performSomeAction()
}
```

### Elements as Matcher

Element could also extended to be a matcher by self and can then be used everywhere, where you need a 
view matcher.

```kotlin
class MatchableView(viewMatcher: Matcher<View>) : EspView, Matcher<View> by delegate viewMatcher, PositionAssertions
val myLeftView = MatchableView(R.id.myLeft)
val myRightView = MatchableView(R.id.myRight)
myRightView.checkIsRightOf(myLeftView)
```