package de.nenick.espressomacchiato.tools;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import java.io.File;

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
     * Execute all existing clear operations.
     */
    public static void clearApplicationData() {
        clearStorage();
        clearCache();
        clearDatabase();
        clearSharedPreferences();
    }

    /**
     * Clear all shared preferences.
     */
    public static void clearSharedPreferences() {
        String[] preferenceFiles = new File(InstrumentationRegistry.getTargetContext().getFilesDir().getParentFile(), "shared_prefs").list();
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
            InstrumentationRegistry.getContext().deleteDatabase(database);
            assertThat(InstrumentationRegistry.getContext().getDatabasePath(database).exists(), is(false));
        }
    }

    public static void clearCache() {
        File cacheDir = InstrumentationRegistry.getTargetContext().getCacheDir();
        assertThat(deleteRecursive(cacheDir), is(true));
    }

    public static void clearStorage() {
        File filesDir = InstrumentationRegistry.getTargetContext().getFilesDir();
        assertThat(deleteRecursive(filesDir), is(true));
    }

    private static boolean deleteRecursive(File directory) {
        if (directory.isDirectory()) {
            String[] directoryContent = directory.list();
            for (String content : directoryContent) {
                assertThat(deleteRecursive(new File(directory, content)), is(true));
            }
        }
        return directory.delete();
    }
}
