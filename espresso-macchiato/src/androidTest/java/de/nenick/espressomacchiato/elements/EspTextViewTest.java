package de.nenick.espressomacchiato.elements;

import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

public class EspTextViewTest extends EspressoTestCase<BaseActivity> {

    private String textViewText = "my text";
    private int textViewId = android.R.id.text1;
    private EspTextView espTextView = EspTextView.byId(textViewId);

    @Before
    public void setup() {
        TextView textView = new TextView(activityTestRule.getActivity());
        textView.setId(textViewId);
        textView.setText(textViewText);
        textView.setTextColor(0xff000000);
        addViewToLayout(textView, BaseActivity.rootLayout);
    }

    @Test
    public void testAssertTextIs() {
        espTextView.assertTextIs(textViewText);
    }

    @Test
    public void testTemplateConstructor() {
        espTextView = new EspTextView(espTextView);
        espTextView.assertTextIs(textViewText);
    }

    @Test
    public void testByText() {
        espTextView = EspTextView.byText(textViewText);
        espTextView.assertIsDisplayedOnScreen();
    }

    @Test
    public void testByAll() {
        espTextView = EspTextView.byAll().withId(textViewId).withIsDisplayed().build();
        espTextView.assertIsDisplayedOnScreen();
    }

    @Test
    public void testAssertTextColorIs() {
        espTextView.assertTextColorIs(0xff000000);
    }

    @Test
    public void testAssertTextColorResIs() {
        espTextView.assertTextColorResIs(android.R.color.black);
    }
}