package com.example.myinstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class commentstab extends AppCompatActivity {

    ListView listView;
    CommentListAdapter listAdapter;
    DatabaseReference reference;
    List<String> cna,com;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentstab);

        Intent intent = getIntent();
        id = intent.getStringExtra("postid");

        listView = (ListView) findViewById(R.id.comments);
        reference = FirebaseDatabase.getInstance().getReference("Comments");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cna = new ArrayList<>();
                com = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CommentsHelperClass commentsHelperClass = dataSnapshot.getValue(CommentsHelperClass.class);
                    if((commentsHelperClass.getPostId()).equals(id)){
                        cna.add(commentsHelperClass.getAuthor());
                        com.add(commentsHelperClass.getComment());
                    }
                }

                Collections.reverse(cna);
                Collections.reverse(com);
                listAdapter = new CommentListAdapter(commentstab.this,cna,com);
                listView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
