package com.example.aashayj.contactsfetch;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		showContacts();

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions,
										   int[] grantResults) {
		if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// Permission is granted
				showContacts();
			} else {
				Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
			}
		}
	}

	void showContacts()
	{
		listView = findViewById(R.id.lists123);
		ArrayList<Android_Contacts> android_contactsArrayList = new ArrayList<>();
		Cursor cursor = null;
		ContentResolver contentResolver = getContentResolver();
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
				requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
				//After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
			} else {
				cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
			}
		}
		catch(Exception ex){
			Log.e("Error on contact", ex.getMessage());
		}

		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Android_Contacts android_contact = new Android_Contacts();
				String contact_id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
				String contact_display_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				android_contact.contact_name = contact_display_name;
				int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
				if (hasPhoneNumber > 0) {

					Cursor phoneCursor = contentResolver.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI
							, null
							, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?"
							, new String[]{contact_id}
							, null);

					while (phoneCursor.moveToNext()) {
						String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

						android_contact.contact_number = phoneNumber;

					}
					phoneCursor.close();
				}

				android_contactsArrayList.add(android_contact);
			}

			CustomList adapter = new CustomList(this, android_contactsArrayList);
			listView.setAdapter(adapter);
		}
	}


}
