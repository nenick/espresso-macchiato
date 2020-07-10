package de.nenick.espressomacchiato.elements;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;

import androidx.test.espresso.Espresso;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

/** Basic tests */
public class EspAlertDialogTest extends EspressoTestCase<BaseActivity> {

    public static final String TITLE = "My Title";
    public static final String MESSAGE = "My Message";
    public static final String OK = "OK";
    public static final String DENY = "Deny";
    public static final String CLICKED_BUTTON = "clicked button: ";
    public static final String CANCEL = "Cancel";
    public static final String DISMISSED = "dismissed";
    private TextView messageView;
    private int messageViewId = android.R.id.text1;
    private DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(DialogInterface dialog, int which) {
            messageView.setText("clicked button: " + which);
        }
    };
    private DialogInterface.OnDismissListener dismissListener = new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            messageView.setText(DISMISSED);
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
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void testDialogDismiss() {
        skipTestIfBelowAndroidMarshmallow();

        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setTitle(TITLE)
                .setMessage(MESSAGE)
                .setPositiveButton(OK, clickListener)
                .setOnDismissListener(dismissListener));

        espAlertDialog.dismiss();

        espAlertDialog.assertNotExist();
        espTextView.assertTextIs(DISMISSED);
    }

    @Test
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void testDialogDismissNonCancelable() {
        skipTestIfBelowAndroidMarshmallow();

        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setTitle(TITLE)
                .setMessage(MESSAGE)
                .setPositiveButton(OK, clickListener)
                .setOnDismissListener(dismissListener)
                .setCancelable(false));

        espAlertDialog.dismiss();
        espAlertDialog.assertIsVisible();
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testSpecObsolete() {
        exception.expect(UnsupportedOperationException.class);
        EspAlertDialog.spec();
    }

    @Test
    public void testTemplateConstructor() {
        espAlertDialog = new EspAlertDialog(espAlertDialog);
        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setTitle(TITLE)
                .setMessage(MESSAGE)
                .setPositiveButton(OK, clickListener));

        espAlertDialog.confirmButton().click();
        Log.e("DIALOG", "check");
        espTextView.assertTextIs(CLICKED_BUTTON + DialogInterface.BUTTON_POSITIVE);
    }

    private void givenClickFeedbackTextView() {
        messageView = new TextView(activityTestRule.getActivity());
        messageView.setId(messageViewId);
        addViewToLayout(messageView, BaseActivity.rootLayout);
    }
}