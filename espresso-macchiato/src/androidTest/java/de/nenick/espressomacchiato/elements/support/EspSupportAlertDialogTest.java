package de.nenick.espressomacchiato.elements.support;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.elements.EspTextView;
import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

/** Basic tests */
public class EspSupportAlertDialogTest extends EspressoTestCase<BaseActivity> {

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

    private EspSupportAlertDialog espSupportAlertDialog = EspSupportAlertDialog.build();
    private EspTextView espTextView = EspTextView.byId(messageViewId);

    @Before
    public void setup() {
        givenClickFeedbackTextView();
    }

    @Test
    public void testDialogWithConfirm() {
        espSupportAlertDialog.assertNotExist();
        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setTitle(TITLE)
                .setMessage(MESSAGE)
                .setPositiveButton(OK, clickListener));

        espSupportAlertDialog.assertIsVisible();
        espSupportAlertDialog.title().assertTextIs(TITLE);
        espSupportAlertDialog.message().assertTextIs(MESSAGE);
        espSupportAlertDialog.confirmButton().click();

        espSupportAlertDialog.assertNotExist();
        espTextView.assertTextIs(CLICKED_BUTTON + DialogInterface.BUTTON_POSITIVE);
    }

    @Test
    public void testDialogWithDeny() {
        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setTitle(TITLE)
                .setMessage(MESSAGE)
                .setPositiveButton(OK, clickListener)
                .setNegativeButton(DENY, clickListener));

        espSupportAlertDialog.denyButton().click();

        espSupportAlertDialog.assertNotExist();
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

        espSupportAlertDialog.cancelButton().click();

        espSupportAlertDialog.assertNotExist();
        espTextView.assertTextIs(CLICKED_BUTTON + DialogInterface.BUTTON_NEUTRAL);
    }

    @Test
    public void testDialogDismiss() {
        skipTestIfBelowAndroidMarshmallow();

        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setTitle(TITLE)
                .setMessage(MESSAGE)
                .setPositiveButton(OK, clickListener)
                .setOnDismissListener(dismissListener));

        espSupportAlertDialog.dismiss();

        espSupportAlertDialog.assertNotExist();
        espTextView.assertTextIs(DISMISSED);
    }

    @Test
    public void testDialogDismissNonCancelable() {
        skipTestIfBelowAndroidMarshmallow();

        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setTitle(TITLE)
                .setMessage(MESSAGE)
                .setPositiveButton(OK, clickListener)
                .setOnDismissListener(dismissListener)
                .setCancelable(false));

        espSupportAlertDialog.dismiss();
        espSupportAlertDialog.assertIsVisible();
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testSpecObsolete() {
        exception.expect(UnsupportedOperationException.class);
        EspSupportAlertDialog.spec();
    }

    @Test
    public void testTemplateConstructor() {
        espSupportAlertDialog = new EspSupportAlertDialog(espSupportAlertDialog);

        addDialog(new AlertDialog.Builder(activityTestRule.getActivity())
                .setTitle(TITLE)
                .setMessage(MESSAGE)
                .setPositiveButton(OK, clickListener));

        espSupportAlertDialog.confirmButton().click();
        espTextView.assertTextIs(CLICKED_BUTTON + DialogInterface.BUTTON_POSITIVE);
    }

    private void givenClickFeedbackTextView() {
        messageView = new TextView(activityTestRule.getActivity());
        messageView.setId(messageViewId);
        addViewToLayout(messageView, BaseActivity.rootLayout);
    }
}