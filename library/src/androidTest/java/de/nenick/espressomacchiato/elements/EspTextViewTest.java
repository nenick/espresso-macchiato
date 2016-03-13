package de.nenick.espressomacchiato.elements;

import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressotools.EspressoTestCase;

public class EspTextViewTest extends EspressoTestCase<BaseActivity> {

    private String textViewText = "my text";
    private int textViewId = android.R.id.text1;
    private EspTextView espTextView = new EspTextView(textViewId);

    @Before
    public void setup() {
        TextView textView = new TextView(activityTestRule.getActivity());
        textView.setId(textViewId);
        textView.setText(textViewText);
        addViewToActivity(textView, BaseActivity.rootLayout);
    }

    @Test
    public void testReplaceText() {
        espTextView.assertTextIs(textViewText);
    }
}