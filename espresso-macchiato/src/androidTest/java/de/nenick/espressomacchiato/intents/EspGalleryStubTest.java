package de.nenick.espressomacchiato.intents;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import de.nenick.espressomacchiato.elements.EspTextView;
import de.nenick.espressomacchiato.test.views.OnActivityResultActivity;
import de.nenick.espressomacchiato.testbase.EspressoIntentTestCase;
import de.nenick.espressomacchiato.tools.EspFilesTool;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/** Basic tests */
public class EspGalleryStubTest extends EspressoIntentTestCase<OnActivityResultActivity> {

    private final static int REQUEST_CODE = 123;
    private EspTextView requestCodeTextView = EspTextView.byId(OnActivityResultActivity.requestCodeResource);
    private EspTextView resultCodeTextView = EspTextView.byId(OnActivityResultActivity.resultCodeResource);
    private EspTextView dataTextView = EspTextView.byId(OnActivityResultActivity.dataResource);
    private OnActivityResultActivity activity;
    private File targetFile;

    @Before
    public void setup() {
        activity = activityTestRule.getActivity();
        activity.setListener(new OnActivityResultActivity.OnActivityResultListener() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                activity.setDataViewText(data.getData().toString());
            }
        });
    }

    @After
    public void reset() {
        if(targetFile != null && targetFile.exists()) {
            assertTrue(targetFile.delete());
        }
    }

    @Test
    public void testGalleryMock() throws IOException {
        targetFile = new File(activity.getExternalCacheDir(), "result.jpg");
        assertFalse(targetFile.exists());

        Uri pictureUri = EspFilesTool.copyFileFromAssetsToExternalCacheDir("sample.jpg", "result.jpg");
        EspGalleryStub.register(pictureUri);

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startForResult(intent, REQUEST_CODE);

        requestCodeTextView.assertTextIs(String.valueOf(REQUEST_CODE));
        resultCodeTextView.assertTextIs(String.valueOf(Activity.RESULT_OK));
        dataTextView.assertTextIs(pictureUri.toString());

        assertTrue(targetFile.exists());
        InputStream inputStream = activity.getContentResolver().openInputStream(pictureUri);
        assertNotNull(inputStream);
        inputStream.close();
    }


}