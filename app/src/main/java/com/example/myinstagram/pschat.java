package com.example.myinstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class pschat extends AppCompatActivity {

    TextView t1;
    EditText e1;
    ImageButton b1;
    ImageView imageView;
    DatabaseReference reference,reference1;
    ListView l1;
    PsChatlistAdapter pschatlistAdapter;
    String uname;
    String friend,friend_dp;
    List<String> from,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pschat_backup);

        t1 = (TextView) findViewById(R.id.friend);
        e1 = (EditText) findViewById(R.id.chatbox);
        b1 = (ImageButton) findViewById(R.id.send);
        l1 = (ListView) findViewById(R.id.chat_history);
        imageView = (ImageView) findViewById(R.id.friend_dp_ps);
        uname = SaveSharedPreference.getUserName(pschat.this);
        Intent intent = getIntent();
        friend = intent.getStringExtra("fri");
        friend_dp = intent.getStringExtra("dp");
        reference = FirebaseDatabase.getInstance().getReference("chatHistory");
        reference1 = FirebaseDatabase.getInstance().getReference("chatlist");

        t1.setText(friend);
        Picasso.get().load(friend_dp).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(imageView);
        e1.getBackground().setAlpha(100);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = e1.getText().toString();
                s1.trim();
                if(!s1.equals("")){
                    pschatHelperClass pschat = new pschatHelperClass(uname,s1,friend);
                    Lastseen lc  = new Lastseen(System.currentTimeMillis()+"");
                    String uploadId = reference.push().getKey();
                    reference.child(uploadId).setValue(pschat);
                    e1.setText("");
                    reference1.child(uname).child(friend).setValue(lc);
                    reference1.child(friend).child(uname).setValue(lc);
                }
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                from = new ArrayList<>();
                message = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    pschatHelperClass ps = dataSnapshot.getValue(pschatHelperClass.class);
                    if((ps.getUser()).equals(uname) && (ps.getFriend()).equals(friend)){
                        from.add(uname);
                        message.add(ps.getMessage());
                    }
                    if((ps.getUser()).equals(friend) && (ps.getFriend()).equals(uname)){
                        from.add(friend);
                        message.add(ps.getMessage());
                    }
                }
                pschatlistAdapter = new PsChatlistAdapter(pschat.this,from,message);
                l1.setAdapter(pschatlistAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}