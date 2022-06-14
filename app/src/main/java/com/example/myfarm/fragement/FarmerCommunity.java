package com.example.myfarm.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myfarm.R;
import com.example.myfarm.activities.PostActivity;
import com.example.myfarm.adapters.PostAdapter;
import com.example.myfarm.modals.Post;
import com.example.myfarm.modals.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FarmerCommunity extends Fragment {
    private  TextView t;
    private ImageView dp;
    private RecyclerView recyclerViewPosts;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private ProgressBar mProgress;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.community_fragment, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        setImage();
       t=view.findViewById(R.id.text);
       dp=view.findViewById(R.id.profile1);
       t.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i=new Intent(getContext(), PostActivity.class);
               startActivity(i);
           }
       });

       recyclerViewPosts=view.findViewById(R.id.recyclerview2);

        mProgress = (ProgressBar) view.findViewById(R.id.loading_indicator);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerViewPosts.setHasFixedSize(true);
      recyclerViewPosts.setLayoutManager(linearLayoutManager);
      postList=new ArrayList<>();

        readPost();


        return  view;

    }

    private void readPost() {
     mProgress.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    Post post=snapshot.getValue(Post.class);

                    postList.add(post);

                }
                //postAdapter.notifyDataSetChanged();
                 mProgress.setVisibility(View.INVISIBLE);
                postAdapter=new PostAdapter(getContext(),postList);
                recyclerViewPosts.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                 mProgress.setVisibility(View.INVISIBLE);
            }
        });

    }


    void setImage(){
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        String uid=mAuth.getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile user=dataSnapshot.getValue(Profile.class);
                Glide.with(getContext()).load(user.getDownload().toString()).apply(RequestOptions.circleCropTransform()).into(dp);

                Log.d("str",user.getDownload().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}
