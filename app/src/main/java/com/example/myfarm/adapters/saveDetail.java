package com.example.myfarm.adapters;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.myfarm.R;
import com.example.myfarm.activities.PostActivity;
import com.example.myfarm.activities.diseaseActivity;

public class saveDetail extends AppCompatActivity {
    String title;
    Uri imageuri;
    TextView confidence;

    ImageView know_more, post;
    TextView textview;
    ImageView imageView;
TextView confidencetext;
    CardView card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);
        Intent i = getIntent();
        String acc = i.getStringExtra("confidence");
        String imageUrl = i.getStringExtra("imageurl");
        title = i.getStringExtra("title");
        textview = findViewById(R.id.textview);
        know_more = findViewById(R.id.know);
        post = findViewById(R.id.post);

        confidencetext=findViewById(R.id.confidenceText);
        imageuri = Uri.parse(imageUrl);
        imageView = findViewById(R.id.imageView);
        card = findViewById(R.id.card);
        confidence = findViewById(R.id.confidence);
        card.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        Glide.with(getApplicationContext()).load(imageuri).into(imageView);

        if(title.contains("Healthy")){
            confidencetext.setVisibility(View.INVISIBLE);
        }
        textview.setText(title);
        confidence.setText(acc + " %");

        know_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/search?q=" + "about "+textview.getText())));

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(saveDetail.this, PostActivity.class);
                i.putExtra("imageUri", imageuri.toString());
                startActivity(i);
            }
        });



    }

}
