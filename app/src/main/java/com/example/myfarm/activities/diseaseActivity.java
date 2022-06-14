package com.example.myfarm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myfarm.R;
import com.example.myfarm.database.locDbHelper;
import com.example.myfarm.fragement.CropPhoto;
import com.example.myfarm.ml.DiseaseDetection;


import org.checkerframework.checker.units.qual.C;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class diseaseActivity extends AppCompatActivity {
    TextView result;
CardView card;
    String detect;
    TextView confidence;
ImageView know_more,post;
    TextView confidencetext;
    String accuracy;
    ImageView imageView;
    Button button;
 int imageSize = 224;
    Uri imageuri;
    Bitmap image = null;
    public Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);
        dialog = new Dialog(diseaseActivity.this);
        result = findViewById(R.id.textview);
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        confidence=findViewById(R.id.confidence);
know_more=findViewById(R.id.know);
post=findViewById(R.id.post);
        confidencetext=findViewById(R.id.confidenceText);
card=findViewById(R.id.card);
        Intent i = getIntent();
        String check = i.getStringExtra("Bitmap");

        if (check.equals("open")) {


            if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {


                ActivityCompat.requestPermissions(getParent(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        100);

            } else {
                pickImageFromGallary();
            }
        }

        if (check.equals("opencamera")) {
            Log.d("ssh","op");
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1);
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
            }

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setVisibility(View.GONE);
                  classifyImage(image);

            }
        });

know_more.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/search?q=" + "about "+result.getText())));

    }
});

post.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(diseaseActivity.this, PostActivity.class);
        i.putExtra("imageUri", imageuri.toString());
    startActivity(i);
    }
});

    }


    private void pickImageFromGallary() {

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);


        startActivityForResult(gallery, 100);


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == 100 && data.getData()!=null) {

            imageuri = data.getData();


            try {
                image = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), imageuri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
        imageView.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        imageView.setImageBitmap(image);

            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
           // classifyImage(image);


        }

        if (resultCode == Activity.RESULT_OK && requestCode == 1 ) {

            image = (Bitmap) data.getExtras().get("data");

            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            imageView.setVisibility(View.VISIBLE);
          button.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(image);
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);


        }


        super.onActivityResult(requestCode, resultCode, data);


    }


    private void classifyImage(Bitmap image) {
        try {
            DiseaseDetection model = DiseaseDetection.newInstance(getApplicationContext());

            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());
            int[] intValue = new int[imageSize * imageSize];
            image.getPixels(intValue, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

            int pixel = 0;
            for (int i = 0; i < imageSize; i++) {
                for (int j = 0; j < imageSize; j++) {
                    int val = intValue[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);
            DiseaseDetection.Outputs outputs = model.process(inputFeature0);

            TensorBuffer outputFeatures0 = outputs.getOutputFeature0AsTensorBuffer();
            float[] confidence = outputFeatures0.getFloatArray();

            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidence.length; i++) {
                if (confidence[i] > maxConfidence) {
                    maxConfidence = confidence[i];
                    maxPos = i;
                }
            }

            String[] classes = {"Healthy Apple", "Apple Blotch", "Apple Rot", "Apple Scab", "Healthy Tomato", "Tomato Bacterial Spot", "Tomato mosaic virus"};
            detect=classes[maxPos];
            Float acc=maxConfidence*100;
            accuracy= String.valueOf(acc).substring(0,5);

            openDialog();



            model.close();
        } catch (IOException e) {

        }

    }

    public void openDialog() {
           dialog.setContentView(R.layout.dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView imageView1 = dialog.findViewById(R.id.dialogimage);
        TextView disease=dialog.findViewById(R.id.textView);
        TextView accurac=dialog.findViewById(R.id.textView3);
        imageView1.setImageBitmap(image);
        disease.setText(detect);
        accurac.setText("Accuracy: "+accuracy+" %");
        Button button = dialog.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setVisibility(View.VISIBLE);
                card.setVisibility(View.VISIBLE);
                if(detect.contains("Healthy")){
                    confidencetext.setVisibility(View.INVISIBLE);
                Log.d("ss","dsds");
                }
                locDbHelper myDb=new locDbHelper(getApplicationContext());
                myDb.addLoc(detect,imageuri.toString(),accuracy);
                result.setText(detect);
                confidence.setText(accuracy+" %");
                dialog.dismiss();


            }
        });
        dialog.show();
    }


};







