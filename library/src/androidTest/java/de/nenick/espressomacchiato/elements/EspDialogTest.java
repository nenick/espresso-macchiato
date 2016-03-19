package de.nenick.espressomacchiato.elements;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressotools.EspressoTestCase;

public class EspDialogTest extends EspressoTestCase<BaseActivity> {

    private TextView messageView;
    private int messageViewId = android.R.id.text1;
    private DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(DialogInterface dialog, int which) {
            messageView.setText("clicked button: " + which);
        }
    };

    private EspDialogOneButton espDialogOneButton = EspDialog.bySpec(android.support.design.R.id.parentPanel, android.support.design.R.id.alertTitle, android.R.id.message, android.R.id.button1);
    private EspDialogTwoButtons espDialogTwoButtons = EspDialog.bySpec(android.support.design.R.id.parentPanel, android.support.design.R.id.alertTitle, android.R.id.message, android.R.id.button1, android.R.id.button2);
    private EspDialogThreeButtons espThreeButtonsDialog = EspDialog.bySpec(android.support.design.R.id.parentPanel, android.support.design.R.id.alertTitle, android.R.id.message, android.R.id.button1, android.R.id.button2, android.R.id.button3);
    private EspTextView  espTextView = EspTextView.byId(messageViewId);

    @Before
    public void setup() {
        givenClickFeedbackTextView();
    }

    @Test
    public void testDialogOnButton() {
        espDialogOneButton.assertNotExist();
        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setTitle("My Title")
                .setMessage("My Message")
                .setPositiveButton("OK", clickListener));

        espDialogOneButton.assertIsVisible();
        espDialogOneButton.assertTitleTextIs("My Title");
        espDialogOneButton.assertMessageTextIs("My Message");
        espDialogOneButton.confirmButton().click();

        espDialogOneButton.assertNotExist();
        espTextView.assertTextIs("clicked button: " + DialogInterface.BUTTON_POSITIVE);
    }

    @Test
    public void testDialogTwoButtons() {
        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setTitle("My Title")
                .setMessage("My Message")
                .setPositiveButton("OK", clickListener)
                .setNegativeButton("Deny", clickListener));

        espDialogTwoButtons.denyButton().click();

        espDialogOneButton.assertNotExist();
        espTextView.assertTextIs("clicked button: " + DialogInterface.BUTTON_NEGATIVE);
    }

    @Test
    public void testDialogThreeButtons() {
        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setTitle("My Title")
                .setMessage("My Message")
                .setPositiveButton("OK", clickListener)
                .setNeutralButton("Cancel", clickListener)
                .setNegativeButton("Deny", clickListener));

        espThreeButtonsDialog.cancelButton().click();

        espDialogOneButton.assertNotExist();
        espTextView.assertTextIs("clicked button: "+ DialogInterface.BUTTON_NEUTRAL);
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