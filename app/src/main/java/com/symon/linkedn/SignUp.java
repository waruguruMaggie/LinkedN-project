package com.symon.linkedn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button signUp;
    EditText emailInput, passwordInput, nameInput;
    String name, gender, email, password;
//    Integer mobile = 071233;
//    String shortBio = "Test short bio";
//    private FirebaseAuth mAuth;
//    private FirebaseFirestore db;
//    User newUser;

//    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//        db = FirebaseFirestore.getInstance();

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        nameInput = findViewById(R.id.sign_up_name);
        signUp = findViewById(R.id.sign_up_button);
        emailInput = findViewById(R.id.sign_up_email_input);
        passwordInput = findViewById(R.id.password_1);

        signUp.setOnClickListener( v -> {
            name = String.valueOf(nameInput.getText()).trim();
            email = String.valueOf(emailInput.getText()).trim();
            password = String.valueOf(passwordInput.getText()).trim();

            Intent intent = new Intent(this, profileDetails.class);
            intent.putExtra("name", name);
            intent.putExtra("password", password);
            intent.putExtra("email", email);
            intent.putExtra("gender", gender);
            startActivity(intent);

            /*mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("FB_AUTH", "createUserWithEmail:success");
                        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        firebaseUser = mAuth.getCurrentUser();
                        insertUserData();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("FB_AUTH", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignUp.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            );*/
        });
    }
   /* public void insertUserData(){
        if (firebaseUser != null) {
            User newUser = new User(name, email,  firebaseUser.getUid().toString(), mobile, "gender", shortBio);
//            databaseReference.child(userId).setValue(newUser);

            db.collection("users").document(email).set(newUser);
            Toast.makeText(this, "Details enter successfully!", Toast.LENGTH_SHORT).show();
            appNavigation.moveToActivity(LandingPage.class);
        }
        else {
            Toast.makeText(this, "Unable to create user profile", Toast.LENGTH_SHORT).show();
            appNavigation.moveToActivity(Login.class);
        }
    }*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gender = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}