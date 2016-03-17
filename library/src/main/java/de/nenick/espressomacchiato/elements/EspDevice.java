package de.nenick.espressomacchiato.elements;

import android.graphics.Rect;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.view.View;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static de.nenick.espressomacchiato.actions.OrientationChangeAction.orientationLandscape;
import static de.nenick.espressomacchiato.actions.OrientationChangeAction.orientationPortrait;
import static de.nenick.espressomacchiato.assertions.OrientationAssertion.isOrientationLandscape;
import static de.nenick.espressomacchiato.assertions.OrientationAssertion.isOrientationPotrait;
import static org.hamcrest.CoreMatchers.is;

/**
 * Actions you can do with android devices.
 */
public class EspDevice {

    public static EspDevice root() {
        return new EspDevice();
    }

    /**
     * Click androids back button.
     * <p>
     * This is not the up button from an ActionBar.
     */
    public void clickBackButton() {
        Espresso.pressBack();
    }

    /**
     * Rotate screen to portrait.
     * <p>
     * Orientation is only changed if activity does not specify android:screenOrientation.
     */
    public void rotateToPortrait() {
        onView(isRoot()).perform(orientationPortrait());
    }

    /**
     * Rotate screen to landscape.
     * <p>
     * Orientation is only changed if activity does not specify android:screenOrientation.
     */
    public void rotateToLandscape() {
        onView(isRoot()).perform(orientationLandscape());
    }

    /**
     * Check if activity orientation is portrait.
     */
    public void assertOrientationIsPortrait() {
        onView(isRoot()).check(isOrientationPotrait());
    }

    /**
     * Check if activity orientation is landscape.
     */
    public void assertOrientationIsLandscape() {
        onView(isRoot()).check(isOrientationLandscape());
    }

    /**
     * Close the soft keyboard is visible.
     */
    public void closeSoftKeyboard() {
        onView(isRoot()).perform(ViewActions.closeSoftKeyboard());
    }

    /**
     * Check soft keyboard is open.
     */
    public void assertSoftKeyboardIsOpen() {
        assertSoftKeyboardIsOpen(true);
    }

    /**
     * Check soft keyboard is closed.
     */
    public void assertSoftKeyboardIsClosed() {
        assertSoftKeyboardIsOpen(false);
    }

    protected void assertSoftKeyboardIsOpen(final boolean isShown) {
        // original solution http://stackoverflow.com/a/26964010/3619179
        onView(isRoot()).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                Rect visibleFrameSpace = new Rect();
                view.getWindowVisibleDisplayFrame(visibleFrameSpace);
                int screenHeight = view.getHeight();
                int keypadHeight = screenHeight - visibleFrameSpace.bottom;

                // 0.15 ratio is perhaps enough to determine keypad height.
                if (keypadHeight > screenHeight * 0.15) {
                    assertThat("Keyboard should be closed.", isShown, is(true));
                }
                else {
                    assertThat("Keyboard should be open.", !isShown, is(true));
                }
            }
        });
    }
}
