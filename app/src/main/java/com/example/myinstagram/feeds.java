package com.example.myinstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class feeds extends AppCompatActivity {

    TextView t1;
    Button b1,b2,b3,b4;



    int [] image={R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground};
    String[] values = {"Android Alpha", "Android Beta", "Android Cupcake", "Android Donut"};
    ListView lView;
    ListAdapter lAdapter;
    private ProgressBar progresscircle;

    private DatabaseReference databaseReference;
    private List<FeedsHelperClass> feeds;
    private List<String> feedsId;

    String uname;
    String phoneno;
    public static FeedsHelperClass temp;
    public static String tempId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
        if(SaveSharedPreference.getUserName(feeds.this).length()==0){
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }else{
            uname = SaveSharedPreference.getUserName(feeds.this);
            phoneno = SaveSharedPreference.getphonenumber(feeds.this);


        }


        progresscircle = findViewById(R.id.progress_circular);
        t1 = (TextView) findViewById(R.id.username);
        b1 = (Button) findViewById(R.id.chat);
        b2 = (Button) findViewById(R.id.addpost);
        b3 = (Button) findViewById(R.id.home);
        b4 = (Button) findViewById(R.id.account);




       b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Chat.class);
                startActivity(i);
            }
        });


        lView = (ListView) findViewById(R.id.androidList);
        feeds = new ArrayList<>();
        feedsId = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("feeds");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    feeds = new ArrayList<>();
                    feedsId = new ArrayList<>();
                    for(DataSnapshot feedsSnapshot : snapshot.getChildren()){
                        String s = feedsSnapshot.getKey();
                        feedsId.add(s);
                        FeedsHelperClass  f = feedsSnapshot.getValue(FeedsHelperClass.class);
                        feeds.add(f);
                    }
                    Collections.reverse(feedsId);
                    Collections.reverse(feeds);
                    lAdapter = new ListAdapter(feeds.this,feeds);
                    lView.setAdapter(lAdapter);
                    progresscircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progresscircle.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                temp = feeds.get(position);
                tempId = feedsId.get(position);
                Intent i  = new Intent(getApplicationContext(),post.class);
                startActivity(i);
            }
        });

/*        lAdapter = new ListAdapter(feeds.this, values, image);
        lView.setAdapter(lAdapter);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(feeds.this, values[i], Toast.LENGTH_SHORT).show();
            }
        });*/



        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),upload.class);
                i.putExtra("uname",uname);
                i.putExtra("phoneno",phoneno);
                startActivity(i);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });


        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),account.class);
                i.putExtra("uname",uname);
                i.putExtra("phoneno",phoneno);
                startActivity(i);
            }
        });

    }



}
