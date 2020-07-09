package de.nenick.espressomacchiato.elements;

import android.content.Intent;
import android.content.res.Configuration;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import junit.framework.AssertionFailedError;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.test.views.LandscapeFixedActivity;
import de.nenick.espressomacchiato.test.views.LongListActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Basic tests */
public class EspDeviceTest extends EspressoTestCase<BaseActivity> {

    private EspDevice espDevice = new EspDevice();

    private final static String nextPageButtonText = "next activity";
    private final static int nextPageButtonId = android.R.id.button1;
    private final static int editTextId = android.R.id.edit;

    private EspButton espButton = EspButton.byId(nextPageButtonId);
    private EspEditText espEditText = EspEditText.byId(editTextId);
    private EspPage startPage = EspPage.byId(BaseActivity.rootLayout);
    private EspPage nextPage = EspPage.byId(LandscapeFixedActivity.rootLayout);

    private int currentScreenSize = InstrumentationRegistry.getContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

    @Test
    public void setup() {
        // emulator with older android version (e.g. v8) has initial landscape mode
        // but we expect orientation is portrait mode
        espDevice.rotateToPortrait();
    }

    @After
    public void reset() {
        espDevice.rotateToPortrait();
        espDevice.closeSoftKeyboard();
    }

    @Test
    public void testRotate() {
        espDevice.assertOrientationIsPortrait();

        espDevice.rotateToLandscape();
        espDevice.assertOrientationIsLandscape();

        espDevice.rotateToPortrait();
        espDevice.assertOrientationIsPortrait();
    }

    @Test
    public void testRotateWithDialog() {
        addDialog(new AlertDialog.Builder(getActivity()));
        espDevice.rotateToLandscape();
        espDevice.assertOrientationIsLandscape();
    }

    @Test
    @Ignore("change orientation works only for current activity but not following activities")
    public void testKeepRotation() {
        espDevice.assertOrientationIsPortrait();
        espDevice.rotateToLandscape();
        espDevice.assertOrientationIsLandscape();

        getActivity().startActivity(new Intent(getActivity(), LongListActivity.class));

        espDevice.assertOrientationIsLandscape();
    }

    @Test
    public void testAssertOrientationIsPortraitFailure() {
        exception.expect(AssertionFailedError.class);
        exception.expectMessage(is("expected device orientation PORTRAIT but was LANDSCAPE"));

        espDevice.rotateToLandscape();
        espDevice.assertOrientationIsPortrait();
    }

    @Test
    public void testAssertOrientationIsLandscapeFailure() {
        exception.expect(AssertionFailedError.class);
        exception.expectMessage(is("expected device orientation LANDSCAPE but was PORTRAIT"));

        espDevice.rotateToPortrait();
        espDevice.assertOrientationIsLandscape();
    }

    @Test
    public void testClickBackButton() {
        givenButtonToStartNextPage();

        startPage.assertIsVisible();
        espButton.click();
        nextPage.assertIsVisible();
        espDevice.clickBackButton();
        startPage.assertIsVisible();
    }

    @Test
    public void testCloseSoftKeyboard() {
        givenEditTextToOpenSoftKeyboard();
        espDevice.assertSoftKeyboardIsClosed();

        espEditText.click();
        espDevice.assertSoftKeyboardIsOpen();

        espDevice.closeSoftKeyboard();
        espDevice.assertSoftKeyboardIsClosed();
    }

    @Test
    public void testAssertSoftKeyboardIsOpenFailure() {
        exception.expect(AssertionFailedError.class);
        exception.expectMessage(containsString("Keyboard should be open."));
        exception.expectMessage(containsString("Expected: is <true>"));
        exception.expectMessage(containsString("Got: <false>"));

        espDevice.assertSoftKeyboardIsOpen();
    }

    @Test
    public void testAssertSoftKeyboardIsClosedFailure() {
        exception.expect(AssertionFailedError.class);
        exception.expectMessage(containsString("Keyboard should be closed."));
        exception.expectMessage(containsString("Expected: is <true>"));
        exception.expectMessage(containsString("Got: <false>"));

        givenEditTextToOpenSoftKeyboard();
        // other test works and difference is this extra check for initial closed.
        espDevice.assertSoftKeyboardIsClosed();

        espEditText.click();
        espDevice.assertSoftKeyboardIsClosed();
    }

    @Test
    public void testIsScreenSizeEqualTo() {
        assertTrue(espDevice.isScreenSizeEqualTo(currentScreenSize));
        assertFalse(espDevice.isScreenSizeEqualTo(currentScreenSize + 1));
    }

    @Test
    public void testIsScreenSizeAtLeast() {
        assertTrue(espDevice.isScreenSizeAtLeast(currentScreenSize));
        assertTrue(espDevice.isScreenSizeAtLeast(currentScreenSize - 1));
        assertFalse(espDevice.isScreenSizeAtLeast(currentScreenSize + 1));
    }

    @Test
    public void testIsScreenSizeAtMost() {
        assertTrue(espDevice.isScreenSizeAtMost(currentScreenSize));
        assertTrue(espDevice.isScreenSizeAtMost(currentScreenSize + 1));
        assertFalse(espDevice.isScreenSizeAtMost(currentScreenSize - 1));
    }

    private void givenButtonToStartNextPage() {
        Button button = new Button(activityTestRule.getActivity());
        button.setText(nextPageButtonText);
        button.setId(nextPageButtonId);
        addViewToLayout(button, BaseActivity.rootLayout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activityTestRule.getActivity(), LandscapeFixedActivity.class);
                activityTestRule.getActivity().startActivity(intent);
            }
        });
    }

    private void givenEditTextToOpenSoftKeyboard() {
        EditText editText = new EditText(activityTestRule.getActivity());
        editText.setId(editTextId);
        addViewToLayout(editText, BaseActivity.rootLayout);
    }
}
