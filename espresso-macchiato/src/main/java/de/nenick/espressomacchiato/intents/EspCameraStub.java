package de.nenick.espressomacchiato.intents;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;

/**
 * Stubbing camera picture request with {@link MediaStore#ACTION_IMAGE_CAPTURE}.
 *
 * Typical application starts the camera app and use the reported Uri to access new picture by ContentResolver.
 * With this stubs you can avoid the start of the camera app and let them report your preferred picture Uri.
 * But you must provide the picture on your phone by self.
 *
 * <span class="simpleTagLabel">Example usage:</span><br>
 *
 * <!-- <pre> -->
 * ```java
 * // provide a test picture
 * Uri pictureUri = EspFilesTool.copyFileFromAssetsToExternalCacheDir("picture_from_assets.jpg", "camera_picture.jpg"
 *
 * // register stub with your test picture uri
 * EspCameraStub.register(pictureUri);
 *
 * // now perform action to start camera picture request
 *
 * ```
 * <!-- </pre> -->
 *
 * @see de.nenick.espressomacchiato.intents Base description for intent mocking.
 * @since Espresso Macchiato 0.6
 */
public class EspCameraStub {

    /**
     * Register new stub for camera picture requests with {@link Activity#RESULT_OK}.
     *
     * @param pictureLocation Picture uri which will be accessible as intent data.
     *
     * @since Espresso Macchiato 0.6
     */
    public void register(Uri pictureLocation) {
        Intent resultData = new Intent();
        resultData.setData(pictureLocation);
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData));
    }
}
