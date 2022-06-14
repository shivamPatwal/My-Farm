package com.example.myfarm.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.myfarm.R;
import com.example.myfarm.activities.detailActivity;

public class MyCrops extends Fragment implements View.OnClickListener {

    CardView apple;
    CardView corn;
    CardView orange;
    CardView banana;
    CardView pomegrant;
    CardView papaya;
    CardView litchi;
    CardView coconut;
    CardView mango;
    CardView patato;
    CardView tamato;
    CardView bottle_gourd;
    CardView brinjal;
    CardView cabbage;
    CardView lemon;
    CardView carot;
    CardView chillies;
    CardView spanish;
    CardView caufifower;
    CardView capsicum;
    CardView peas;
    CardView kidneybeans;
    CardView black_gram;
    CardView green_gram;
    CardView sugar_cane;
    CardView groundnut;
    CardView rice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.crop_information, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        apple=view.findViewById(R.id.apple);
        corn=view.findViewById(R.id.corn);
        orange=view.findViewById(R.id.orange);
         banana=view.findViewById(R.id.banana);
        pomegrant =view.findViewById(R.id.pomegrant);
        papaya =view.findViewById(R.id.papaya);
        litchi=view.findViewById(R.id.litchi);
        coconut =view.findViewById(R.id.coconut);
        mango =view.findViewById(R.id.mango);
        patato=view.findViewById(R.id.patato);
        tamato =view.findViewById(R.id.tamato);
        bottle_gourd =view.findViewById(R.id.bottle_gourd);
        brinjal=view.findViewById(R.id.brinjal);
        cabbage =view.findViewById(R.id.cabbage);
        lemon =view.findViewById(R.id.lemon);
        carot=view.findViewById(R.id.carot);
        chillies =view.findViewById(R.id.chillies);
        spanish =view.findViewById(R.id.spanish);
        caufifower=view.findViewById(R.id.caufifower);
        capsicum =view.findViewById(R.id.capsicum);
        peas =view.findViewById(R.id.peas);
        kidneybeans=view.findViewById(R.id.kidneybeans);
        black_gram =view.findViewById(R.id.black_gram);
        green_gram =view.findViewById(R.id.green_gram);
        sugar_cane=view.findViewById(R.id.sugar_cane);
        groundnut =view.findViewById(R.id.groundnut);
        rice =view.findViewById(R.id.rice);

        apple.setOnClickListener(MyCrops.this);
        corn.setOnClickListener(MyCrops.this);
        orange.setOnClickListener(MyCrops.this);
        banana.setOnClickListener(MyCrops.this);
        pomegrant.setOnClickListener(MyCrops.this);
        papaya.setOnClickListener(MyCrops.this);
        litchi.setOnClickListener(MyCrops.this);
        coconut.setOnClickListener(MyCrops.this);
        mango.setOnClickListener(MyCrops.this);
        patato.setOnClickListener(MyCrops.this);
        tamato.setOnClickListener(MyCrops.this);
        bottle_gourd.setOnClickListener(MyCrops.this);
        brinjal.setOnClickListener(MyCrops.this);
        cabbage.setOnClickListener(MyCrops.this);
        lemon.setOnClickListener(MyCrops.this);
        carot.setOnClickListener(MyCrops.this);
        chillies.setOnClickListener(MyCrops.this);
        spanish.setOnClickListener(MyCrops.this);
        caufifower.setOnClickListener(MyCrops.this);
        capsicum.setOnClickListener(MyCrops.this);
        peas.setOnClickListener(MyCrops.this);
        kidneybeans.setOnClickListener(MyCrops.this);
        black_gram.setOnClickListener(MyCrops.this);
        green_gram.setOnClickListener(MyCrops.this);
        sugar_cane.setOnClickListener(MyCrops.this);
        groundnut.setOnClickListener(MyCrops.this);
        rice.setOnClickListener(MyCrops.this);



        return  view;

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.apple:
                Intent i=new Intent(getContext(), detailActivity.class);
                i.putExtra("soil-type","soil2");
                i.putExtra("irrigation-type","irr1");
                i.putExtra("sowing","apple");

                startActivity(i);
                break;
            case R.id.corn:

                Intent i1=new Intent(getContext(), detailActivity.class);
                i1.putExtra("soil-type","soil1");
                i1.putExtra("irrigation-type","irr1");
                i1.putExtra("sowing","corn");
                startActivity(i1);
                break;
            case R.id.orange:

                Intent i2=new Intent(getContext(), detailActivity.class);
                i2.putExtra("soil-type","soil2");
                i2.putExtra("irrigation-type","irr2");
                i2.putExtra("sowing","orange");

                startActivity(i2);
                break;

            case R.id.banana:

                Intent i3=new Intent(getContext(), detailActivity.class);
                i3.putExtra("soil-type","soil4");
                i3.putExtra("irrigation-type","irr1");
                i3.putExtra("sowing","banana");

                startActivity(i3);
                break;

            case R.id.pomegrant:
                Intent i4=new Intent(getContext(), detailActivity.class);
                i4.putExtra("soil-type","soil5");
                i4.putExtra("irrigation-type","irr2");
                i4.putExtra("sowing","pomegrant");

                startActivity(i4);
                break;

            case R.id.papaya:
                Intent i5=new Intent(getContext(), detailActivity.class);
                i5.putExtra("soil-type","soil1");
                i5.putExtra("irrigation-type","irr2");
                i5.putExtra("sowing","papaya");

                startActivity(i5);

                break;

            case R.id.litchi:
                Intent i6=new Intent(getContext(), detailActivity.class);
                i6.putExtra("soil-type","soil4");
                i6.putExtra("irrigation-type","irr1");
                i6.putExtra("sowing","litchi");
                startActivity(i6);
                break;

            case R.id.coconut:
                Intent i7=new Intent(getContext(), detailActivity.class);
                i7.putExtra("soil-type","soil5");
                i7.putExtra("irrigation-type","irr3");
                i7.putExtra("sowing","coconut");
                startActivity(i7);
                break;

            case R.id.mango:
                Intent i8=new Intent(getContext(), detailActivity.class);
                i8.putExtra("soil-type","soil1");
                i8.putExtra("irrigation-type","irr2");
                i8.putExtra("sowing","mango");
                startActivity(i8);
                break;

            case R.id.patato:
                Intent i9=new Intent(getContext(), detailActivity.class);
                i9.putExtra("soil-type","soil2");
                i9.putExtra("irrigation-type","irr2");
                i9.putExtra("sowing","patato");
                startActivity(i9);
                break;

            case R.id.tamato:
                Intent i10=new Intent(getContext(), detailActivity.class);
                i10.putExtra("soil-type","soil1");
                i10.putExtra("irrigation-type","irr2");
                i10.putExtra("sowing","tamato");
                startActivity(i10);
                break;

            case R.id.bottle_gourd:

                Intent i11=new Intent(getContext(), detailActivity.class);
                i11.putExtra("soil-type","soil1");
                i11.putExtra("irrigation-type","irr1");

                i11.putExtra("sowing","bottle_gourd");
                startActivity(i11);
                break;

            case R.id.brinjal:

                Intent i12=new Intent(getContext(), detailActivity.class);
                i12.putExtra("soil-type","soil3");
                i12.putExtra("irrigation-type","irr2");

                i12.putExtra("sowing","brinjal");

                startActivity(i12);
                break;

            case R.id.cabbage:

                Intent i13=new Intent(getContext(), detailActivity.class);
                i13.putExtra("soil-type","soil4");
                i13.putExtra("irrigation-type","irr2");

                i13.putExtra("sowing","cabbage");
                startActivity(i13);
                break;

            case R.id.lemon:

                Intent i14=new Intent(getContext(), detailActivity.class);
                i14.putExtra("soil-type","soil2");
                i14.putExtra("irrigation-type","irr1");

                i14.putExtra("sowing","lemon");
                startActivity(i14);
                break;

            case R.id.carot:

                Intent i15=new Intent(getContext(), detailActivity.class);
                i15.putExtra("soil-type","soil2");
                i15.putExtra("irrigation-type","irr3");

                i15.putExtra("sowing","carot");
                startActivity(i15);
                break;

            case R.id.chillies:

                Intent i16=new Intent(getContext(), detailActivity.class);
                i16.putExtra("soil-type","soil4");
                i16.putExtra("irrigation-type","irr2");

                i16.putExtra("sowing","chillies");
                startActivity(i16);
                break;

            case R.id.spanish:

                Intent i17=new Intent(getContext(), detailActivity.class);
                i17.putExtra("soil-type","soil5");
                i17.putExtra("irrigation-type","irr1");

                i17.putExtra("sowing","spanish");
                startActivity(i17);
                break;

            case R.id.caufifower:

                Intent i18=new Intent(getContext(), detailActivity.class);
                i18.putExtra("soil-type","soil4");
                i18.putExtra("irrigation-type","irr2");

                i18.putExtra("sowing","caufifower");
                startActivity(i18);
                break;

            case R.id.capsicum:

                Intent i19=new Intent(getContext(), detailActivity.class);
                i19.putExtra("soil-type","soil1");
                i19.putExtra("irrigation-type","irr2");

                i19.putExtra("sowing","capsicum");
                startActivity(i19);
                break;

            case R.id.peas:

                Intent i20=new Intent(getContext(), detailActivity.class);
                i20.putExtra("soil-type","soil4");
                i20.putExtra("irrigation-type","irr1");

                i20.putExtra("sowing","peas");
                startActivity(i20);
                break;

            case R.id.kidneybeans:

                Intent i21=new Intent(getContext(), detailActivity.class);
                i21.putExtra("soil-type","soil2");
                i21.putExtra("irrigation-type","irr4");

                i21.putExtra("sowing","kidneybeans");
                startActivity(i21);
                break;

            case R.id.black_gram:

                Intent i22=new Intent(getContext(), detailActivity.class);
                i22.putExtra("soil-type","soil1");
                i22.putExtra("irrigation-type","irr4");

                i22.putExtra("sowing","black_gram");
                startActivity(i22);
                break;

            case R.id.green_gram:

                Intent i23=new Intent(getContext(), detailActivity.class);
                i23.putExtra("soil-type","soil2");
                i23.putExtra("irrigation-type","irr4");

                i23.putExtra("sowing","green_gram");
                startActivity(i23);
                break;

            case R.id.sugar_cane:

                Intent i24=new Intent(getContext(), detailActivity.class);
                i24.putExtra("soil-type","soil1");
                i24.putExtra("sowing","sugar_cane");

                i24.putExtra("irrigation-type","irr4");
                startActivity(i24);
                break;

            case R.id.groundnut:
                Intent i25=new Intent(getContext(), detailActivity.class);
                i25.putExtra("soil-type","soil4");
                i25.putExtra("irrigation-type","irr3");

                i25.putExtra("sowing","groundnut");
                startActivity(i25);
                break;

            case R.id.rice:
                Intent i26=new Intent(getContext(), detailActivity.class);
                i26.putExtra("soil-type","soil5");
                i26.putExtra("irrigation-type","irr1");

                i26.putExtra("sowing","rice");

                startActivity(i26);
                break;






        }



    }
}
