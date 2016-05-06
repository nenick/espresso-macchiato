package de.nenick.espressomacchiato.tools;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import java.io.File;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tool for application data "file storage/cache, shared preferences, database".
 * <p>
 * Best situation is to clear all data is when activity is not started.
 * <p>
 * You can delay activity start with:<br>
 * new ActivityTestRule(Activity.class, false, false)<br>
 * clearApplicationData()<br>
 * ActivityTestRule.lunchActivity()
 */
public class EspAppDataTool {

    /**
     * Clear all application data except screenshots taken with {@link EspScreenshotTool}.
     */
    public static void clearApplicationData() {
        clearStorageExceptScreenshots();
        clearCache();
        clearDatabase();
        clearSharedPreferences();
    }

    /**
     * Clear all shared preferences.
     */
    public static void clearSharedPreferences() {
        String[] preferenceFiles = new File(InstrumentationRegistry.getTargetContext().getFilesDir().getParentFile(), "shared_prefs").list();

        if(preferenceFiles == null) {
            // occurs when no preferences was created
            return;
        }

        for (String preferenceFile : preferenceFiles) {
            InstrumentationRegistry.getTargetContext().getSharedPreferences(preferenceFile.replace(".xml", ""), Context.MODE_PRIVATE).edit().clear().commit();
        }
    }

    /**
     * only works if all database connections are closed. does not produce error if connection still open.
     */
    public static void clearDatabase() {
        String[] databaseList = InstrumentationRegistry.getTargetContext().databaseList();
        for (String database : databaseList) {

            // when transaction rollback files exists they are always locked so we can't delete them
            if(database.contains("-journal")) {
                continue;
            }
            Log.v("EspressoMacchiato", "deleting " + database);

            assertThat(InstrumentationRegistry.getTargetContext().getDatabasePath(database).exists(), is(true));
            assertThat("could not delete " + database, InstrumentationRegistry.getTargetContext().deleteDatabase(database), is(true));
            assertThat(InstrumentationRegistry.getTargetContext().getDatabasePath(database).exists(), is(false));
        }
    }

    public static void clearCache() {
        File cacheDir = InstrumentationRegistry.getTargetContext().getCacheDir();
        assertThat(deleteRecursive(cacheDir), is(true));
    }

    public static void clearStorage(String ... excludes) {
        File filesDir = InstrumentationRegistry.getTargetContext().getFilesDir();
        String[] directoryContent = filesDir.list();
        for (String content : directoryContent) {
            assertThat(deleteRecursive(new File(filesDir, content), excludes), is(true));
        }

    }

    public static void clearStorageExceptScreenshots() {
        clearStorage(EspScreenshotTool.screenshotFolderName);
    }

    private static boolean deleteRecursive(File directory, String ... excludes) {
        if(excludes.length > 0 && Arrays.asList(excludes).contains(directory.getName())) {
            return true;
        }

        if (directory.isDirectory()) {
            String[] directoryContent = directory.list();
            for (String content : directoryContent) {
                assertThat(deleteRecursive(new File(directory, content), excludes), is(true));
            }
        }
        return directory.delete();
    }
}
