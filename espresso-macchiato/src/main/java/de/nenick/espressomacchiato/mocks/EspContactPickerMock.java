package de.nenick.espressomacchiato.mocks;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.tools.EspContactTool;

import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Mock requests for contact picker.
 *
 * @see EspContactTool to modify given contacts.
 */
public class EspContactPickerMock {

    /**
     * Create mock for {@link Intent#ACTION_PICK} intents.
     *
     * As result you get reported an {@link Activity#RESULT_OK}.
     *
     * @param uri Contact data uri which should be reported as result.
     */
    public void registerMockWithData(Uri uri) {
        Intent contactData = new Intent();
        contactData.setData(uri);
        intending(hasAction(Intent.ACTION_PICK)).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, contactData));
    }

    /**
     * Create mock for {@link Intent#ACTION_PICK} intents.
     *
     * As result you get reported an {@link Activity#RESULT_OK}.
     *
     * @param uri Contact data uri which should be reported as result.
     * @param extraMatcher React only when intent match the given additional matcher.
     */
    public void registerMockWithData(Uri uri, Matcher<? super Intent> extraMatcher) {
        Intent contactData = new Intent();
        contactData.setData(uri);
        intending(allOf(hasAction(Intent.ACTION_PICK), extraMatcher)).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, contactData));
    }

}
