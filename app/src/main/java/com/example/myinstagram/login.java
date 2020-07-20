package com.example.myinstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    DatabaseHelper db;
    EditText e1,e2;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        e1 = (EditText) findViewById(R.id.username);
        e2 = (EditText) findViewById(R.id.password);
        b1= (Button) findViewById(R.id.login);
/*        if(SaveSharedPreference.getUserName(login.this).length()!=0){
            Intent i = new Intent(getApplicationContext(),feeds.class);
            i.putExtra("uname",SaveSharedPreference.getUserName(login.this));
            startActivity(i);
        }else{

        }*/


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s1 = e1.getText().toString();
                final String s2 = e2.getText().toString();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

                Query checkuser = reference.orderByChild("username").equalTo(s1);

                checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            String passfromdb = snapshot.child(s1).child("password").getValue(String.class);

                            if(passfromdb.equals(s2)){
                                SaveSharedPreference.setUserName(login.this,s1);
                                String phonenumber = snapshot.child(s1).child("phonenumber").getValue(String.class);
                                SaveSharedPreference.setphonenumber(login.this,phonenumber);
                                Intent i =new Intent(getApplicationContext(),feeds.class);
/*                                i.putExtra("uname",s1);
                                i.putExtra("phoneno",phonenumber);*/
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                // true passwords
                            }else{
                                Toast.makeText(getApplicationContext(),"wrong password",Toast.LENGTH_SHORT).show();
                                // false passwords
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"user not found",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });









/*        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();
                *//*Toast.makeText(getApplicationContext(),s1,Toast.LENGTH_SHORT).show();

                Toast.makeText(getApplicationContext(),s2,Toast.LENGTH_SHORT).show();
*//*
                if(db.check_credentials(s1,s2)==true){

                    *//*Toast.makeText(getApplicationContext(),"User found",Toast.LENGTH_SHORT).show();*//*

                    SaveSharedPreference.setUserName(login.this,s1);

                    Intent i = new Intent(getApplicationContext(),feeds.class);
                    i.putExtra("uname",s1);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"Usernotfound",Toast.LENGTH_SHORT).show();
                }

            }
        });*/


    }
}
