package com.example.myfarm.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myfarm.modals.Post;
import com.example.myfarm.modals.Profile;
import com.example.myfarm.R;
import com.example.myfarm.activities.commentActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.Viewholder> {

    private Context mContext;
    private List<Post> mPosts;
    private FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, List<Post> mPosts) {
        this.mContext = mContext;
        this.mPosts = mPosts;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false);
        return new PostAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Post post = mPosts.get(position);

        Glide.with(mContext).load(post.getImageurl()).into(holder.postImage);
        if (post.getDescription().length() == 0) {
            holder.about.setVisibility(View.GONE);
        } else {
            holder.about.setVisibility(View.VISIBLE);
            holder.about.setText(post.getDescription().toString());
        }

        isLiked(post.getPostid(),holder.Like);
     noOfLikes(post.getPostid(),holder.noOfLikes);
        holder.Like.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {

          if(holder.Like.getTag().equals("Like")) {

            FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostid()).child(firebaseUser.getUid()).setValue(true);

          }else{

              FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostid()).child(firebaseUser.getUid()).removeValue();


          }



      }
  });


 holder.comments.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         Intent i=new Intent(mContext, commentActivity.class);
         i.putExtra("postId", post.getPostid());
         i.putExtra("authorId", post.getPublisher());
         mContext.startActivity(i);
     }
 });



        holder.dateSet.setText(post.getDate().toString());

        holder.timeSet.setText(post.getTime().toString());


        FirebaseDatabase.getInstance().getReference().child("users").child(post.getPublisher().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile user = dataSnapshot.getValue(Profile.class);
                Glide.with(mContext).load(user.getDownload()).apply(RequestOptions.circleCropTransform()).into(holder.imageProfile);

                holder.userName.setText(user.getName().toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder {

        public ImageView imageProfile;
        public TextView userName;
        public ImageView postImage;
        public TextView about;
        public TextView dateSet;
        public TextView timeSet;
        public ImageView Like;
     public  TextView noOfLikes;
     public  ImageView comments;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageProfile = itemView.findViewById(R.id.profile);
            userName = itemView.findViewById(R.id.username);
            postImage = itemView.findViewById(R.id.post_image);
            about = itemView.findViewById(R.id.text);
            dateSet = itemView.findViewById(R.id.date);
            timeSet = itemView.findViewById(R.id.time);
            Like = itemView.findViewById(R.id.like);
            noOfLikes=itemView.findViewById(R.id.one);
        comments=itemView.findViewById(R.id.comment);
        }
    }


    private  void isLiked(String postId,ImageView imageView){
        FirebaseDatabase.getInstance().getReference().child("Likes").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(firebaseUser.getUid()  ).exists()  ){
                    imageView.setColorFilter(Color.RED);
                    imageView.setTag("Liked");
                }else {
                    imageView.setColorFilter(Color.DKGRAY);
                    imageView.setTag("Like");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void noOfLikes(String postId,TextView textView){
FirebaseDatabase.getInstance().getReference().child("Likes").child(postId).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        textView.setText(snapshot.getChildrenCount()+"" );
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});



    }


}
