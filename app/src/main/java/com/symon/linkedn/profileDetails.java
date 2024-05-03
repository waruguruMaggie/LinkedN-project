package com.symon.linkedn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class profileDetails extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    Button updateDetailsButton;
    ImageView shapeableImageView;
    TextView nameText;
    ProgressBar progressBar;
    EditText phoneNumber, shortBio, skill;
    String name, email, password, gender, userId;
    String skills, bio,imageName;
    Uri imageUri;
    Long mobile;
    Navigation appNavigation;
    User newUser = new User();
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    StorageReference firebaseStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getColor(R.color.light_blue)));

        appNavigation = new Navigation(this, profileDetails.this);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        firebaseStorageReference = firebaseStorage.getReference();

        floatingActionButton = findViewById(R.id.floatingActionButton);
        updateDetailsButton = findViewById(R.id.update_details_button);
        nameText = findViewById(R.id.name_text_view);
        progressBar = findViewById(R.id.progress_bar);
        shapeableImageView = findViewById(R.id.shapeableImageView);
        phoneNumber = findViewById(R.id.phone_number);
        shortBio = findViewById(R.id.short_bio_field);
        skill = findViewById(R.id.skill_tab);

        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        gender = intent.getStringExtra("gender");
        password = intent.getStringExtra("password");

        nameText.setText(name);

        floatingActionButton.setOnClickListener(
                v -> ImagePicker.with(this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start()
        );

        updateDetailsButton.setOnClickListener(v -> {

            bio = shortBio.getText().toString().trim();
            mobile = Long.valueOf(phoneNumber.getText().toString().trim());
            skills = String.valueOf(skill.getText()).trim();

            appNavigation.sessionStart(updateDetailsButton, progressBar);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                       if (task.isSuccessful()){
                           Log.d("FB_AUTH", "CreateUserWithEmail: success");
                           firebaseUser = mAuth.getCurrentUser();
                           assert firebaseUser != null;
                           userId = firebaseUser.getUid();
                           uploadImageAndInsertUserData();
                       } else {
                           Log.w("FB_AUTH", "CreateUserWithEmail: failure", task.getException());
                           appNavigation.sessionComplete(updateDetailsButton, progressBar);
                           Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                       }
                    });
        });
    }

    public void insertUserDataWithNoImage(){
        if (firebaseUser != null) {
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setUserId(userId);
            newUser.setMobileNo(mobile);
            newUser.setSkills(skills);
            newUser.setGender(gender);
            newUser.setShortBio(bio);
            newUser.setUserImage("NULL");
            firebaseFirestore.collection("users").document(email).set(newUser);
            Toast.makeText(this, "Details entered successfully!", Toast.LENGTH_SHORT).show();
            appNavigation.moveToActivity(LandingPage.class);
        } else {
            Toast.makeText(this, "Unable to create user!", Toast.LENGTH_SHORT).show();
            appNavigation.sessionComplete(updateDetailsButton, progressBar);
            appNavigation.moveToLogin();
        }
    }
    public void insertUserData(String imageUrl){
        if (firebaseUser != null) {
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setSkills(skills);
            newUser.setUserId(userId);
            newUser.setGender(gender);
            newUser.setShortBio(bio);
            newUser.setMobileNo(mobile);
            newUser.setUserImage(imageUrl);
            firebaseFirestore.collection("users").document(email).set(newUser);
            Toast.makeText(this, "Use with image created", Toast.LENGTH_SHORT).show();
            appNavigation.moveToActivity(LandingPage.class);
        } else {
            Toast.makeText(this, "User with image not created", Toast.LENGTH_SHORT).show();
            appNavigation.sessionComplete(updateDetailsButton, progressBar);
            appNavigation.moveToLogin();
        }
    }

    public void uploadImageAndInsertUserData(){
        if (imageUri != null){
            imageName = userId + ".jpg";
            firebaseStorageReference.child("user_images/" + imageName)
                    .putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        newUser.setUserImage(imageName);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        appNavigation.sessionComplete(updateDetailsButton, progressBar);
                        appNavigation.moveToLogin();
                    }).addOnCompleteListener(
                            task -> {
                                if (task.isSuccessful()){
                                    firebaseStorageReference.child("user_images/" + imageName).getDownloadUrl().addOnCompleteListener(
                                            task1 -> {
                                                if (task1.isSuccessful()){
                                                    String imageUrl = task1.getResult().toString();
                                                    insertUserData(imageUrl);
                                                }
                                            }
                                    );
                                }
                            }
                    );
        } else {
            insertUserDataWithNoImage();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        assert data != null;
        imageUri = data.getData();
        shapeableImageView.setImageURI(imageUri);
    }
}