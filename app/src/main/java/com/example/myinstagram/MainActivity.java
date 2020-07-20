package com.example.myinstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText e1,e2,e3;
    Button b1,b2;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db =new DatabaseHelper(this);
        e1 = (EditText) findViewById(R.id.username);
        e2 = (EditText)findViewById(R.id.phonenum);
        e3 = (EditText)findViewById(R.id.password);
        b1 = (Button)findViewById(R.id.register);
        b2 = (Button)findViewById(R.id.login);
        if(SaveSharedPreference.getUserName(MainActivity.this).length()!=0){
            Intent i = new Intent(getApplicationContext(),login.class);
            startActivity(i);
        }else{}

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();
                String s3 = e3.getText().toString();

                if(s1.equals("")||s2.equals("")||s3.equals("")){
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                }else {
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("users");
                    UserHelperClass helperClass = new UserHelperClass(s1, s2, s3);
                    reference.child(s1).setValue(helperClass);
                    Intent i = new Intent(MainActivity.this, login.class);
                    startActivity(i);
                }
            }
        });
















/*        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();
                String s3 = e3.getText().toString();
                if(s1.equals("")||s2.equals("")||s3.equals("")){

                    Toast.makeText(getApplicationContext(),"Fields are required",Toast.LENGTH_SHORT).show();

                }else{

                    if(db.check_username(s1)==true){
                        boolean added= db.insert_user_data(s1,s2,s3);
                        if(added==true){
                            Toast.makeText(getApplicationContext(),"Registration Sussesful",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this, login.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Username Already Taken",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });*/




        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,login.class);
                startActivity(i);
            }
        });

    }
}
