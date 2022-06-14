package com.example.myfarm.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myfarm.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    private EditText userName;
    private EditText phoneNo;
    private EditText emailId;
    private EditText stateName;
    private ImageView gmailPic;
    public String download = null;
    private Button signup;
    private FirebaseAuth mAuth;
    Uri selectedImageUri;

    private ImageView profilePic;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        profilePic = findViewById(R.id.profile);
        userName = findViewById(R.id.username);
        phoneNo = findViewById(R.id.phoneno1);
        emailId = findViewById(R.id.email);
        stateName = findViewById(R.id.state);
        gmailPic = findViewById(R.id.gmail);
        signup = findViewById(R.id.next);
        mAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkPermissionForImage();


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName.getText().toString();
                String phone = phoneNo.getText().toString();
                String email = emailId.getText().toString();
                String state = stateName.getText().toString();

                if (checkfields(name, phone, email, state)) {
                    Toast.makeText(getApplicationContext(), "Invalid Entry", Toast.LENGTH_SHORT).show();
                } else {

                    if (download != null) {
                        Intent i = new Intent(SignUpActivity.this, signUpOtpActivity.class);
                        i.putExtra("phoneNumber", phone);
                        i.putExtra("profileName", name);
                        i.putExtra("emailId", email);
                        i.putExtra("stateName", state);
                        i.putExtra("down", download);

                        startActivity(i);
                    }
                }


            }
        });


    }

    public static boolean checkfields(String name, String phone, String email, String state) {

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || state.isEmpty()) {
            return true;
        }
        if (containDigit(name) || containDigit(state)) {
            return true;
        }
        if (phone.length() < 10) {
            return true;
        }
        if (!email.contains("@")) {
            return true;
        }

        return false;
    }

    private static boolean containDigit(String name) {
        char[] chars = name.toCharArray();
        for (char c : chars) {
            if (Character.isDigit(c) || c == '@' || c == '#' || c == '.') {
                return true;
            }
        }
        return false;
    }

    private void checkPermissionForImage() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);

        } else {
            pickImageFromGallary();
        }

    }

    private void pickImageFromGallary() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        startActivityForResult(gallery, 100);


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == Activity.RESULT_OK && requestCode == 100 && data.getData() != null) {

            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                Glide.with(getApplicationContext()).load(selectedImageUri).apply(RequestOptions.circleCropTransform()).into(profilePic);
                upLoadImage(selectedImageUri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void upLoadImage(Uri selectedImageUri) {
        Log.d("check", "check");
        // Bitmap bmp = null;
        // try {
        //     bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
        //   } catch (IOException e) {
        //        e.printStackTrace();
        //      }
        //Bitmap s=rotateImageIfRequired(getApplicationContext(),bmp,selectedImageUri);

        //ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //s.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        // byte[] fileInBytes = baos.toByteArray();
        StorageReference ref = storageReference.child("uploads/" + "profilePic");


        //UploadTask uploadTask=ref.putBytes(fileInBytes);
        UploadTask uploadTask = ref.putFile(selectedImageUri);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {

                    download = task.getResult().toString();
                    Log.i("DOWNLOAD", download);


                } else {

                }
            }
        });


    }
/*
    private  Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) {

        // Detect rotation
        int rotation = getRotation( context, selectedImage);
        if (rotation != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(new Float( rotation));
            Bitmap  rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
            img.recycle();
            return rotatedImg;
        }
        else{
            return img;
        }
    }


    int getRotation(Context context, Uri imageSelected){
        int  rotation = 0;
        ContentResolver content  =context.getContentResolver();
        String[] arr=new String[2];
        arr[0]="orientation";
        arr[1]="date_added";


        Cursor mediaCursor = content.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,arr, null, null, "date_added desc");
        if (mediaCursor != null && mediaCursor.getCount() != 0) {
            while(mediaCursor.moveToNext()){
                rotation = mediaCursor.getInt(0);
                break;
            }
        }
        mediaCursor.close();
        return rotation;

    }*/

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100: {
                if (grantResults.length > 0
                        && (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    pickImageFromGallary();

                } else {

                    Toast.makeText(getApplicationContext(), "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                }
                break;
            }


        }
    }


}