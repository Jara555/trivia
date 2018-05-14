package com.jara.trivia;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {
    /** Manages the login page of the app:
     * Allows users to create and authenticate their account **/

    /* Declare class instances and variables */
    private FirebaseAuth mAuth;
    String email;
    String password;

    /* Implements the login layout */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // hardcoded user profile
        email = "hoi@hoi.com";
        password = "abcdef";

        // initialize firebase authentication instance
        mAuth = FirebaseAuth.getInstance();

        // create user
        createAccount();
    }

    /* Checks if user is currently signed in */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void getEditText() {
        EditText etEmail = (EditText) findViewById(R.id.email);
        EditText etPassword = (EditText) findViewById(R.id.password);

        // hardcoded user profile
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
    }

    /* Creates a new account with email and password */
    public void createAccount() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("create user", "createUserWithEmail:success");
                            Toast.makeText(LogInActivity.this, "Account created: " + email,
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("create user", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Failed to create account.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void signIn(View view) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("sign in", "signInWithEmail:success");
                            Toast.makeText(LogInActivity.this, "Authentication succeed: " + email,
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);

                            // create intent and pass through to game activity
                            Intent intent = new Intent(LogInActivity.this, GameActivity.class);
                            startActivity(intent);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("sign in", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
