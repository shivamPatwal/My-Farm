package com.example.myfarm.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myfarm.R;
import com.example.myfarm.databinding.ActivityDetailsBinding;
import com.example.myfarm.fragement.fragmentInfo;
import com.example.myfarm.fragement.fragmentIrrigation;
import com.example.myfarm.fragement.fragmentSeason;
import com.example.myfarm.fragement.fragmentSoilType;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class detailActivity extends AppCompatActivity {
    ImageView imageView;
    private ActivityDetailsBinding binding;
    private detailActivity activity;
    private viewPagerAdapter adapter;
    CollapsingToolbarLayout text;
    String picture;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_details);
        activity=this;
        imageView=findViewById(R.id.image);
        text=findViewById(R.id.toolbar);


        activity.getSupportActionBar().hide();

        Intent i = getIntent();
       picture = i.getStringExtra("sowing");



        initView();

        picture();
    }

    @SuppressLint("ResourceType")
    private void picture() {

        switch (picture) {
            case "apple":
                imageView.setImageResource(R.drawable.apple);
          text.setTitle(picture);
                break;
            case "corn":

                imageView.setImageResource(R.drawable.corn);
                text.setTitle(picture);
                break;
            case "orange":

                imageView.setImageResource(R.drawable.orange);
                text.setTitle(picture);
                break;

            case "banana":

                imageView.setImageResource(R.drawable.banana);
                text.setTitle(picture);
                break;

            case "pomegrant":


                imageView.setImageResource(R.drawable.pomegrant);
                text.setTitle(picture);
                break;

            case "papaya":

                imageView.setImageResource(R.drawable.papaya);
                text.setTitle(picture);

                break;

            case "litchi":
                text.setTitle(picture);
                imageView.setImageResource(R.drawable.litchi);
                break;

            case "coconut":

                imageView.setImageResource(R.drawable.coconut);
                text.setTitle(picture);
                break;

            case "mango":
                text.setTitle(picture);
                imageView.setImageResource(R.drawable.mango);
                break;

            case "patato":
                text.setTitle(picture);
                imageView.setImageResource(R.drawable.patato);
                break;

            case "tamato":

                text.setTitle(picture);
                imageView.setImageResource(R.drawable.tamato);
                break;

            case "bottle_gourd":
                text.setTitle(picture);

                imageView.setImageResource(R.drawable.loki);
                break;

            case "brinjal":

                text.setTitle(picture);
                imageView.setImageResource(R.drawable.brinjal);
                break;

            case "cabbage":

                text.setTitle(picture);
                imageView.setImageResource(R.drawable.cabbage);
                break;

            case "lemon":

                text.setTitle(picture);
                imageView.setImageResource(R.drawable.lemon);
                break;

            case "carot":

                text.setTitle(picture);
                imageView.setImageResource(R.drawable.carot);
                break;

            case "chillies":
                text.setTitle(picture);
                imageView.setImageResource(R.drawable.chili);
                break;

            case "spanish":

                text.setTitle(picture);
                imageView.setImageResource(R.drawable.spinach);
                break;

            case "caufifower":
                text.setTitle(picture);

                imageView.setImageResource(R.drawable.cauliflower);
                break;

            case "capsicum":
                text.setTitle(picture);
                imageView.setImageResource(R.drawable.capsicum);

                break;

            case "peas":

                text.setTitle(picture);
                imageView.setImageResource(R.drawable.peas);
                break;

            case "kidneybeans":

                text.setTitle(picture);
                imageView.setImageResource(R.drawable.kidney);
                break;

            case "black_gram":

                text.setTitle(picture);
                imageView.setImageResource(R.drawable.black);
                break;

            case "green_gram":

                text.setTitle(picture);
                imageView.setImageResource(R.drawable.green);
                break;

            case "sugar_cane":

                text.setTitle(picture);
                imageView.setImageResource(R.drawable.sugar);
                break;

            case "groundnut":
                text.setTitle(picture);
                imageView.setImageResource(R.drawable.ground);
                break;

            case "rice":
                text.setTitle(picture);
                imageView.setImageResource(R.drawable.rice);
                break;


        }


    }

    private void initView() {

        adapter=new viewPagerAdapter(activity.getSupportFragmentManager(),activity.getLifecycle());
        adapter.addFragement(new fragmentInfo(),"INFO" );

        adapter.addFragement(new fragmentSoilType(),"Soil Type" );

        adapter.addFragement(new fragmentSeason(),"Season" );

        adapter.addFragement(new fragmentIrrigation(),"Irrigation Type" );
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(1);
        new TabLayoutMediator(binding.tabLayout,binding.viewPager,
                (tab, position) -> {
                    tab.setText(adapter.fragmentTitleList.get(position));
                }).attach();

        for(int i=0;i<binding.tabLayout.getTabCount();i++){

            TextView tv = (TextView) LayoutInflater.from(activity)
                    .inflate(R.layout.custom_tab, null);

            binding.tabLayout.getTabAt(i).setCustomView(tv);
        }
    }


    class viewPagerAdapter extends FragmentStateAdapter {
        private  final  List<Fragment> fragmentList=new ArrayList<>();
        private  final  List<String> fragmentTitleList=new ArrayList<>();


        public viewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        public void addFragement(Fragment fragment,String title){
            fragmentList.add(fragment);
            fragmentTitleList.add(title);

        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }



}
