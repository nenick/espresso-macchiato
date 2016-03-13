package de.nenick.espressomacchiato.elements;

import android.widget.Button;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressotools.EspressoTestCase;

public class EspButtonTest extends EspressoTestCase<BaseActivity> {

    private String buttonText = "my button";
    private int buttonId = android.R.id.button1;
    private EspButton espButton = new EspButton(buttonId);

    @Before
    public void setup() {
        Button button = new Button(activityTestRule.getActivity());
        button.setId(buttonId);
        button.setText(buttonText);
        addViewToActivity(button, BaseActivity.linearLayout);
    }

    @Test
    public void testAssertTextIs() {
        espButton.assertTextIs(buttonText);
    }
}