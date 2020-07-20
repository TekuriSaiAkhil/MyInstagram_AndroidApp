package com.example.myinstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chat extends AppCompatActivity {

    EditText t1;
    ListView r1;
    ChatlistAdapter listAdapter;
    DatabaseReference reference,reference1,reference2;
    List<String> name,dps,lastseen,tempname,tempdps;
    String uname;
    List<String> uname_data;
    Map<String,String> friend_pics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        t1 = (EditText) findViewById(R.id.search);
        r1 = (ListView) findViewById(R.id.chatlist);
        uname = SaveSharedPreference.getUserName(Chat.this);
        reference = FirebaseDatabase.getInstance().getReference("chatlist");
        reference1 = FirebaseDatabase.getInstance().getReference("users");
        reference2 = FirebaseDatabase.getInstance().getReference("dps");

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uname_data = new ArrayList<>();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    uname_data.add(dataSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                friend_pics = new HashMap<>();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    DpHelperClass dpHelperClass = dataSnapshot.getValue(DpHelperClass.class);
                    friend_pics.put(dataSnapshot.getKey(),dpHelperClass.getDpurl());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        t1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tempname = new ArrayList<>();
                tempdps = new ArrayList<>();
                if(!String.valueOf(s).equals("")) {

                    for (int i = 0; i < uname_data.size(); i++) {
                        if ((uname_data.get(i)).indexOf(String.valueOf(s)) != -1) {
                            tempname.add(uname_data.get(i));
                            if(friend_pics.containsKey(uname_data.get(i))){
                                tempdps.add(friend_pics.get(uname_data.get(i)));
                            }else{
                                tempdps.add("nothing");
                            }
                        }
                    }
                }else{
                    tempname = name;
                    tempdps = dps;
                }

                listAdapter = new ChatlistAdapter(Chat.this,tempname,tempdps);
                r1.setAdapter(listAdapter);
            }
        });



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = new ArrayList<>();
                dps = new ArrayList<>();
                lastseen = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    if((dataSnapshot.getKey()).equals(uname)){
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            name.add(ds.getKey());
                            Lastseen ls = ds.getValue(Lastseen.class);
                            lastseen.add(ls.getLastseen());
                            if(friend_pics.containsKey(ds.getKey())){
                                dps.add(friend_pics.get(ds.getKey()));
                            }else{
                                dps.add("nothing");
                            }
                        }
                    }
                }

                tempname = name;
                tempdps = dps;
                listAdapter = new ChatlistAdapter(Chat.this,name,dps);
                r1.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        r1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),pschat.class);
                i.putExtra("fri",tempname.get(position));
                i.putExtra("dp",tempdps.get(position));
                startActivity(i);
            }
        });


    }
}