package com.example.myfarm.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.myfarm.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class PostActivity extends AppCompatActivity {

    private ImageView back;
    private TextView post;
    private EditText postEditText;
    private TextView name;
    private ImageView postCamera;

    private ImageView delete;
    private Button shareButton;
    private ImageView userphoto;
    FirebaseAuth mAuth;
    Uri selectedImageUri=null;
    public String download = null;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mAuth = FirebaseAuth.getInstance();



        FirebaseUser user = mAuth.getCurrentUser();
        back = findViewById(R.id.back);
        post = findViewById(R.id.upload);
        postEditText = findViewById(R.id.edittext);
        name = findViewById(R.id.name);
        postCamera = findViewById(R.id.touch);
        userphoto = findViewById(R.id.userUpload);
        shareButton = findViewById(R.id.shareButton);
        delete=findViewById(R.id.delete);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (postEditText.getText().toString().length() == 0 && download==null) {
                    finish();
                } else {

                    alertDialog();
                }

            }
        });
post.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(postEditText.getText().length()==0 && selectedImageUri==null){

        }else{

        upLoadImage(selectedImageUri);
    }
    }
});

        postCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkPermissionForImage();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userphoto.setVisibility(View.INVISIBLE);
                postCamera.setVisibility(View.VISIBLE);
                delete.setVisibility(View.INVISIBLE);
                download=null;
            }
        });


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(postEditText.getText().length()==0 && selectedImageUri==null){

                }else{

                    upLoadImage(selectedImageUri);
                }

               }
        });


        Intent i=getIntent();
        String action=null;
        action=i.getStringExtra("imageUri");
        if(action!=null){

            newupload(action);

        }


    }

    private void newupload(String action) {
        Uri myUri = Uri.parse(action);
       selectedImageUri=myUri;
        if (selectedImageUri != null) {
            userphoto.setVisibility(View.VISIBLE);
            postCamera.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(selectedImageUri).into(userphoto);
        }


    }

    private void alertDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Do you really want to go back!");
        //dialog.setTitle("Dialog Box");
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        finish();
                    }
                });
        dialog.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

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

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {

            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                userphoto.setVisibility(View.VISIBLE);
                postCamera.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext()).load(selectedImageUri).into(userphoto);
            }
        }

    }

    private void upLoadImage(Uri selectedImageUri) {
        ProgressDialog pd=new ProgressDialog(this);

        pd.setMessage("Uploading");
        pd.show();
        Calendar   calendar = Calendar.getInstance();
        SimpleDateFormat   simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String Date = simpledateformat.format(calendar.getTime());
        String[] d=Date.split(" ");

        if(selectedImageUri!=null) {


            StorageReference ref = FirebaseStorage.getInstance().getReference("Posts").child(System.currentTimeMillis() + "." + getFileExtension(selectedImageUri));
            UploadTask uploadTask=ref.putFile(selectedImageUri);




            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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


                    download = task.getResult().toString();

                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Posts");
                    String postId= reference.push().getKey();
                    HashMap<String , String>map1=new HashMap<>();
                    map1.put("postid",postId);
                    map1.put("imageurl",download);
                    map1.put("description",postEditText.getText().toString());
                    map1.put("publisher",FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                    map1.put("date", d[0]);
                    map1.put("time", d[1]);


                    reference.child(postId).setValue(map1);





                    pd.dismiss();
                    finish();



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }else {

            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Posts");
            String postId= reference.push().getKey();
            HashMap<String , String>map1=new HashMap<>();
            map1.put("postid",postId);
            map1.put("imageurl","null");
            map1.put("description",postEditText.getText().toString());
            map1.put("publisher",FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
            map1.put("date", d[0]);
            map1.put("time", d[1]);
            reference.child(postId).setValue(map1);

            pd.dismiss();
            finish();



        }

    }
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

    }

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




    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(uri));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
          alertDialog();
        return false;
    }

}
