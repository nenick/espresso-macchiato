package de.nenick.espressomacchiato.mocks;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;

import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.elements.EspTextView;
import de.nenick.espressomacchiato.test.views.OnActivityResultActivity;
import de.nenick.espressotools.EspressoIntentTestCase;

public class EspContactPickerMockTest extends EspressoIntentTestCase<OnActivityResultActivity> {

    private final static int REQUEST_CODE = 123;
    private EspTextView requestCodeTextView = EspTextView.byId(OnActivityResultActivity.requestCodeResource);
    private EspTextView resultCodeTextView = EspTextView.byId(OnActivityResultActivity.resultCodeResource);
    private EspTextView dataTextView = EspTextView.byId(OnActivityResultActivity.dataResource);
    private OnActivityResultActivity activity;

    private EspContactPickerMock espContactPickerMock = new EspContactPickerMock();

    @Before
    public void setup() {
        activity = activityTestRule.getActivity();
        activity.setListener(new OnActivityResultActivity.OnActivityResultListener() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                activity.setDataViewText(data.getData().toString());
            }
        });
    }

    @Test
    public void testContactPickerMock() {
        Uri dummyContactDataUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, 42);
        espContactPickerMock.registerMockWithData(dummyContactDataUri);

        activity.startForResult(createContactPickerIntent(), REQUEST_CODE);

        requestCodeTextView.assertTextIs(String.valueOf(REQUEST_CODE));
        resultCodeTextView.assertTextIs(String.valueOf(Activity.RESULT_OK));
        dataTextView.assertTextIs(dummyContactDataUri.toString());
    }

    protected Intent createContactPickerIntent() {
        Intent intent = new Intent(activity, OnActivityResultActivity.class);
        intent.setAction(Intent.ACTION_PICK);
        return intent;
    }
}