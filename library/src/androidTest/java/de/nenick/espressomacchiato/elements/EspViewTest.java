package de.nenick.espressomacchiato.elements;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressotools.EspressoTestCase;

public class EspViewTest extends EspressoTestCase<BaseActivity> {

    private static final String VIEW_WAS_CLICKED_MESSAGE = "view was clicked";

    private int viewId = android.R.id.edit;
    private EspView espView = new EspView(viewId);
    private Button view;

    private int messageViewId = android.R.id.text1;
    private TextView messageView;
    private EspTextView espTextView = new EspTextView(messageViewId);

    @Before
    public void setup() {
        view = new Button(activityTestRule.getActivity());
        view.setId(viewId);
        addViewToActivity(view, BaseActivity.linearLayout);

        messageView = new TextView(activityTestRule.getActivity());
        messageView.setId(messageViewId);
        addViewToActivity(messageView, BaseActivity.linearLayout);
    }

    @Test
    public void testAssertions() {
        espView.assertIsVisible();
        espView.assertIsEnabled();

        givenViewIsDisabled();
        espView.assertIsDisabled();

        givenViewIsHidden();
        espView.assertIsHidden();
    }

    @Test
    public void testClick() {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageView.setText(VIEW_WAS_CLICKED_MESSAGE);
            }
        });
        espView.click();
        espTextView.assertTextIs(VIEW_WAS_CLICKED_MESSAGE);
    }

    private void givenViewIsHidden() {
        perform(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void givenViewIsDisabled() {
        perform(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(false);
            }
        });
    }
}