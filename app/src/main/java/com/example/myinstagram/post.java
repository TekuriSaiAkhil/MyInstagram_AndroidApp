package com.example.myinstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.myinstagram.feeds.temp;
import static com.example.myinstagram.feeds.tempId;


public class post extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5;
    EditText e1;
    ImageView imageView;
    DatabaseReference reference;
    String uname;
    public static ArrayList<CommentsHelperClass> c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        final FeedsHelperClass post=temp;
        uname = SaveSharedPreference.getUserName(post.this);

        t1 = (TextView) findViewById(R.id.author2);

        t3 =(TextView) findViewById(R.id.postcomment);
        t4 = (TextView) findViewById(R.id.commentcount);
        t5 = (TextView) findViewById(R.id.commentsection);
        e1 = (EditText) findViewById(R.id.addcomment);
        imageView = (ImageView) findViewById(R.id.image2);

        t1.setText(post.getUname()+": "+post.getComment());
        Picasso.get().load(post.getImageUrl()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(imageView);
        reference = FirebaseDatabase.getInstance().getReference("Comments");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer i=0;
                c=new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CommentsHelperClass commentsHelperClass = dataSnapshot.getValue(CommentsHelperClass.class);
                    if((commentsHelperClass.getPostId()).equals(tempId)){
                        i=i+1;
                        c.add(commentsHelperClass);
                    }
                }
                Collections.reverse(c);
                t4.setText("View All "+i.toString()+" comments");
                if(i>=3){
                    t5.setText(c.get(0).getAuthor()+": "+c.get(0).getComment()+"\n"+c.get(1).getAuthor()+": "+c.get(1).getComment()+"\n"+c.get(2).getAuthor()+": "+c.get(2).getComment()+"\n");
                }else if(i==2){
                    t5.setText(c.get(0).getAuthor()+": "+c.get(0).getComment()+"\n"+c.get(1).getAuthor()+": "+c.get(1).getComment()+"\n");
                }else if(i==1){
                    t5.setText(c.get(0).getAuthor()+": "+c.get(0).getComment()+"\n");
                }else {
                    t5.setText("No comments yet");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference("Comments");
                String s1 = e1.getText().toString();
                if(s1.equals("")){

                }else{
                    CommentsHelperClass comment = new CommentsHelperClass(uname,s1,tempId);
                    String uploadId = reference.push().getKey();
                    reference.child(uploadId).setValue(comment);
                    e1.setText("");
                    Toast.makeText(getApplicationContext(),"comment added",Toast.LENGTH_SHORT).show();
                }

            }
        });


        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),commentstab.class);
                String t = tempId;
                i.putExtra("postid",t);
                startActivity(i);

            }
        });

    }
}
