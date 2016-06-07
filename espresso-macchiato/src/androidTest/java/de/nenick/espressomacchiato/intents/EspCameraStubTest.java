package de.nenick.espressomacchiato.intents;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.test.InstrumentationRegistry;
import android.widget.TextView;

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
public class EspCameraStubTest extends EspressoIntentTestCase<OnActivityResultActivity> {

    private static final int REQUEST_CODE = 123;
    private static final String RESULT_JPG = "result.jpg";
    private static final String SAMPLE_JPG = "sample.jpg";
    private EspTextView requestCodeTextView = EspTextView.byId(OnActivityResultActivity.requestCodeResource);
    private EspTextView resultCodeTextView = EspTextView.byId(OnActivityResultActivity.resultCodeResource);
    private EspTextView dataTextView = EspTextView.byId(OnActivityResultActivity.dataResource);
    private OnActivityResultActivity activity;

    private EspCameraStub espCameraStub = new EspCameraStub();
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
        if (targetFile != null) {
            assertTrue(targetFile.delete());
        }
    }

    @Test
    public void testCameraStub() throws IOException {
        targetFile = new File(activity.getExternalCacheDir(), RESULT_JPG);
        assertFalse(targetFile.exists());

        Uri pictureUri = EspFilesTool.copyFileFromAssetsToExternalCacheDir(SAMPLE_JPG, RESULT_JPG);
        espCameraStub.register(pictureUri);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        activity.startForResult(intent, REQUEST_CODE);

        requestCodeTextView.assertTextIs(String.valueOf(REQUEST_CODE));
        resultCodeTextView.assertTextIs(String.valueOf(Activity.RESULT_OK));
        dataTextView.assertTextIs(pictureUri.toString());

        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                ((TextView) getActivity().findViewById(OnActivityResultActivity.requestCodeResource)).setText("");
            }
        });


        activity.startForResult(intent, REQUEST_CODE);
        requestCodeTextView.assertTextIs(String.valueOf(REQUEST_CODE));
        resultCodeTextView.assertTextIs(String.valueOf(Activity.RESULT_OK));
        dataTextView.assertTextIs(pictureUri.toString());

        assertTrue(targetFile.exists());
        InputStream inputStream = activity.getContentResolver().openInputStream(pictureUri);
        assertNotNull(inputStream);
        inputStream.close();
    }

    @Test
    public void testCameraStubMultipleTimes() throws IOException {
        targetFile = new File(activity.getExternalCacheDir(), RESULT_JPG);
        assertFalse(targetFile.exists());

        Uri pictureUri = EspFilesTool.copyFileFromAssetsToExternalCacheDir(SAMPLE_JPG, RESULT_JPG);
        espCameraStub.register(pictureUri);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        activity.startForResult(intent, REQUEST_CODE);

        requestCodeTextView.assertTextIs(String.valueOf(REQUEST_CODE));

        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                ((TextView) getActivity().findViewById(OnActivityResultActivity.requestCodeResource)).setText("");
            }
        });
        requestCodeTextView.assertTextIs("");

        activity.startForResult(intent, REQUEST_CODE);
        requestCodeTextView.assertTextIs(String.valueOf(REQUEST_CODE));
    }
}