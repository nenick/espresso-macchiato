package de.nenick.espressomacchiato.assertions;

import android.graphics.Rect;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.util.Log;
import android.view.View;

import de.nenick.espressomacchiato.elements.EspDevice;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class KeyboardAssertion implements ViewAssertion {
    private final boolean expectIsShown;

    public KeyboardAssertion(boolean expectIsShown) {
        this.expectIsShown = expectIsShown;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        boolean isShown = calculateIsKeyboardShown(view);
        if (isShown) {
            assertThat("Keyboard should be closed.", expectIsShown, is(true));
        } else {
            assertThat("Keyboard should be open.", !expectIsShown, is(true));
        }
    }

    protected boolean calculateIsKeyboardShown(View view) {
        // original solution http://stackoverflow.com/a/26964010/3619179
        Rect visibleFrameSpace = new Rect();
        view.getWindowVisibleDisplayFrame(visibleFrameSpace);
        int screenHeight = view.getHeight();
        int keypadHeight = screenHeight - visibleFrameSpace.bottom;

        // 0.15 ratio is perhaps enough to determine keypad height
        double keypadMinHeight = screenHeight * 0.15;
        Log.v(EspDevice.class.getSimpleName(), "expected min keyboard height " + keypadMinHeight + ", calculated keypadHeight is " + keypadHeight);
        return keypadHeight > keypadMinHeight;
    }
}
