package com.example.myfarm.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfarm.R;
import com.example.myfarm.database.locDbHelper;

import java.util.ArrayList;

public class SaveAdapter extends RecyclerView.Adapter<SaveAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList loc_id, loc_title,loc_image,loc_accuracy;

    public SaveAdapter(Activity activity, Context context, ArrayList loc_id, ArrayList loc_title,  ArrayList loc_image, ArrayList loc_accuracy){
        this.activity = activity;
        this.context = context;
        this.loc_id = loc_id;
        this.loc_title = loc_title;
        this.loc_image=loc_image;
        this.loc_accuracy=loc_accuracy;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_save, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


        Glide.with(context).load( loc_image.get(position)  ).into(holder.photo);
    holder.photo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent i=new Intent(context,saveDetail.class);
          i.putExtra("imageurl", String.valueOf(loc_image.get(position)));
          i.putExtra("title", String.valueOf(loc_title.get(position)));
          i.putExtra("confidence", String.valueOf(loc_accuracy.get(position)));
            activity.startActivity(i);

        }
    });

         holder.delete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 confirmDialog(position);
             }
         });

          }

    private void confirmDialog(int position) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete ?");
        builder.setMessage("Are you sure you want to delete ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                locDbHelper myDB = new locDbHelper(context);
                myDB.deleteOneRow(String.valueOf(loc_id.get(position)));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();

    }


    @Override
    public int getItemCount() {
        return loc_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  loc_title_txt;
       ImageView delete;
        ImageView photo;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
           photo = itemView.findViewById(R.id.i1);
delete=itemView.findViewById(R.id.dele);

        }

    }




}