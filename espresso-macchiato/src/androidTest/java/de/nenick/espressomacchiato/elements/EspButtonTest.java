package de.nenick.espressomacchiato.elements;

import android.widget.Button;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

/** Basic tests */
public class EspButtonTest extends EspressoTestCase<BaseActivity> {

    private String buttonText = "my button";
    private int buttonId = android.R.id.button1;
    private EspButton espButton = EspButton.byId(buttonId);

    @Before
    public void setup() {
        Button button = new Button(activityTestRule.getActivity());
        button.setId(buttonId);
        button.setText(buttonText);
        addViewToLayout(button, BaseActivity.rootLayout);
    }

    @Test
    public void testAssertTextIs() {
        espButton.assertTextIs(buttonText);
    }

    @Test
    public void testByText() {
        espButton = EspButton.byText(buttonText);
        espButton.assertIsVisible();
    }

    @Test
    public void testTemplateConstructor() {
        EspButton template = EspButton.byText(buttonText);
        this.espButton = new EspButton(template);
        this.espButton.assertIsVisible();
    }
}