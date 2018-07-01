package com.rb.rexben.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FullDetailActivity extends AppCompatActivity {
    public static final String ENTRY_ARGS = "entry_args";
    private static final String DATABASE_CONFIG = "Diary";
    private FirebaseDatabase db;
    DatabaseReference entryRef;
    private EventEntry entries;
    TextView titleTextView;
    TextView detailTextView;
    TextView messageTextView;
    String getID;
    TextView timeTextView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_full);
        db = FirebaseDatabase.getInstance();
        entries = getIntent().getParcelableExtra(ENTRY_ARGS);
        titleTextView = findViewById(R.id.title);
        detailTextView = findViewById(R.id.details);
        messageTextView = findViewById(R.id.message);
        floatingActionButton = findViewById(R.id.fab);
        setUpView(entries);
    }

    void setUpView(EventEntry entry) {
        titleTextView.setText(entry.getTitle());
        detailTextView.setText(entry.getDetails());
        messageTextView.setText(entry.getSomeMessage());
        getID = entry.getId();
        //timeTextView.setText(entry.getPhone());
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FullDetailActivity.this, AddNewEvent.class);
                intent.putExtra(AddNewEvent.ENTRY_ARGS, entries);
                startActivity(intent);
            }
        });
    }

    private void DeleteData(String entry) {
        entryRef = db.getReference(DATABASE_CONFIG);
        entryRef.child(entry).removeValue();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            DeleteData(getID);
        }
        return super.onOptionsItemSelected(item);
    }

}
