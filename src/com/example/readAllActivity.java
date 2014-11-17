package com.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: ashish
 * Date: 13/10/12
 * Time: 7:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class readAllActivity extends Activity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        readContacts();
    }
    public void readContacts (){
        LinearLayout lLayout = (LinearLayout)findViewById(R.id.layout1);
        final LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
        while (cursor.moveToNext()) {
            String contactId = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts._ID));
            //String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            //if (Boolean.parseBoolean(hasPhone)) {
                // You know it has a number so now query it like this
                Cursor phones = getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId, null, null);
                while (phones.moveToNext()) {
                    String name = phones.getString(phones.getColumnIndex( ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    TextView b2 = (TextView) inflater.inflate(R.layout.textviews,null);
                    b2.setTextColor(Color.BLACK) ;
                    b2.setText(name);
                    registerForContextMenu(b2);
                    lLayout.addView(b2);

                    String phoneNumber = phones.getString(phones.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER));
                    TextView b = (TextView) inflater.inflate(R.layout.textviews,null);
                    b.setTextColor(Color.BLACK) ;
                    b.setText(phoneNumber);
                    registerForContextMenu(b);
                    lLayout.addView(b);

                }
                phones.close();
            //}

            Cursor emails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
            while (emails.moveToNext()) {
                // This would allow you get several email addresses
                String emailAddress = emails.getString(
                        emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            }
            emails.close();
        }
        cursor.close();
    }
    public void onClickTextView1(View v) {
        Intent resultData = new Intent();
        String s =((TextView)v).getText().toString();
        resultData.putExtra("number", s);
        setResult(Activity.RESULT_OK, resultData);
        finish();
    }
}