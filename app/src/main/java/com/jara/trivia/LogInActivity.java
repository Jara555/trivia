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

        // initialize firebase authentication instance
        mAuth = FirebaseAuth.getInstance();
    }

    /* Returns true if text in login fields */
    public boolean getEditText() {
        // get edit text
        EditText etEmail = (EditText) findViewById(R.id.email);
        EditText etPassword = (EditText) findViewById(R.id.password);

        // store email and password of user
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        // check if there was text entered
        if (email.isEmpty()) {
            Toast toast = Toast.makeText(this, "Please enter e-mail", Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        else if (password.isEmpty()) {
            Toast toast = Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        return true;
    }

    /* Creates a new account with email and password in firebase authentication database */
    public void createAccount(View view) {
        // check if text is entered in login fields
        if (getEditText()) {
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
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("create user", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(LogInActivity.this, "Failed to create account.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    /* Signs user in by firebase authentication */
    public void signIn(View view) {
        // check if text is entered in login fields
        if (getEditText()) {
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

                                // create intent and pass through to game activity
                                Intent intent = new Intent(LogInActivity.this, StartActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("sign in", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LogInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            }
                    });
        }
    }
}
