package de.nenick.espressomacchiato.tools;

import android.app.Activity;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import de.nenick.espressomacchiato.testbase.EspressoTestBase;

public class EspFilesToolTest extends EspressoTestBase {

    @Override
    public Activity getActivity() {
        return null;
    }

    @Test
    public void testCopyErrorNotFound() throws IOException {
        exception.expect(IllegalStateException.class);
        EspFilesTool.copyFileFromAssets("some file", new File("another file"));
    }
}