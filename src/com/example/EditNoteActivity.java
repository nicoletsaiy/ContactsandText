package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ashish
 * Date: 6/10/12
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class EditNoteActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.PayMeBack.MESSAGE";
    String action = new String(),action2 = new String(),oldnote = new String();

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.editnote);
        Intent intent = getIntent();
        EditText text = (EditText) findViewById(R.id.editText1);

        Bundle extras = intent.getExtras();
        Set<String> keypair = extras.keySet();

        if ( extras.getString("ACTION").equals("oldnote")) {
            action = "oldnote";
            if ( keypair.size() == 3 ) {
            if ( extras.getString("ACTION2").equals("replace")) {
                action2 = "replace";
            }
            }
            oldnote = extras.getString(EXTRA_MESSAGE);
            text.setText(extras.getString(EXTRA_MESSAGE));
        }
    }

    public void onClickSave(View theButton) {
        EditText text = (EditText) findViewById(R.id.editText1);
        NotesDatabase db =new NotesDatabase(this);

        if ( action.equals("oldnote") && action2.equals("replace")) {
            db.searchAndDelete(oldnote);
        }
        db.addNote(new Notes(text.getText().toString()));
        finish();
    }
    public void onClickBack(View theButton) {
        finish();
    }
}