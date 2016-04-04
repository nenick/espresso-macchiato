package de.nenick.espressomacchiato.testbase;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ScreenShot {

    private static final String TAG = "SCREENSHOT_TAG";

    public static void take(Activity activity, String name) {

        File externalCacheDir = InstrumentationRegistry.getTargetContext().getExternalCacheDir();
        if(externalCacheDir == null) {
            Log.e(TAG, "could not find external cache dir to store screenshot");
            return;
        }
        final String dir = externalCacheDir.getAbsolutePath() + "/test-screenshots/";
        final String path = dir + name + ".jpg";

        // Create directory if not present
        File filePath = new File(dir);
        if (!filePath.isDirectory()) {
            Log.i(TAG, "Creating directory " + filePath);
            if(!filePath.mkdirs()) {
                Log.i(TAG, "Creating directory failed " + filePath);
            }
        }

        Log.i(TAG, "Saving to path: " +  path);


        View phoneView = activity.getWindow().getDecorView().getRootView();
        phoneView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(phoneView.getDrawingCache());
        phoneView.setDrawingCacheEnabled(false);

        OutputStream out = null;

        File imageFile = new File(path);

        try {
            out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }

        finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }
    }
}
