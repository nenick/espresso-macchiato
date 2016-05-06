package de.nenick.espressomacchiato.tools;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.nenick.espressomacchiato.test.database.PersonContract;
import de.nenick.espressomacchiato.test.database.PersonDbHelper;
import de.nenick.espressomacchiato.test.views.BaseActivity;
import de.nenick.espressomacchiato.testbase.EspressoTestCase;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class EspAppDataToolTest extends EspressoTestCase<BaseActivity> {

    private static final String PRFERENCE_FALLBACK_VALUE = "not exist";
    private static final String PREFERENCE_VALUE = "Android";
    private static final String DEFAULT_PREFERENCE = "MyDefaultPreference";
    private static final String CUSTOM_PREFERENCE = "MyCustomPreference";
    private static final String FILE_NAME = "myfile";

    @After
    public void reset() {
        EspAppDataTool.clearDatabase();

        // just for coverage
        new EspAppDataTool();
    }

    @Test
    public void testClearSharedPreferences() {
        SharedPreferences defaultPreferences = givenDefaultPreferences();
        SharedPreferences customPreferences = givenCustomPreferences();

        EspAppDataTool.clearSharedPreferences();

        thenDefaultPreferencesIsCleared(defaultPreferences);
        thenCustomPreferencesIsCleared(customPreferences);
    }

    @Test
    public void testClearSharedPreferencesWhenNoPreferencesExists() {
        EspAppDataTool.clearSharedPreferences();
    }

    @Test
    public void testClearDatabase() {
        PersonDbHelper personDbHelper = new PersonDbHelper(InstrumentationRegistry.getContext());
        givenDatabaseEntry(personDbHelper);

        EspAppDataTool.clearDatabase();

        thenDatabaseIsEmpty(personDbHelper);
    }

    @Test
    @Ignore // TODO need a way to throw error if any connection is open. better way would be to close them all
    public void testClearDatabaseWithOpenConnection() {
        EspAppDataTool.clearDatabase();

        PersonDbHelper personDbHelper = new PersonDbHelper(InstrumentationRegistry.getContext());
        givenDatabaseEntry(personDbHelper);

        // simulate: forget to close database connection
        personDbHelper.getWritableDatabase();
        EspAppDataTool.clearDatabase();
        /*
        thenDatabaseHasAnEntry(personDbHelper);

        personDbHelper.close();
        EspAppDataTool.clearDatabase();
        */
        thenDatabaseIsEmpty(personDbHelper);
    }

    @Test
    public void testClearStorageFiles() throws IOException {
        Uri uri = givenStorageFile(FILE_NAME);

        InputStream inputStream = InstrumentationRegistry.getTargetContext().getContentResolver().openInputStream(uri);
        assertNotNull(inputStream);
        inputStream.close();

        EspAppDataTool.clearStorageExceptScreenshots();

        exception.expect(FileNotFoundException.class);
        InstrumentationRegistry.getTargetContext().getContentResolver().openInputStream(uri);
    }

    @Test
    public void testClearCacheFiles() throws IOException {
        Uri uri = givenCacheFile(FILE_NAME);

        InputStream inputStream = InstrumentationRegistry.getTargetContext().getContentResolver().openInputStream(uri);
        assertNotNull(inputStream);
        inputStream.close();

        EspAppDataTool.clearCache();

        exception.expect(FileNotFoundException.class);
        InstrumentationRegistry.getTargetContext().getContentResolver().openInputStream(uri);
    }

    @Test
    public void testAllApplicationData() {
        PersonDbHelper personDbHelper = new PersonDbHelper(InstrumentationRegistry.getContext());

        SharedPreferences defaultPreferences = givenDefaultPreferences();
        SharedPreferences customPreferences = givenCustomPreferences();
        givenDatabaseEntry(personDbHelper);
        Uri storageFileUri = givenStorageFile(FILE_NAME);
        Uri cacheFileUri = givenCacheFile(FILE_NAME);

        EspAppDataTool.clearApplicationData();

        thenDefaultPreferencesIsCleared(defaultPreferences);
        thenCustomPreferencesIsCleared(customPreferences);
        thenDatabaseIsEmpty(personDbHelper);
        try {
            InstrumentationRegistry.getTargetContext().getContentResolver().openInputStream(storageFileUri);
            fail();
        } catch (FileNotFoundException e) {
            // file should be deleted, exception is expected
        }

        try {
            InstrumentationRegistry.getTargetContext().getContentResolver().openInputStream(cacheFileUri);
            fail();
        } catch (FileNotFoundException e) {
            // file should be deleted, exception is expected
        }
    }

    private void thenDatabaseIsEmpty(PersonDbHelper personDbHelper) {
        SQLiteDatabase writableDatabase;
        Cursor query;
        writableDatabase = personDbHelper.getWritableDatabase();
        query = writableDatabase.query(PersonContract.Entry.TABLE_NAME, null, null, null, null, null, null);
        assertThat(query.getCount(), is(0));
        query.close();
        personDbHelper.close();
    }

    private void givenDatabaseEntry(PersonDbHelper personDbHelper) {
        ContentValues values = new ContentValues();
        values.put(PersonContract.Entry.COLUMN_NAME, "John");

        SQLiteDatabase writableDatabase = personDbHelper.getWritableDatabase();
        writableDatabase.insert(PersonContract.Entry.TABLE_NAME, null, values);
        Cursor query = writableDatabase.query(PersonContract.Entry.TABLE_NAME, null, null, null, null, null, null);
        assertThat(query.getCount(), is(1));
        query.close();
        writableDatabase.close();
    }

    private Uri givenStorageFile(String filename) {
        try {
            File file = new File(InstrumentationRegistry.getTargetContext().getFilesDir(), filename);
            assertThat(file.createNewFile(), is(true));
            return Uri.fromFile(file);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private Uri givenCacheFile(String filename) {
        try {
            File file = File.createTempFile(filename, null, InstrumentationRegistry.getTargetContext().getCacheDir());
            return Uri.fromFile(file);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private SharedPreferences givenCustomPreferences() {
        SharedPreferences customPreferences = InstrumentationRegistry.getTargetContext().getSharedPreferences("custom", Context.MODE_PRIVATE);
        customPreferences.edit().putString(CUSTOM_PREFERENCE, PREFERENCE_VALUE).commit();
        assertThat(customPreferences.getString(CUSTOM_PREFERENCE, PRFERENCE_FALLBACK_VALUE), is(PREFERENCE_VALUE));
        return customPreferences;
    }

    private SharedPreferences givenDefaultPreferences() {
        SharedPreferences defaultPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        defaultPreferences.edit().putString(DEFAULT_PREFERENCE, PREFERENCE_VALUE).commit();
        assertThat(defaultPreferences.getString(DEFAULT_PREFERENCE, PRFERENCE_FALLBACK_VALUE), is(PREFERENCE_VALUE));
        return defaultPreferences;
    }

    private void thenDefaultPreferencesIsCleared(SharedPreferences defaultPreferences) {
        assertThat(defaultPreferences.getString(DEFAULT_PREFERENCE, PRFERENCE_FALLBACK_VALUE), is(PRFERENCE_FALLBACK_VALUE));
    }

    private void thenCustomPreferencesIsCleared(SharedPreferences customPreferences) {
        assertThat(customPreferences.getString(CUSTOM_PREFERENCE, PRFERENCE_FALLBACK_VALUE), is(PRFERENCE_FALLBACK_VALUE));
    }
}