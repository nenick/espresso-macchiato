package de.nenick.espressomacchiato.mocks;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

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

public class EspCameraMockTest extends EspressoIntentTestCase<OnActivityResultActivity> {

    private final static int REQUEST_CODE = 123;
    private EspTextView requestCodeTextView = EspTextView.byId(OnActivityResultActivity.requestCodeResource);
    private EspTextView resultCodeTextView = EspTextView.byId(OnActivityResultActivity.resultCodeResource);
    private EspTextView dataTextView = EspTextView.byId(OnActivityResultActivity.dataResource);
    private OnActivityResultActivity activity;

    private EspCameraMock espCameraMock = new EspCameraMock();

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

    @Test
    public void testCameraMock() throws IOException {
        File targetFile = new File(activity.getExternalCacheDir(), "result.jpg");
        assertFalse(targetFile.exists());
        Uri fileUri = Uri.fromFile(targetFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        espCameraMock.givenMockedCameraResult("sample.jpg", "result.jpg");

        activity.startForResult(intent, REQUEST_CODE);

        requestCodeTextView.assertTextIs(String.valueOf(REQUEST_CODE));
        resultCodeTextView.assertTextIs(String.valueOf(Activity.RESULT_OK));
        dataTextView.assertTextIs(fileUri.toString());

        assertTrue(targetFile.exists());
        InputStream inputStream = activity.getContentResolver().openInputStream(fileUri);
        assertNotNull(inputStream);
        inputStream.close();
        assertTrue(targetFile.delete());
    }

    @Test
    public void testCameraMockFileNotFound() throws IOException {
        exception.expect(IllegalStateException.class);
        espCameraMock.givenMockedCameraResult("some file", new File("another file"));
    }
}