package de.nenick.espressomacchiato.elements;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.test.views.LandscapeFixedActivity;
import de.nenick.espressotools.EspressoTestCase;

import static org.hamcrest.CoreMatchers.is;

public class EspDeviceTest extends EspressoTestCase<BaseActivity> {

    private EspDevice espDevice = new EspDevice();

    private final static String nextPageButtonText = "next activity";
    private final static int nextPageButtonId = android.R.id.button1;

    private EspButton espButton = new EspButton(nextPageButtonId);
    private EspPage startPage = new EspPage(BaseActivity.rootLayout);
    private EspPage nextPage = new EspPage(LandscapeFixedActivity.rootLayout);

    @Test
    public void testRotate() {
        espDevice.assertOrientationIsPortrait();

        espDevice.rotateToLandscape();
        espDevice.assertOrientationIsLandscape();

        espDevice.rotateToPortrait();
        espDevice.assertOrientationIsPortrait();
    }

    @Test
    public void testAssertOrientationIsPortraitFailure() {
        exception.expect(AssertionError.class);
        exception.expectMessage(is("expected device orientation PORTRAIT but was LANDSCAPE"));

        espDevice.rotateToLandscape();
        espDevice.assertOrientationIsPortrait();
    }

    @Test
    public void testAssertOrientationIsLandscapeFailure() {
        exception.expect(AssertionError.class);
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

    private void givenButtonToStartNextPage() {
        Button button = new Button(activityTestRule.getActivity());
        button.setText(nextPageButtonText);
        button.setId(nextPageButtonId);
        addViewToActivity(button, BaseActivity.rootLayout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activityTestRule.getActivity(), LandscapeFixedActivity.class);
                activityTestRule.getActivity().startActivity(intent);
            }
        });
    }
}
