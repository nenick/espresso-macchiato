package de.nenick.espressomacchiato.elements;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressotools.EspressoTestCase;

public class EspAlertDialogTest extends EspressoTestCase<BaseActivity> {

    public static final String TITLE = "My Title";
    public static final String MESSAGE = "My Message";
    public static final String OK = "OK";
    public static final String DENY = "Deny";
    public static final String CLICKED_BUTTON = "clicked button: ";
    public static final String CANCEL = "Cancel";
    private TextView messageView;
    private int messageViewId = android.R.id.text1;
    private DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(DialogInterface dialog, int which) {
            messageView.setText("clicked button: " + which);
        }
    };

    private EspAlertDialog espAlertDialog = EspAlertDialog.build();
    private EspTextView  espTextView = EspTextView.byId(messageViewId);

    @Before
    public void setup() {
        givenClickFeedbackTextView();
    }

    @Test
    public void testDialogWithConfirm() {
        espAlertDialog.assertNotExist();
        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setTitle(TITLE)
                .setMessage(MESSAGE)
                .setPositiveButton(OK, clickListener));

        espAlertDialog.assertIsVisible();
        espAlertDialog.title().assertTextIs(TITLE);
        espAlertDialog.message().assertTextIs(MESSAGE);
        espAlertDialog.confirmButton().click();

        espAlertDialog.assertNotExist();
        espTextView.assertTextIs(CLICKED_BUTTON + DialogInterface.BUTTON_POSITIVE);
    }

    @Test
    public void testDialogWithDeny() {
        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setTitle(TITLE)
                .setMessage(MESSAGE)
                .setPositiveButton(OK, clickListener)
                .setNegativeButton(DENY, clickListener));

        espAlertDialog.denyButton().click();

        espAlertDialog.assertNotExist();
        espTextView.assertTextIs(CLICKED_BUTTON + DialogInterface.BUTTON_NEGATIVE);
    }

    @Test
    public void testDialogWithCancel() {
        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setTitle(TITLE)
                .setMessage(MESSAGE)
                .setPositiveButton(OK, clickListener)
                .setNeutralButton(CANCEL, clickListener)
                .setNegativeButton(DENY, clickListener));

        espAlertDialog.cancelButton().click();

        espAlertDialog.assertNotExist();
        espTextView.assertTextIs(CLICKED_BUTTON + DialogInterface.BUTTON_NEUTRAL);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testSpecObsolete() {
        exception.expect(UnsupportedOperationException.class);
        EspAlertDialog.spec();
    }

    protected void addDialog(final AlertDialog.Builder dialog) {
        performOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });
    }

    private void givenClickFeedbackTextView() {
        messageView = new TextView(activityTestRule.getActivity());
        messageView.setId(messageViewId);
        addViewToActivity(messageView, BaseActivity.rootLayout);
    }
}