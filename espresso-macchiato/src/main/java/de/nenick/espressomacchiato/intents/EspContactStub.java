package de.nenick.espressomacchiato.intents;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;

import org.hamcrest.Matcher;

import de.nenick.espressomacchiato.tools.EspContactTool;

import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;

/**
 * Stubbing contact request with {@link Intent#ACTION_PICK}.
 *
 * Typical application starts the contact picker and use the reported Uri to access selected contact data by ContentResolver.
 * With this stubs you can avoid the start of the contact picker and let them report your preferred data Uri.
 * But you must provide the contact data on your phone by self.
 *
 * <span class="simpleTagLabel">Example usage:</span><br>
 *
 * <!-- <pre> -->
 * ```java
 * // since Android M you need permissions to write contacts
 * EspPermissionsTool.ensurePermissions(currentActivity(), Manifest.permission.WRITE_CONTACTS)
 *
 * // provide some test contact data
 * Uri contactUri = EspContactTool.uriByName("My Test User");
 * if(contactUri == null) {
 *     contactUri = EspContactTool.spec().withDisplayName("My Test User").addContact();
 * }
 *
 * // register a stub with your test contact uri
 * EspContactStub.register(contactUri);
 *
 * // now perform action to start contact picker request
 *
 * ```
 * <!-- </pre> -->
 *
 * @see EspContactTool
 * @see de.nenick.espressomacchiato.intents Base description for intent stubbing.
 * @since Espresso Macchiato 0.6
 */
public class EspContactStub {

    /**
     * Register new stub for contact data requests with {@link Activity#RESULT_OK}.
     *
     * @param contactDataUri Contact uri which will be accessible as intent data.
     *
     * @since Espresso Macchiato 0.6
     */
    public static void register(Uri contactDataUri) {
        register(contactDataUri, Activity.RESULT_OK);
    }

    /**
     * Register new stub for contact data requests with specific activity result.
     *
     * @param contactDataUri Contact uri which will be accessible as intent data.
     * @param activityResult {@link Activity#RESULT_OK} or {@link Activity#RESULT_CANCELED}.
     *
     * @since Espresso Macchiato 0.6
     */
    public static void register(Uri contactDataUri, int activityResult) {
        register(contactDataUri, activityResult, anything());
    }

    /**
     * Register new stub for contact data requests with specific activity result and extra Intent matcher.
     *
     * @param contactDataUri Contact uri which will be accessible as intent data.
     * @param activityResult {@link Activity#RESULT_OK} or {@link Activity#RESULT_CANCELED}.
     * @param extraMatcher   Additional intent matcher to match specific requests.
     *
     * @since Espresso Macchiato 0.6
     */
    public static void register(Uri contactDataUri, int activityResult, Matcher<? super Intent> extraMatcher) {
        Intent contactData = new Intent();
        contactData.setData(contactDataUri);
        intending(allOf(hasAction(Intent.ACTION_PICK), extraMatcher)).respondWith(new Instrumentation.ActivityResult(activityResult, contactData));
    }
}
