package com.rb.rexben.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String DATABASE_CONFIG = "Diary";
    private static final String TAG = "MAIN_ACTIVITY";
    FloatingActionButton floatingActionButton;
    ProgressBar progressBar;
    EventEntry entry;
    RecyclerView entryList;
    String user;
    int count = 0;
    CustomAdapter adapter;
    FirebaseDatabase db;
    private List<EventEntry> entries;

    private FirebaseAuth auth;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getStarted();
    }

    private ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            entries.clear();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                entry = snapshot.getValue(EventEntry.class);
                entry.setCid(count);
                entries.add(entry);
                count++;
                adapter.notifyDataSetChanged();
                if (count >= dataSnapshot.getChildrenCount()) {
                    adapter.replaceData(entries);
                    count = 0;
                }
            }
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(MainActivity.this, "Error Occurred",
                    Toast.LENGTH_SHORT).show();
        }
    };

    private void getStarted() {
        setBaseGround();
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        entryList = findViewById(R.id.recyclerViewTasks);
        progressBar = findViewById(R.id.progressBar);
        entryList.setLayoutManager(linearLayoutManager);
        entries = new ArrayList<>();
        entry = new EventEntry();
        getEntries();
        setClicks();
        entryList.setAdapter(adapter);
    }



    private void setBaseGround() {
        db = FirebaseDatabase.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid();
    }

    private void getEntries() {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference entryRef = db.getReference(DATABASE_CONFIG);
        entryRef.addValueEventListener(eventListener);
    }

    private void setClicks() {
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNewEvent.class);
                startActivity(intent);
            }
        });
        adapter = new CustomAdapter(entries, new CustomAdapter.AdapterClickListener() {
            @Override
            public void onEntryClicked(EventEntry entry) {
                Intent i = new Intent(MainActivity.this, FullDetailActivity.class);
                i.putExtra(FullDetailActivity.ENTRY_ARGS, entry);
                startActivity(i);
            }
        });
    }

}
