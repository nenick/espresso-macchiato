package de.nenick.espressomacchiato.tools;

import android.content.res.AssetManager;
import android.net.Uri;
import androidx.test.InstrumentationRegistry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class EspFilesTool {

    public static Uri copyFileFromAssets(String assetFile, File targetFile) {
        try {
            AssetManager assetManager = InstrumentationRegistry.getContext().getAssets();
            InputStream assetFileStream = assetManager.open(assetFile);
            FileOutputStream targetFileStream = new FileOutputStream(targetFile);
            copyFile(assetFileStream, targetFileStream);
            targetFileStream.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return Uri.fromFile(targetFile);
    }

    public static Uri copyFileFromAssetsToExternalCacheDir(String assetFile, String targetFile) {
        File galleryPhoto = new File(InstrumentationRegistry.getTargetContext().getExternalCacheDir(), targetFile);
        return copyFileFromAssets(assetFile, galleryPhoto);
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
