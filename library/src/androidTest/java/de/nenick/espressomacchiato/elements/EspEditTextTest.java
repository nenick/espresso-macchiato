package de.nenick.espressomacchiato.elements;

import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressotools.EspressoTestCase;

import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EspEditTextTest extends EspressoTestCase<BaseActivity> {

    public static final String INTIAL_TEXT = "My Text";
    public static final String CHANGED_TEXt = "Another text";
    private int editTextId = android.R.id.edit;
    private EspEditText espEditText = EspEditText.byId(editTextId);

    @Before
    public void setup() {
        EditText editText = new EditText(activityTestRule.getActivity());
        editText.setId(editTextId);
        addViewToActivity(editText, BaseActivity.rootLayout);
    }

    @Test
    public void testReplaceText() {
        espEditText.assertTextIs("");

        espEditText.replaceText(INTIAL_TEXT);
        espEditText.assertTextIs(INTIAL_TEXT);

        espEditText.replaceText(CHANGED_TEXt);
        espEditText.assertTextIs(CHANGED_TEXt);
    }

    @Test
    public void testCustomBaseMatcher() {
        espEditText.replaceText(INTIAL_TEXT);
        espEditText = new EspEditText(withText(INTIAL_TEXT));
        espEditText.assertIsVisible();
    }
}