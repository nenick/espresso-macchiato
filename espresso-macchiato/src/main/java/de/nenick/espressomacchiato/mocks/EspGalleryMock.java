package de.nenick.espressomacchiato.mocks;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasType;
import static org.hamcrest.Matchers.allOf;

public class EspGalleryMock {

    public void givenMockedGalleryResult(String assetFile, String targetFile) {
        File galleryPhoto = new File(InstrumentationRegistry.getTargetContext().getExternalCacheDir(), targetFile);
        Log.v("EspGalleryMockFile", galleryPhoto.getAbsolutePath());
        givenMockedGalleryResult(assetFile, galleryPhoto);
    }

    public void givenMockedGalleryResult(String assetFile, File targetFile) {
        try {
            AssetManager assetManager = InstrumentationRegistry.getContext().getAssets();
            InputStream inputStream = assetManager.open(assetFile);
            FileOutputStream outputStream = new FileOutputStream(targetFile);
            copyFile(inputStream, outputStream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        Intent resultData = new Intent();
        resultData.setData(Uri.fromFile(targetFile));
        intending(allOf(hasAction(Intent.ACTION_GET_CONTENT), hasType("image/*"))).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData));
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}
