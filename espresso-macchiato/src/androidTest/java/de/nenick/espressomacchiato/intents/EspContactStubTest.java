package de.nenick.espressomacchiato.intents;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import androidx.test.espresso.intent.matcher.IntentMatchers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.nenick.espressomacchiato.elements.EspTextView;
import de.nenick.espressomacchiato.test.views.OnActivityResultActivity;
import de.nenick.espressomacchiato.testbase.EspressoIntentTestCase;

/** Basic tests */
public class EspContactStubTest extends EspressoIntentTestCase<OnActivityResultActivity> {

    private static final int REQUEST_CODE = 123;
    private static final int CONTACT_ID = 42;
    private EspTextView requestCodeTextView = EspTextView.byId(OnActivityResultActivity.requestCodeResource);
    private EspTextView resultCodeTextView = EspTextView.byId(OnActivityResultActivity.resultCodeResource);
    private EspTextView dataTextView = EspTextView.byId(OnActivityResultActivity.dataResource);
    private OnActivityResultActivity activity;

    @Before
    public void setup() {
        activity = activityTestRule.getActivity();
        activity.setListener(new OnActivityResultActivity.OnActivityResultListener() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                activity.setDataViewText(String.valueOf(data.getData()));
            }
        });
    }

    @Test
    public void testContactPickerStub() {
        Uri dummyContactDataUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, CONTACT_ID);
        EspContactStub.register(dummyContactDataUri);

        activity.startForResult(createContactPickerIntent(), REQUEST_CODE);

        requestCodeTextView.assertTextIs(String.valueOf(REQUEST_CODE));
        resultCodeTextView.assertTextIs(String.valueOf(Activity.RESULT_OK));
        dataTextView.assertTextIs(dummyContactDataUri.toString());
    }

    @Test
    public void testContactPickerStubWithExtraMatcher() {
        Uri dummyContactDataUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, CONTACT_ID);
        EspContactStub.register(dummyContactDataUri, Activity.RESULT_OK, IntentMatchers.hasExtraWithKey("MyKey"));

        Intent contactPickerIntent = createContactPickerIntent();
        contactPickerIntent.putExtra("MyKey", "myValue");
        activity.startForResult(contactPickerIntent, REQUEST_CODE);

        requestCodeTextView.assertTextIs(String.valueOf(REQUEST_CODE));
        resultCodeTextView.assertTextIs(String.valueOf(Activity.RESULT_OK));
        dataTextView.assertTextIs(dummyContactDataUri.toString());
    }

    @Test
    @Ignore("result will be delivered in tear down, after all checks are done")
    public void testWhenNotMatchingExtraMatcher() {
        Uri dummyContactDataUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, CONTACT_ID);
        EspContactStub.register(dummyContactDataUri, Activity.RESULT_OK, IntentMatchers.hasExtraWithKey("MyKey"));

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