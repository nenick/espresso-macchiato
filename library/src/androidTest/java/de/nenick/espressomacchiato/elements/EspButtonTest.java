package de.nenick.espressomacchiato.elements;

import android.support.test.InstrumentationRegistry;
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
        final Button button = new Button(activityTestRule.getActivity());
        button.setId(buttonId);
        button.setText(buttonText);

        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                activityTestRule.getActivity().addContentView(button, null);
            }
        });
    }

    @Test
    public void testAssertTextIs() {
        espButton.assertTextIs(buttonText);
    }
}