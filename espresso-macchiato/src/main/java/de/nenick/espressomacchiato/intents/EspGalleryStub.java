package de.nenick.espressomacchiato.intents;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasType;
import static org.hamcrest.Matchers.allOf;

/**
 * Stubbing gallery picture request with {@link Intent#ACTION_GET_CONTENT} and {@link MediaStore.Images.Media#CONTENT_TYPE}.
 *
 * Typical application starts the gallery picker and use the reported Uri to access selected picture by ContentResolver.
 * With this stubs you can avoid the start of the picture picker and let them report your preferred picture Uri.
 * But you must provide the picture on your phone by self.
 *
 * <span class="simpleTagLabel">Example usage:</span><br>
 *
 * <!-- <pre> -->
 * ```java
 * // provide a test picture
 * Uri pictureUri = EspFilesTool.copyFileFromAssetsToExternalCacheDir("picture_from_assets.jpg", "gallery_picture.jpg"
 *
 * // register stub with your test picture uri
 * EspGalleryStub.register(pictureUri);
 *
 * // now perform action to start gallery picture request
 *
 * ```
 * <!-- </pre> -->
 *
 * @see de.nenick.espressomacchiato.intents Base description for intent mocking.
 * @since Espresso Macchiato 0.6
 */
public class EspGalleryStub {

    /**
     * Register new stub for camera picture requests with {@link Activity#RESULT_OK}.
     *
     * @param pictureLocation Picture uri which will be accessible as intent data.
     *
     * @since Espresso Macchiato 0.6
     */
    public static void register(Uri pictureLocation) {
        Intent resultData = new Intent();
        resultData.setData(pictureLocation);
        intending(allOf(hasAction(Intent.ACTION_GET_CONTENT), hasType("image/*"))).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData));
    }
}
