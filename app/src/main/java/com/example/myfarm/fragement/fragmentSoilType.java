package com.example.myfarm.fragement;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfarm.R;

public class fragmentSoilType extends Fragment {
    private ImageView soil1;
    private ImageView soil2;
    private ImageView soil3;
    private ImageView soil4;
    private ImageView soil5;
String soil;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_soil, container, false);

        soil1=view.findViewById(R.id.soil1);

        soil2=view.findViewById(R.id.soil2);

        soil3=view.findViewById(R.id.soil3);

        soil4=view.findViewById(R.id.soil4);

        soil5=view.findViewById(R.id.soil5);

        Intent i=  getActivity().getIntent();
        soil=i.getStringExtra("soil-type");

        soilFun();


        return view;
    }

    private void soilFun() {

        switch (soil){
            case "soil1":
                soil1.setBackgroundResource(R.drawable.border);

                break;
            case "soil2":
                soil2.setBackgroundResource(R.drawable.border);

                break;

            case "soil3":
                soil3.setBackgroundResource(R.drawable.border);

                break;

            case "soil4":
                soil4.setBackgroundResource(R.drawable.border);

                break;

            case "soil5":
                soil5.setBackgroundResource(R.drawable.border);

                break;



        }


    }
}
