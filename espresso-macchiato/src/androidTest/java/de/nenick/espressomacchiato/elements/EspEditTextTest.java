package de.nenick.espressomacchiato.elements;

import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EspEditTextTest extends EspressoTestCase<BaseActivity> {

    public static final String INITIAL_TEXT = "My Text";
    public static final String CHANGED_TEXt = "Another text";
    public static final String HINT_TEXT = "just for testing";

    private int editTextId = android.R.id.edit;
    private EspEditText espEditText = EspEditText.byId(editTextId);

    @Before
    public void setup() {
        EditText editText = new EditText(activityTestRule.getActivity());
        editText.setId(editTextId);
        editText.setHint(HINT_TEXT);
        addViewToLayout(editText, BaseActivity.rootLayout);
    }

    @Test
    public void testByText() {
        espEditText.replaceText(INITIAL_TEXT);
        espEditText = EspEditText.byText(INITIAL_TEXT);
        espEditText.assertIsDisplayedOnScreen();
    }

    @Test
    public void testByAll() {
        espEditText = EspEditText.byAll().withId(editTextId).build();
        espEditText.assertIsDisplayedOnScreen();
    }

    @Test
    public void testReplaceText() {
        espEditText.assertTextIs("");

        espEditText.replaceText(INITIAL_TEXT);
        espEditText.assertTextIs(INITIAL_TEXT);

        espEditText.replaceText(CHANGED_TEXt);
        espEditText.assertTextIs(CHANGED_TEXt);
    }

    @Test
    public void testHint() {
        espEditText.assertHintTextIs(HINT_TEXT);
    }

    @Test
    public void testCustomBaseMatcher() {
        espEditText.replaceText(INITIAL_TEXT);
        espEditText = new EspEditText(withText(INITIAL_TEXT));
        espEditText.assertIsVisible();
    }

    @Test
    public void testTemplateConstructor() {
        espEditText = new EspEditText(espEditText);
        espEditText.assertIsVisible();
    }
}