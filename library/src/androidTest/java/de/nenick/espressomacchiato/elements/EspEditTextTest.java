package de.nenick.espressomacchiato.elements;

import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressotools.EspressoTestCase;

public class EspEditTextTest extends EspressoTestCase<BaseActivity> {

    private int editTextId = android.R.id.edit;
    private EspEditText espEditText = new EspEditText(editTextId);

    @Before
    public void setup() {
        EditText editText = new EditText(activityTestRule.getActivity());
        editText.setId(editTextId);
        addViewToActivity(editText, BaseActivity.linearLayout);
    }

    @Test
    public void testReplaceText() {
        espEditText.assertTextIs("");

        espEditText.replaceText("My Text");
        espEditText.assertTextIs("My Text");

        espEditText.replaceText("Another text");
        espEditText.assertTextIs("Another text");
    }
}