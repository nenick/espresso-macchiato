package de.nenick.espressomacchiato.tools;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import androidx.test.platform.app.InstrumentationRegistry;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;

public class EspContactTool {

    public static ContactSpec spec() {
        return new ContactSpec();
    }

    public static ContactAddress address() {
        return new ContactAddress();
    }

    public static class ContactAddress {
        private String street;
        private String postcode;
        private String city;
        private String country;

        public ContactAddress withStreet(String street) {
            this.street = street;
            return this;
        }

        public ContactAddress withPostcode(String postcode) {
            this.postcode = postcode;
            return this;
        }

        public ContactAddress withCity(String city) {
            this.city = city;
            return this;
        }

        public ContactAddress withCountry(String country) {
            this.country = country;
            return this;
        }
    }

    public static class ContactSpec {

        private String displayName;
        /*private String mobileNumber;
        private String homeNumber;
        private String workNumber;
        private String emailID;
        private String company;
        private String jobTitle;*/
        private ContactAddress address;

        public ContactSpec withDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public ContactSpec withAddress(ContactAddress address) {
            this.address = address;
            return this;
        }

        public Uri addContact() {
            return add(this);
        }
    }

    public static Uri add(ContactSpec spec) {

        // original code http://stackoverflow.com/questions/4744187/how-to-add-new-contacts-in-android
        // good blog http://androiddevelopement.blogspot.de/2011/07/insert-update-delete-view-contacts-in.html

        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        addContactBase(ops);
        addContactDisplayName(spec, ops);
        addContactAddress(spec, ops);

        try {
            ContentProviderResult[] results = InstrumentationRegistry.getTargetContext().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            return results[0].uri;
        } catch (RemoteException | OperationApplicationException e) {
            throw new IllegalStateException("Could not add contact", e);
        }
    }

    public static void delete(Uri contactUri) {
        Uri rawContactUri;
        if(contactUri.toString().contains(ContactsContract.RawContacts.CONTENT_URI.toString())) {
            rawContactUri = contactUri;
        } else {
            Cursor contactData = InstrumentationRegistry.getTargetContext().getContentResolver().query(contactUri, null, null, null, null);
            assertNotNull(contactData);
            contactData.moveToFirst();
            long rawContactId = contactData.getLong(contactData.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID));
            contactData.close();
            rawContactUri = ContentUris.withAppendedId(ContactsContract.RawContacts.CONTENT_URI, rawContactId);
        }

        InstrumentationRegistry.getTargetContext().getContentResolver().delete(rawContactUri, null, null);
//        assertTrue(InstrumentationRegistry.getTargetContext().getContentResolver().delete(ContactsContract.Data.CONTENT_URI, ContactsContract.Data.RAW_CONTACT_ID + " = ?", new String[]{String.valueOf(rawContactId)}) > 0);
    }

    /**
     * Find data uri for contact.
     *
     * @param contactName display name
     * @return uri to contact data
     * @deprecated May return uri to unpredicted data set. Use instead a specific data uri search method
     */
    @Deprecated
    public static Uri uriByName(String contactName) {
        Uri result = null;
        Cursor cursor = InstrumentationRegistry.getTargetContext().getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, null, null, null);

        assertNotNull(cursor);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));
                if (contactName.equals(name)) {
                    result = Uri.withAppendedPath(ContactsContract.Data.CONTENT_URI, id);
                    break;
                }
            }
        }

        cursor.close();
        return result;
    }

    public static Uri addressUriByDisplayName(String contactName) {
        Uri result = null;
        Cursor cursor = InstrumentationRegistry.getTargetContext().getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                null,
                ContactsContract.Data.MIMETYPE + " = ? ",
                new String[]{ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE},
                null);

        assertNotNull(cursor);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DISPLAY_NAME));
                if (contactName.equals(name)) {
                    result = Uri.withAppendedPath(ContactsContract.Data.CONTENT_URI, id);
                    break;
                }
            }
        }

        cursor.close();
        return result;
    }



    private static void addContactAddress(ContactSpec spec, ArrayList<ContentProviderOperation> ops) {
        if (spec.address != null) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.STREET, spec.address.street)
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE, spec.address.postcode)
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.CITY, spec.address.city)
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY, spec.address.country)
                    .build());
        }
    }

    private static void addContactDisplayName(ContactSpec spec, ArrayList<ContentProviderOperation> ops) {
        if (spec.displayName != null) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, spec.displayName)
                    .build());
        }
    }

    private static void addContactBase(ArrayList<ContentProviderOperation> ops) {
        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());
    }

    /*
        //------------------------------------------------------ Mobile Number
        if (spec.mobileNumber != null) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, spec.mobileNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
        }

        //------------------------------------------------------ Home Numbers
        if (spec.homeNumber != null) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, spec.homeNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                    .build());
        }

        //------------------------------------------------------ Work Numbers
        if (spec.workNumber != null) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, spec.workNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                    .build());
        }

        //------------------------------------------------------ Email
        if (spec.emailID != null) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, spec.emailID)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .build());
        }

        //------------------------------------------------------ Organization
        if (!Strings.isNullOrEmpty(spec.company) && !Strings.isNullOrEmpty(spec.jobTitle)) {
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, spec.company)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, spec.jobTitle)
                    .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                    .build());
        }
*/
}
