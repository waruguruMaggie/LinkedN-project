package com.symon.linkedn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class LandingPage extends AppCompatActivity {
    Navigation appNavigation = new Navigation(this, LandingPage.this);
    ShapeableImageView userCard;
    Button exit;
    private FirebaseAuth mAuth;

    RecyclerView rvUsers;

    ArrayList<User> users;
    private TextView userName;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userName = findViewById(R.id.tvUsername);
        users = new ArrayList<>();
        rvUsers = findViewById(R.id.rvUsers);

        exit = findViewById(R.id.logout_button);

        db.collection("users").get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        task.getResult().forEach(
                                queryDocumentSnapshot -> {
                                    User user = queryDocumentSnapshot.toObject(User.class);
                                    users.add(user);
                                    Log.d("USER", user.getName());
                                }
                        );
                        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                        rvUsers.setLayoutManager(layoutManager);
                        UsersAdapter usersAdapter = new UsersAdapter(users);
                        rvUsers.setAdapter(usersAdapter);
                    }else {
                        Log.d("USER", task.getException().toString());
                        userName.setText("Failed");
                    }
                }
        );
//        userCard.setOnClickListener( v -> { appNavigation.moveToActivity(popupWindow.class);});

        exit.setOnClickListener(
                v -> {
                    mAuth.signOut();
                    appNavigation.moveToLogin();
                }
        );
    }
}