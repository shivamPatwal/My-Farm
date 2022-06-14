package com.example.myfarm.fragement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.myfarm.R;

public class fragmentIrrigation extends Fragment {
    String irrigation;
    private CardView irrigation1;
    private CardView irrigation2;
    private CardView irrigation3;
    private CardView irrigation4;

    @SuppressLint("ResourceAsColor")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_irrigation, container, false);

        Intent i=  getActivity().getIntent();
        irrigation=i.getStringExtra("irrigation-type");
        irrigation1=view.findViewById(R.id.irr1);

        irrigation2=view.findViewById(R.id.irr2);

        irrigation3=view.findViewById(R.id.irr3);

        irrigation4=view.findViewById(R.id.irr4);


        if(irrigation.equals("irr1")){
            irrigation1.setCardBackgroundColor(R.color.purple_500);
        }else if(irrigation.equals("irr2")){

            irrigation2.setCardBackgroundColor(R.color.purple_500);

        }
        else if(irrigation.equals("irr3")){

            irrigation3.setCardBackgroundColor(R.color.purple_500);
        }
        else {

            irrigation4.setCardBackgroundColor(R.color.purple_500);
        }


        return view;
    }
}
