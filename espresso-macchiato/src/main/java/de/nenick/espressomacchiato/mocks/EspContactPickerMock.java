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
import static org.hamcrest.CoreMatchers.anything;

/**
 * Stubbing contact request with {@link Intent#ACTION_PICK}.
 *
 * @see EspContactTool to modify given contacts.
 * @see de.nenick.espressomacchiato.intents Base description for intent mocking.
 * @since Espresso Macchiato 0.6
 */
public class EspContactPickerMock {

    /**
     * Register new stub (convenience method for {@link #register(Uri, Matcher, int)}.
     *
     * As result you get reported an {@link Activity#RESULT_OK}.
     *
     * @param uri Contact data uri which should be reported as result.
     *
     * @since Espresso Macchiato 0.6
     */
    public void register(Uri uri) {
        register(uri, anything(), Activity.RESULT_OK);
    }

    /**
     * Register new stub (convenience method for {@link #register(Uri, Matcher, int)}.
     *
     * As result you get reported an {@link Activity#RESULT_OK}.
     *
     * @param uri          Contact data uri which should be reported as result.
     * @param extraMatcher Report only when intent match the given additional matcher.
     *
     * @since Espresso Macchiato 0.6
     */
    public void register(Uri uri, Matcher<? super Intent> extraMatcher) {
        register(uri, extraMatcher, Activity.RESULT_OK);
    }

    /**
     * Register new stub for contact data requests
     *
     * @param uri            Uri will be reported as intent data.
     * @param extraMatcher   Report only when intent match the given additional matcher.
     * @param activityResult Specific activity result ({@link Activity#RESULT_OK} or {@link Activity#RESULT_CANCELED}.
     *
     * @since Espresso Macchiato 0.6
     */
    public void register(Uri uri, Matcher<? super Intent> extraMatcher, int activityResult) {
        Intent contactData = new Intent();
        contactData.setData(uri);
        intending(allOf(hasAction(Intent.ACTION_PICK), extraMatcher)).respondWith(new Instrumentation.ActivityResult(activityResult, contactData));
    }
}
