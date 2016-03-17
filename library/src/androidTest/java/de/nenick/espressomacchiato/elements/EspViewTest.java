package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.PerformException;
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
    private EspView espView = EspView.byId(viewId);
    private Button view;

    private int messageViewId = android.R.id.text1;
    private TextView messageView;
    private EspTextView espTextView = EspTextView.byId(messageViewId);

    @Before
    public void setup() {
        givenTestViewAndClickFeedbackTextView();
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
        espView.click();
        espTextView.assertTextIs(VIEW_WAS_CLICKED_MESSAGE);
    }

    @Test
    public void testClickFailureWhenNotVisible() {
        exception.expect(PerformException.class);
        exception.expectMessage("Error performing 'single click' on view 'with id: android:id/edit'.");

        view.setVisibility(View.INVISIBLE);
        espView.click();
    }

    private void givenViewIsHidden() {
        performOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void givenViewIsDisabled() {
        performOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(false);
            }
        });
    }

    private void givenTestViewAndClickFeedbackTextView() {
        view = new Button(activityTestRule.getActivity());
        view.setId(viewId);
        addViewToActivity(view, BaseActivity.rootLayout);

        messageView = new TextView(activityTestRule.getActivity());
        messageView.setId(messageViewId);
        addViewToActivity(messageView, BaseActivity.rootLayout);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageView.setText(VIEW_WAS_CLICKED_MESSAGE);
            }
        });
    }
}