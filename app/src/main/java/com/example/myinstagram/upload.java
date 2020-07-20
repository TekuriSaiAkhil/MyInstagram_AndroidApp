package com.example.myinstagram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class upload extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    EditText e1;
    Button b1,b2;
    ImageView imageView;

    StorageReference mStorageRef;
    DatabaseReference reference;
    StorageTask muplode;

    private Uri mImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        if(SaveSharedPreference.getUserName(upload.this).length()==0){
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }else{
        }

        e1 = (EditText) findViewById(R.id.comment);
        b1 = (Button) findViewById(R.id.gallery);
        b2 = (Button) findViewById(R.id.upload);
        imageView = (ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        final String uname = intent.getStringExtra("uname");

        mStorageRef = FirebaseStorage.getInstance().getReference("feeds/");
        reference = FirebaseDatabase.getInstance().getReference("feeds");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(muplode!=null && muplode.isInProgress()){
                    // do nothing
                }else{
                    uploadImage(uname);

                }

            }
        });


    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null){
                mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(imageView);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadImage(final String uname){
        if(mImageUri!=null){
            e1.setEnabled(false);
            b2.setEnabled(false);
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
            +"."+getFileExtension(mImageUri));

            muplode=fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    FeedsHelperClass feed = new FeedsHelperClass(uname,e1.getText().toString(),uri.toString());
                                    String uploadId = reference.push().getKey();
                                    reference.child(uploadId).setValue(feed);
                                    e1.setEnabled(true);
                                    b2.setEnabled(true);
                                    finish();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e1.setEnabled(true);
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
            b2.setEnabled(true);
            Toast.makeText(getApplicationContext(),"no image selected ",Toast.LENGTH_SHORT).show();
        }

    }
}
