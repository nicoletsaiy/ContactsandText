package com.example;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: ashish
 * Date: 10/10/12
 * Time: 10:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class SendAsSmsActivity extends Activity {
    String sms = new String();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendassmsactivity);
        Intent intent = getIntent();
        EditText text = (EditText) findViewById(R.id.editText2);
        Bundle extras = intent.getExtras();
        sms = extras.getString("SMS");
        text.setText(sms);
        EditText text1 = (EditText) findViewById(R.id.editText1);
        registerForContextMenu (text1);
    }
    public void onClickSend ( View button){
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this,0,new Intent(SENT),0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this,0,new Intent(DELIVERED),0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager smsManager = SmsManager.getDefault();

        EditText text1 = (EditText) findViewById(R.id.editText1);
        Log.v("phoneNumber", text1.getText().toString());
        Log.v("MEssage",sms);
        smsManager.sendTextMessage(text1.getText().toString(), null, sms, sentPI, deliveredPI);
        finish();
    }
    public void onClickCancel( View button){
        finish();
    }
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //currentNote = ((TextView)v).getText().toString();
        // Create your context menu here
        // Clear current contents
        menu.clearHeader();
        menu.clear();

        menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, "Contacts");
    }
    public boolean onContextItemSelected(MenuItem item) {
        // Call your function to preform for buttons pressed in a context menu
        // can use item.getTitle() or similar to find out button pressed
        // item.getItemID() will return the v.getID() that we passed before
        super.onContextItemSelected(item);

        if ( item.getTitle().toString().equals("Contacts")){
            Intent intent = new Intent(this,readAllActivity.class);
            startActivityForResult( intent, 0);
        }
        return true;
    }
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String myValue = data.getStringExtra("number");
                EditText text1 = (EditText) findViewById(R.id.editText1);
                text1.setText(myValue);
            }
        }
    }
}