package de.nenick.espressomacchiato.mocks;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;

/**
 * Mock requests for camera picture.
 */
public class EspCameraMock {

    /**
     * Convenience method for {@link #givenMockedCameraResult(String, File)}.
     *
     * Picture will be stored at {@link Context#getExternalCacheDir()}.
     *
     * @param assetFile      Dummy camera picture from asset files.
     * @param targetFileName Target file name for the picture result.
     */
    public void givenMockedCameraResult(String assetFile, String targetFileName) {
        File cameraPhoto = new File(InstrumentationRegistry.getTargetContext().getExternalCacheDir(), targetFileName);
        Log.v("EspCameraMockFile", cameraPhoto.getAbsolutePath());
        givenMockedCameraResult(assetFile, cameraPhoto);
    }

    /**
     * Create mock for MediaStore.ACTION_IMAGE_CAPTURE intents.
     *
     * After request is started the source picture will be copied to the given target.
     * As result you get reported an {@link Activity#RESULT_OK}.
     *
     * @param assetFile  Dummy camera picture from asset files.
     * @param targetFile Target where the picture result should be written.
     */
    public void givenMockedCameraResult(String assetFile, File targetFile) {
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
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData));
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
