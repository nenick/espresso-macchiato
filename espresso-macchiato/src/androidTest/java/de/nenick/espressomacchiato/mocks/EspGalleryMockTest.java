package de.nenick.espressomacchiato.mocks;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import de.nenick.espressomacchiato.elements.EspTextView;
import de.nenick.espressomacchiato.test.views.OnActivityResultActivity;
import de.nenick.espressomacchiato.testbase.EspressoIntentTestCase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class EspGalleryMockTest extends EspressoIntentTestCase<OnActivityResultActivity> {

    private final static int REQUEST_CODE = 123;
    private EspTextView requestCodeTextView = EspTextView.byId(OnActivityResultActivity.requestCodeResource);
    private EspTextView resultCodeTextView = EspTextView.byId(OnActivityResultActivity.resultCodeResource);
    private EspTextView dataTextView = EspTextView.byId(OnActivityResultActivity.dataResource);
    private OnActivityResultActivity activity;
    private File targetFile;

    private EspGalleryMock espGalleryMock = new EspGalleryMock();

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
        Uri fileUri = Uri.fromFile(targetFile);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        espGalleryMock.givenMockedGalleryResult("sample.jpg", "result.jpg");

        activity.startForResult(intent, REQUEST_CODE);

        requestCodeTextView.assertTextIs(String.valueOf(REQUEST_CODE));
        resultCodeTextView.assertTextIs(String.valueOf(Activity.RESULT_OK));
        dataTextView.assertTextIs(fileUri.toString());

        assertTrue(targetFile.exists());
        InputStream inputStream = activity.getContentResolver().openInputStream(fileUri);
        assertNotNull(inputStream);
        inputStream.close();
    }

    @Test
    public void testGalleryMockFileNotFound() throws IOException {
        exception.expect(IllegalStateException.class);
        espGalleryMock.givenMockedGalleryResult("some file", new File("another file"));
    }
}