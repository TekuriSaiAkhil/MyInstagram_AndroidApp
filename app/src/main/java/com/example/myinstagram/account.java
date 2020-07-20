package com.example.myinstagram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class account extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    TextView t1,t2;
    Button b1,b2;
    ImageView dp;
    private Uri mImageUri;
    StorageReference mStorageRef;
    DatabaseReference reference;
    StorageTask muplode;
    String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        if(SaveSharedPreference.getUserName(account.this).length()==0){
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }else{
        }
        t1= (TextView) findViewById(R.id.uname);
        t2 = (TextView) findViewById(R.id.phonenum);
        b1=(Button) findViewById(R.id.logout);
        b2= (Button) findViewById(R.id.uplodedp);
        dp = (ImageView) findViewById(R.id.dp);
        mStorageRef = FirebaseStorage.getInstance().getReference("dps/");
        reference = FirebaseDatabase.getInstance().getReference("dps");


        Intent intent = getIntent();

        uname = intent.getStringExtra("uname");
        final String phoneno = intent.getStringExtra("phoneno");
        t1.setText("Name: "+uname);
        t2.setText("Phno: "+phoneno);
        Picasso.get().load("nothing").placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(dp);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dpdataSnapshot: snapshot.getChildren()){
                    if((dpdataSnapshot.getKey()).equals(uname)){
                        DpHelperClass dpHelperClass = dpdataSnapshot.getValue(DpHelperClass.class);
                        Picasso.get().load(dpHelperClass.getDpurl()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(dp);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSharedPreference.setUserName(account.this,"");
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });



        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });

    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).fit().centerCrop().into(dp);
            uploadImage();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadImage(){
        if(mImageUri!=null){

            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    +"."+getFileExtension(mImageUri));

            muplode=fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    DpHelperClass feed = new DpHelperClass(uri.toString());
                                    reference.child(uname).setValue(feed);
                                    b2.setEnabled(true);
                                    /*finish();*/
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            b2.setEnabled(true);
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            int progress = (int) (100.0 *taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            Toast.makeText(getApplicationContext(),progress+"% uploded",Toast.LENGTH_LONG).show();
                            // add a text view and set this value on it;
                        }
                    });

        }else {

        }

    }

}
