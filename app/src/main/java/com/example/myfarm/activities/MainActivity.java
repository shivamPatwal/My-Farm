package com.example.myfarm.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.example.myfarm.BottomNavigationBehavior;
import com.example.myfarm.fragement.CropPhoto;
import com.example.myfarm.fragement.FarmerCommunity;
import com.example.myfarm.fragement.HomeFragment;
import com.example.myfarm.fragement.MyCrops;
import com.example.myfarm.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       toolbar = getSupportActionBar();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_vie);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        // load the store fragment by default

        toolbar.setTitle("MY FARM");
          toolbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

    toolbar.setCustomView(R.layout.toolbar);



        loadFragment(new HomeFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   // toolbar.setTitle("HOME");
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_home2:
                   // toolbar.setTitle("MY CROPS");
                    fragment = new MyCrops();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_home3:
                   // toolbar.setTitle("CROP PHOTO");
                    fragment = new CropPhoto();
                    loadFragment(fragment);
                     return true;

                case R.id.navigation_home4:
                  //  toolbar.setTitle("FEED");
                    fragment = new FarmerCommunity();
                    loadFragment(fragment);
                    return true;

            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return false;
    }

    }

