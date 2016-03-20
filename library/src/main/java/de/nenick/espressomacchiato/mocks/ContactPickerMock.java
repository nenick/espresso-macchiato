package de.nenick.espressomacchiato.mocks;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;

import org.hamcrest.Matcher;

import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static org.hamcrest.CoreMatchers.allOf;

public class ContactPickerMock {

    public void registerMockWithData(Uri uri) {
        Intent contactData = new Intent();
        contactData.setData(uri);
        intending(hasAction(Intent.ACTION_PICK)).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, contactData));
    }

    public void registerMockWithData(Uri uri, Matcher<? super Intent> extraMatcher) {
        Intent contactData = new Intent();
        contactData.setData(uri);
        intending(allOf(hasAction(Intent.ACTION_PICK), extraMatcher)).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, contactData));
    }

}
