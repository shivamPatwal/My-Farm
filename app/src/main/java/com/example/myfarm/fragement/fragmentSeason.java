package com.example.myfarm.fragement;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfarm.R;
import com.example.myfarm.activities.detailActivity;

public class fragmentSeason extends Fragment {

    String sowing;
    ImageView imageView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_season, container, false);

        Intent i = getActivity().getIntent();
        sowing = i.getStringExtra("sowing");
        imageView = view.findViewById(R.id.imageView14);
        Log.d("ss",sowing);
        image();
        // imageView.setImageDrawable(Drawable.createFromPath(s));
        return view;
    }

    private void image() {
        switch (sowing) {
            case "apple":

                imageView.setImageResource(R.drawable.sowing);
                break;
            case "corn":

                imageView.setImageResource(R.drawable.corn_s);

                break;
            case "orange":

                imageView.setImageResource(R.drawable.orange_s);

                break;

            case "banana":

                imageView.setImageResource(R.drawable.banana_s);

                break;

            case "pomegrant":


                imageView.setImageResource(R.drawable.pomegrant_s);
                break;

            case "papaya":

                imageView.setImageResource(R.drawable.papaya_s);


                break;

            case "litchi":

                imageView.setImageResource(R.drawable.sowing);
                break;

            case "coconut":

                imageView.setImageResource(R.drawable.sowing);

                break;

            case "mango":

                imageView.setImageResource(R.drawable.sowing);
                break;

            case "patato":

                imageView.setImageResource(R.drawable.sowing);
                break;

            case "tamato":


                imageView.setImageResource(R.drawable.sowing);
                break;

            case "bottle_gourd":


                imageView.setImageResource(R.drawable.sowing);
                break;

            case "brinjal":


                imageView.setImageResource(R.drawable.sowing);
                break;

            case "cabbage":


                imageView.setImageResource(R.drawable.sowing);
                break;

            case "lemon":


                imageView.setImageResource(R.drawable.sowing);
                break;

            case "carot":


                imageView.setImageResource(R.drawable.sowing);
                break;

            case "chillies":

                imageView.setImageResource(R.drawable.sowing);
                break;

            case "spanish":


                imageView.setImageResource(R.drawable.sowing);
                break;

            case "caufifower":


                imageView.setImageResource(R.drawable.sowing);
                break;

            case "capsicum":

                imageView.setImageResource(R.drawable.sowing);

                break;

            case "peas":


                imageView.setImageResource(R.drawable.sowing);
                break;

            case "kidneybeans":


                imageView.setImageResource(R.drawable.sowing);
                break;

            case "black_gram":


                imageView.setImageResource(R.drawable.sowing);
                break;

            case "green_gram":


                imageView.setImageResource(R.drawable.sowing);
                break;

            case "sugar_cane":


                imageView.setImageResource(R.drawable.sowing);
                break;

            case "groundnut":

                imageView.setImageResource(R.drawable.sowing);
                break;

            case "rice":

                imageView.setImageResource(R.drawable.sowing);
                break;


        }

    }
}
