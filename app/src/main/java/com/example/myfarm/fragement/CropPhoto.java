package com.example.myfarm.fragement;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.icu.util.Calendar;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myfarm.R;
import com.example.myfarm.activities.diseaseActivity;
import com.example.myfarm.ml.DiseaseDetection;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CropPhoto extends Fragment {
    ImageView imageView;
    TextView button;
    TextView button1;
    public Bitmap image = null;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_cropphoto, container, false);
        imageView = view.findViewById(R.id.imageView);
        button = view.findViewById(R.id.read);
        button1 = view.findViewById(R.id.read1);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(getContext(), diseaseActivity.class);
                intent.putExtra("Bitmap", "open");
                startActivity(intent);

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(getContext(), diseaseActivity.class);
                intent.putExtra("Bitmap", "opencamera");
                startActivity(intent);


            }
        });

        return view;
    }


}
