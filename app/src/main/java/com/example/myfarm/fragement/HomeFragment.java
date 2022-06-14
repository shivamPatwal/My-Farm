package com.example.myfarm.fragement;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.icu.util.LocaleData;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfarm.R;
import com.example.myfarm.activities.MainActivity;
import com.example.myfarm.adapters.SaveAdapter;
import com.example.myfarm.database.locDbHelper;
import com.example.myfarm.modals.HttpRequest;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.core.content.ContextCompat.getSystemService;

public class HomeFragment extends Fragment {
    TextView address;
    TextView temp;
    TextView sunsettime;

    TextView previouspic;
    TextView description;
    TextView presi;
    CircleImageView circleImageView;
    ImageView heal;
    String latitude;
    String longitude;
    private LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
RecyclerView recyclerView;
    locDbHelper myDB;
    ArrayList<String> loc_id, loc_title,loc_imageurl,loc_accuracy;
    SaveAdapter saveAdapter;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;





    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

  previouspic=view.findViewById(R.id.previouspic);
        address = view.findViewById(R.id.location);
        temp=view.findViewById(R.id.temp);
        sunsettime=view.findViewById(R.id.sunsettime);
        description=view.findViewById(R.id.description);
        heal=view.findViewById(R.id.heal);
        circleImageView=view.findViewById(R.id.image);
        presi=view.findViewById(R.id.pres);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }

heal.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Fragment fragment = new CropPhoto();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
       fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
});


        myDB = new locDbHelper(getContext());
        loc_id = new ArrayList<>();
        loc_title = new ArrayList<>();
        loc_imageurl = new ArrayList<>();
   loc_accuracy=new ArrayList<>();
        storeDataInArrays();


        return view;

    }

    public  void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();

            while (cursor.moveToNext()){
                loc_id.add(cursor.getString(0));
                loc_title.add(cursor.getString(1));
                loc_imageurl.add(cursor.getString(2));
                loc_accuracy.add(cursor.getString(3));
            }

   if(loc_id.size()!=0){
       previouspic.setVisibility(View.VISIBLE);
   }
        saveAdapter = new SaveAdapter(getActivity(),getContext(), loc_id, loc_title,loc_imageurl,loc_accuracy);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        saveAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(saveAdapter);

    }


    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                Log.d("lat", latitude);
                Log.d("lon", longitude);

                new weatherTask().execute();
            } else {
                Toast.makeText(getContext(), "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    class weatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String response = HttpRequest.excuteGet("https://api.weatherbit.io/v2.0/current?lat=" + latitude + "&lon=" + longitude + "&key=030314b750cc43e7b39e503dfe37150c");
            return response;
        }


        //https://api.weatherbit.io/v2.0/current?lat=30.28205698&lon=78.09516058&key=030314b750cc43e7b39e503dfe37150c
        protected void onPostExecute(String result) {
            try {


                JSONObject jsonObject1 = new JSONObject(result);
                JSONArray jsonArray = jsonObject1.getJSONArray("data");

                JSONObject jsonObject = jsonArray.getJSONObject(0);


                Calendar cal = Calendar.getInstance();
               String datefull= String.valueOf(cal.getTime());
              String month=datefull.substring(4,7);
                String date = datefull.substring(8, 11);



                address.setText(jsonObject.getString("city_name") + ", "+ date+" "+month  );
                 temp.setText(jsonObject.getString("temp")+" °C");
                sunsettime.setText(jsonObject.getString("aqi"));
                JSONObject jsonObject2=jsonObject.getJSONObject("weather");
                description.setText(jsonObject2.getString("description"));
                String pres=jsonObject.getString("precip");
                 double prescipition=0;
                if(pres.equals("null")){
                    prescipition=0;
                }else {
                    prescipition= Double.parseDouble(pres);

                }
                presi.setText(prescipition+" %");
         String link=jsonObject2.getString("icon");
                Glide.with(getContext())
                        .load("https://www.weatherbit.io/static/img/icons/"+link+".png")
                        .into(circleImageView);

            } catch (JSONException e) {
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }



}
