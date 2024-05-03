package com.symon.linkedn;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    private List<User> users;

    StorageReference firebaseStorageReference;

    public UsersAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        String imageName = user.getUserId() + ".jpg";
        String downloadUriString;

        firebaseStorageReference = FirebaseStorage.getInstance().getReference();
        Task<Uri> task = firebaseStorageReference.child("user_images/" + imageName).getDownloadUrl();
//        String mockLocation = "gs://linked-in-mock.appspot.com/user_images/1DLsVYKMrzXiVzFdxsf3XBc0qkt2.jpg";

        holder.userName.setText(user.getName());
        holder.mobileNo.setText(String.valueOf(user.getMobileNo()));
        holder.shortBio.setText(user.getShortBio());
        holder.gender.setText(user.getGender());
        holder.skills.setText(user.getSkills());
//        holder.shapeableImageView.setImageURI(imageReference.getDownloadUrl().getResult());
        Glide.with(holder.itemView.getContext())
                .load(user.getUserImage())
                .into(holder.shapeableImageView);


        holder.userName.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), popupWindow.class);
            intent.putExtra("email", user.getEmail());
            intent.putExtra("phone", user.getMobileNo().toString());
            Log.d("MOBILE", user.getMobileNo().toString());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userName, mobileNo, gender, shortBio, skills;
        ShapeableImageView shapeableImageView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.username);
            mobileNo = itemView.findViewById(R.id.phoneNumber);
            gender = itemView.findViewById(R.id.gender);
            shortBio = itemView.findViewById(R.id.short_bio);
            shapeableImageView = itemView.findViewById(R.id.user_card_image);
            skills = itemView.findViewById(R.id.skills_tab);
        }
    }
}
