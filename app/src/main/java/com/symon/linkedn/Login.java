package com.symon.linkedn;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {
    Button loginButton;
    TextView signUp;
    EditText emailInput, passwordInput;
    String email, password;

    private FirebaseAuth mAuth;
    Navigation appNavigation = new Navigation(this, Login.this);
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(this, "User already signed in", Toast.LENGTH_SHORT).show();
            appNavigation.moveToActivity(LandingPage.class);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.sign_in_button);
        signUp = findViewById(R.id.sign_up_text);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);

        loginButton.setOnClickListener( v -> {
            email = String.valueOf(emailInput.getText());
            password = String.valueOf(passwordInput.getText());

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FB_AUTH", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login.this, "Success!", Toast.LENGTH_SHORT).show();
                            appNavigation.moveToActivity(LandingPage.class);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FB_AUTH", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        signUp.setOnClickListener( v -> { appNavigation.moveToActivity(SignUp.class); });
    }
}