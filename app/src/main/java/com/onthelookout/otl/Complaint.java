package com.onthelookout.otl;

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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.onthelookout.otl.ui.home.HomeFragment;
import com.onthelookout.otl.ImageUrl;

import java.util.HashMap;

public class Complaint extends AppCompatActivity {
EditText info_complaint;
Button submit_complaint;
ImageView image_complaint;
ProgressBar progressBar_complaint;
//possible issue fire base reference
private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
private StorageReference reference = FirebaseStorage.getInstance().getReference();
private Uri imageUri;
    ImageUrl imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        info_complaint = findViewById(R.id.info_complaint);
        submit_complaint = findViewById(R.id.submit_complaint);
        image_complaint = findViewById(R.id.image_complaint);
        progressBar_complaint = findViewById(R.id.progressBar_complaint);

        progressBar_complaint.setVisibility(View.INVISIBLE);

        String type = getIntent().getStringExtra("type");

        image_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);
            }
        });
        submit_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri!= null) {
                    uploadToFirebase(imageUri);
                }
                HashMap<String ,Object> m = new HashMap<String, Object>();
                m.put("Information",info_complaint.getText().toString());
                m.put("Type",type);
                m.put("Image",imageUrl);
                root.child("Complaints").push().setValue(m);

                Intent intent = new Intent(Complaint.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data) {                 //possible issue placement
        super.onActivityResult(requestCode, resultCode,data);

        if(requestCode == 2 && resultCode == RESULT_OK && data!= null)  {                                      //possible error data

                imageUri = data.getData();
                image_complaint.setImageURI(imageUri);
        }
    }
    private void uploadToFirebase(Uri uri){
        StorageReference fileRef = reference.child(System.currentTimeMillis()+"."+ getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        ImageUrl imageUrl = new ImageUrl(uri.toString());
                        // possible error
                        Toast.makeText(Complaint.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar_complaint.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar_complaint.setVisibility(View.INVISIBLE);
                Toast.makeText(Complaint.this, "Uploading Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri muri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(muri));
    }
}