package com.example;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MyActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.PayMeBack.MESSAGE";
    String currentNote = new String();
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    public void addNote ( View theButton) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra("ACTION","addnote");
        startActivity(intent);
    }
    public void onClickTextView1 ( View textview) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra("ACTION","oldnote");
        intent.putExtra(EXTRA_MESSAGE,((TextView)textview).getText().toString());
        startActivity(intent);
    }
    public void onResume () {
        super.onResume();
        setContentView(R.layout.main);

        LinearLayout lLayout = (LinearLayout)findViewById(R.id.layout1);
        lLayout.removeAllViews();

        final LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Button add = (Button) inflater.inflate(R.layout.addnotebutton, null);
        lLayout.addView(add);
        NotesDatabase db =new NotesDatabase(this);

        List<Notes> notes = db.getAllNotes();

        for ( Notes nt: notes) {

            TextView b = (TextView) inflater.inflate(R.layout.textviews,null);
            b.setTextColor(Color.BLACK) ;
            b.setText(nt.getNote());
            registerForContextMenu(b);
            lLayout.addView(b);
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        currentNote = ((TextView)v).getText().toString();
        // Create your context menu here
        menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, "Edit n Replace");
        menu.add(0, v.getId(), 1, "Delete");
        menu.add(0, v.getId(), 2, "Send as SMS");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Call your function to preform for buttons pressed in a context menu
        // can use item.getTitle() or similar to find out button pressed
        // item.getItemID() will return the v.getID() that we passed before
        super.onContextItemSelected(item);

        if ( item.getTitle().toString().equals("Delete")){
            NotesDatabase db =new NotesDatabase(this);

            db.searchAndDelete(currentNote);
            onResume();
        }
        else if ( item.getTitle().toString().equals("Edit n Replace")) {
            Intent intent = new Intent(this, EditNoteActivity.class);
            intent.putExtra("ACTION","oldnote");
            intent.putExtra("ACTION2","replace");
            intent.putExtra(EXTRA_MESSAGE,currentNote);
            startActivity(intent);
        }
        else if (item.getTitle().toString().equals("Send as SMS")){
            Intent intent = new Intent(this, SendAsSmsActivity.class);
            intent.putExtra("SMS", currentNote);
            startActivity(intent);
        }

        return true;
    }
}
