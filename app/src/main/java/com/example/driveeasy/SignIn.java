package com.example.driveeasy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


    public class SignIn extends AppCompatActivity {
        private static final String TAG="EmailPassword";
        private FirebaseAuth mAuth;
        private Button signup;
        private Button sign;
        private EditText user;
        private EditText pass;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_in);
            mAuth = FirebaseAuth.getInstance();

        }
        @Override
        public void onStart() {
            sign=findViewById(R.id.sign);
            signup=findViewById(R.id.signup);
            user=findViewById(R.id.user);
            pass=findViewById(R.id.pass);
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                updateUI(currentUser);
            }
            else{
            sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(user.getText().toString().isEmpty() || pass.getText().toString().isEmpty())
                    {
                        reload();
                    }
                    else {
                        signIn(user.getText().toString(), pass.getText().toString());
                    }
                }
            });
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(com.example.driveeasy.SignIn.this, SignUp.class);
                    startActivity(intent);
                    finish();
                }
            });

            }
        }

        private void signIn(String email, String password) {
            // [START sign_in_with_email]
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(com.example.driveeasy.SignIn.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                reload();

                            }
                        }
                    });
            // [END sign_in_with_email]
        }
        private void sendEmailVerification() {
            // Send verification email
            // [START send_email_verification]
            final FirebaseUser user = mAuth.getCurrentUser();
            user.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // Email sent
                        }
                    });
            // [END send_email_verification]
        }

        private void reload() {
            Intent intent = new Intent(com.example.driveeasy.SignIn.this, com.example.driveeasy.SignIn.class);
            startActivity(intent);
            finish();

        }

        private void updateUI(FirebaseUser user) {
            if(user.getPhoneNumber()==null)
            {
                Intent intent = new Intent(com.example.driveeasy.SignIn.this, OTP.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Intent intent = new Intent(com.example.driveeasy.SignIn.this, Dashboard.class);
                startActivity(intent);
                finish();
            }


        }
    }


