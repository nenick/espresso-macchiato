package de.nenick.espressomacchiato.elements;

import android.support.test.espresso.NoMatchingViewException;
import android.support.v7.app.ActionBar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import junit.framework.AssertionFailedError;

import org.hamcrest.Matchers;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

public class EspViewTest extends EspressoTestCase<BaseActivity> {

    private static final String VIEW_WAS_CLICKED_MESSAGE = "view was clicked";

    private int viewId = android.R.id.button1;
    private EspView espView = EspView.byId(viewId);
    private Button view;

    private int messageViewId = android.R.id.text1;
    private TextView messageView;
    private EspTextView espTextView = EspTextView.byId(messageViewId);

    @Test
    public void testByAll() {
        givenClickableView();

        espView = EspView.byAll().withId(viewId).withIsDisplayed().build();
        espView.assertIsDisplayedOnScreen();
    }

    @Test
    public void testAssertions() {
        espTextView.assertNotExist();

        givenClickableView();
        espView.assertIsVisible();
        espView.assertIsEnabled();
        espView.assertIsDisplayedOnScreen();

        givenViewIsDisabled();
        espView.assertIsDisabled();

        givenViewIsInvisible();
        espView.assertIsHidden();

        givenViewIsGone();
        espView.assertIsHidden();
    }

    @Test
    public void testAssertIsHiddenWhenNotOnScreen() {
        givenClickableViewNotOnScreen();
        espView.assertIsVisible();
        espView.assertIsEnabled();

        espView.assertIsHidden();
    }

    @Test
    public void testAssertIsDisplayedOnScreenWhenViewLargerThanScreen() {
        givenViewLargerThanScreen();
        espView.assertIsDisplayedOnScreen();
    }

    private void givenViewLargerThanScreen() {
        ScrollView scrollView = new ScrollView(getActivity());
        scrollView.setId(android.R.id.candidatesArea);
        addViewToLayout(scrollView, BaseActivity.rootLayout);

        view = new Button(activityTestRule.getActivity());
        view.setHeight(3000);
        view.setWidth(2000);
        view.setId(viewId);
        addViewToLayout(view, android.R.id.candidatesArea);
    }


    @Test
    public void testAssertIsDisplayedOnScreenFailure() {
        givenViewOutsideOfScreen(android.R.id.text2);
        EspView.byId(android.R.id.text2).assertIsVisible();

        exception.expect(AssertionFailedError.class);
        EspView.byId(android.R.id.text2).assertIsDisplayedOnScreen();
    }

    @Test
    public void testClick() {
        givenClickableView();
        givenClickFeedbackTextView();

        espView.click();
        espTextView.assertTextIs(VIEW_WAS_CLICKED_MESSAGE);
    }

    @Test
    public void testClickFailureWhenNotVisible() {
        exception.expect(NoMatchingViewException.class);
        exception.expectMessage("No views in hierarchy found matching: (with id: android:id/button1 and is displayed on the screen to the user)");

        givenClickableView();
        givenViewIsInvisible();
        espView.click();
    }

    @Test
    public void testClickSelectsOnlyVisibleView() {
        givenClickableView();
        givenViewIsInvisible();
        givenClickableView();
        givenClickFeedbackTextView();

        espView.click();
        espTextView.assertTextIs(VIEW_WAS_CLICKED_MESSAGE);
    }

    @Test
    public void testExtend() {
        givenClickableView();
        givenClickFeedbackTextView();

        MyEspView myEspView = new MyEspView(EspView.byId(viewId));
        myEspView.click();
        espTextView.assertTextIs(VIEW_WAS_CLICKED_MESSAGE);
    }

    private void givenViewIsInvisible() {
        performOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void givenViewIsGone() {
        performOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.GONE);
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

    private void givenClickableView() {
        view = new Button(activityTestRule.getActivity());
        view.setId(viewId);
        addViewToLayout(view, BaseActivity.rootLayout);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageView.setText(VIEW_WAS_CLICKED_MESSAGE);
            }
        });
    }

    private void givenClickableViewNotOnScreen() {
        for (int i=0;i<20;i++) {
            addViewToLayout(new Button(activityTestRule.getActivity()), BaseActivity.rootLayout);
        }
        givenClickableView();
    }

    private void givenClickFeedbackTextView() {
        messageView = new TextView(activityTestRule.getActivity());
        messageView.setId(messageViewId);
        addViewToLayout(messageView, BaseActivity.rootLayout);
    }

    private void givenViewOutsideOfScreen(int id) {
        TextView textView = new TextView(getActivity());
        textView.setHeight(5000);
        addViewToLayout(textView, BaseActivity.rootLayout);

        textView = new TextView(getActivity());
        textView.setId(id);
        addViewToLayout(textView, BaseActivity.rootLayout);
    }

    class MyEspView extends EspView {
        public MyEspView(EspView template) {
            super(template);
        }
    }
}