/**
 * Validation and stubbing of Intents sent out by the application.
 *
 * Many apps sent Intents to use third party applications but Espresso does only work in your app scope.
 * A solution could be to use UiAutomator to handle third party Activities.
 * But the preferred way is to stub the third party applications and let them report your configured result.
 *
 * For Intent stubbing you must use the {@link android.support.test.espresso.intent.rule.IntentsTestRule} or it will not work.
 * (Necessary dependencies already provided by Espresso Macchiato library)
 *
 * <!-- <pre> -->
 * ```java
 * public class MyTest {
 *  {@literal @}Rule
 *   public IntentsTestRule<MyActivity> activityTestRule = new IntentsTestRule<>(MyActivity.class);
 * }
 * ```
 * <!-- </pre> -->
 *
 * For more details about testing with Intents see [[https://google.github.io/android-testing-support-library/docs/espresso/intents/ Espresso documentation]].
 *
 * A registered stub works for multiple requests until your test is done.
 * You can have multiple stubs registered then the best matching one will be used automatically.
 *
 * @see de.nenick.espressomacchiato.testbase.EspressoIntentTestCase Ready to use test base for Intent stubbing.
 */
package de.nenick.espressomacchiato.intents;