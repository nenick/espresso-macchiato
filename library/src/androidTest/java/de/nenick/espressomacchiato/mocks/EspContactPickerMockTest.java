package de.nenick.espressomacchiato.mocks;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.matcher.IntentMatchers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.nenick.espressomacchiato.elements.EspTextView;
import de.nenick.espressomacchiato.test.views.OnActivityResultActivity;
import de.nenick.espressotools.EspressoIntentTestCase;

public class EspContactPickerMockTest extends EspressoIntentTestCase<OnActivityResultActivity> {

    private static final int REQUEST_CODE = 123;
    private static final int CONTACT_ID = 42;
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
        Uri dummyContactDataUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, CONTACT_ID);
        espContactPickerMock.registerMockWithData(dummyContactDataUri);

        activity.startForResult(createContactPickerIntent(), REQUEST_CODE);

        requestCodeTextView.assertTextIs(String.valueOf(REQUEST_CODE));
        resultCodeTextView.assertTextIs(String.valueOf(Activity.RESULT_OK));
        dataTextView.assertTextIs(dummyContactDataUri.toString());
    }

    @Test
    public void testContactPickerMockWithExtraMatcher() {
        Uri dummyContactDataUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, CONTACT_ID);
        espContactPickerMock.registerMockWithData(dummyContactDataUri, IntentMatchers.hasExtraWithKey("MyKey"));

        Intent contactPickerIntent = createContactPickerIntent();
        contactPickerIntent.putExtra("MyKey", "myValue");
        activity.startForResult(contactPickerIntent, REQUEST_CODE);

        requestCodeTextView.assertTextIs(String.valueOf(REQUEST_CODE));
        resultCodeTextView.assertTextIs(String.valueOf(Activity.RESULT_OK));
        dataTextView.assertTextIs(dummyContactDataUri.toString());
    }

    @Test
    @Ignore("result is delivered after all checks are done (tear down)")
    public void testContactPickerMockWhenNotMatching() {
        activity.setListener(new OnActivityResultActivity.OnActivityResultListener() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                activity.setDataViewText(String.valueOf(data.getData()));
            }
        });

        Uri dummyContactDataUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, CONTACT_ID);
        espContactPickerMock.registerMockWithData(dummyContactDataUri, IntentMatchers.hasExtraWithKey("MyKey"));

        activity.startForResult(createContactPickerIntent(), REQUEST_CODE);

        requestCodeTextView.assertTextIs("");
        resultCodeTextView.assertTextIs("");
        dataTextView.assertTextIs("");
    }

    protected Intent createContactPickerIntent() {
        Intent intent = new Intent(activity, OnActivityResultActivity.class);
        intent.setAction(Intent.ACTION_PICK);
        return intent;
    }
}