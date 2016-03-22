package de.nenick.espressomacchiato.tools;

import android.Manifest;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.nenick.espressomacchiato.test.views.OnActivityResultActivity;
import de.nenick.espressotools.EspressoTestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class EspContactToolTest extends EspressoTestCase<OnActivityResultActivity> {

    private static final String TEST_CONTACT_NAME = "Test Contact";
    private static final String TEST_STREET = "DummyStreet 17";
    private static final String TEST_POSTCODE = "12345";
    private static final String TEST_CITY = "Berlin";
    private static final String TEST_COUNTRY = "Germany";
    private int initialContactCount;
    private Uri contactUri;

    @Before
    public void setup() {
        EspPermissionsTool.ensurePermissions(getActivity(), Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS);
        initialContactCount = queryContactCount();
    }

    @After
    public void reset() {
        EspContactTool.delete(contactUri);
        assertEquals(initialContactCount, queryContactCount());
    }

    @Test
    public void testContactHandling() {
        EspContactTool.ContactSpec contactSpec = EspContactTool.spec()
                .withDisplayName(TEST_CONTACT_NAME)
                .withAddress(EspContactTool.address()
                        .withStreet(TEST_STREET)
                        .withPostcode(TEST_POSTCODE)
                        .withCity(TEST_CITY)
                        .withCountry(TEST_COUNTRY));

        EspContactTool.add(contactSpec);

        contactUri = EspContactTool.uriByName(TEST_CONTACT_NAME);
        assertNotNull(contactUri);

        // check raw data
        Cursor contactData = InstrumentationRegistry.getTargetContext().getContentResolver().query(contactUri, null, null, null, null);
        assertNotNull(contactData);
        assertTrue(contactData.moveToFirst());

        assertEquals(TEST_CONTACT_NAME, contactData.getString(contactData.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME)));

        // check address data
        long rawContactId = contactData.getLong(contactData.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.RAW_CONTACT_ID));
        Cursor contactAddress = InstrumentationRegistry.getTargetContext().getContentResolver().query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null, ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = ?", new String[] {String.valueOf(rawContactId)}, null);
        assertNotNull(contactAddress);
        assertTrue(contactAddress.moveToFirst());

        assertEquals(TEST_STREET, contactAddress.getString(contactAddress.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET)));
        assertEquals(TEST_POSTCODE, contactAddress.getString(contactAddress.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE)));
        assertEquals(TEST_CITY, contactAddress.getString(contactAddress.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY)));
        assertEquals(TEST_COUNTRY, contactAddress.getString(contactAddress.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY)));

        contactData.close();
        contactAddress.close();
    }

    protected int queryContactCount() {
        Cursor cursor = InstrumentationRegistry.getContext().getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, null, null, null);
        assertNotNull(cursor);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}