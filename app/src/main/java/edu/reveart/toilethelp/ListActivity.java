package edu.reveart.toilethelp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "ListActivity";
    DatabaseReference ref;
    RecyclerView recyclerView;
    ArrayList<Help> helpList;
    ArrayList<String> messageList;
    HelpAdapter adapter;
    ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ref = FirebaseDatabase.getInstance().getReference("help");

        recyclerView = findViewById(R.id.rclView);

        helpList = new ArrayList<Help>();
        messageList = new ArrayList<>();

        adapter = new HelpAdapter(helpList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent detailIntent = new Intent(ListActivity.this, DetailActivity.class);
                detailIntent.putExtra("helpinfo", helpList.get(position));
                startActivity(detailIntent);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new Decoration(30));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Help help = dataSnapshot.getValue(Help.class);
                helpList.add(help);
                messageList.add(help.getMessage());
                adapter.notifyDataSetChanged();

                Log.d(TAG, "도움요청: " + help.toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        ref.addChildEventListener(childEventListener);
    }
}
