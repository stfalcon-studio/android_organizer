package com.stfalcon.organizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class HomeActivity extends Activity {

    private ListView listViewNotes;
    private HashSet<String> notes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void initViews(){
        listViewNotes = (ListView) findViewById(R.id.list_notes);
        listViewNotes.setEmptyView((TextView) findViewById(R.id.empty_view));

       listViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               notes.remove(listViewNotes.getItemAtPosition(position));
               HashSet<String> newSet = new HashSet<String>();
               newSet.addAll(notes);
               OrganizerApp.getInstans().saveSetInPreferences(newSet);
               getData();
           }
       });
    }

    private void getData(){
        notes = (HashSet<String>) OrganizerApp.getInstans().getSavedNotes();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, notes.toArray(new String[notes.size()]));
        listViewNotes.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(0, 0, 0, "");
        item.setIcon(R.drawable.ic_edit);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(HomeActivity.this,
                        CreateNotes.class));
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
