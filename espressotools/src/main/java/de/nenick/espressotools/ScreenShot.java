package de.nenick.espressotools;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * https://mobile.awsblog.com/post/TxP90JPTTBPE17/Getting-started-with-Android-testing-on-AWS-Device-Farm-using-Espresso-Part-2-Se
 */
public class ScreenShot {

    private static final String TAG = "SCREENSHOT_TAG";

    public static void take(Activity activity, String name) {
        final String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test-screenshots/";
        final String path = dir + name;

        File filePath = new File(dir);     // Create directory if not present
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
