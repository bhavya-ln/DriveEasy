package com.example.driveeasy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public  class SignUp extends AppCompatActivity {
        private static final String TAG="EmailPassword";
        private FirebaseAuth mAuth;
        FirebaseDatabase rootNode;
        DatabaseReference reference,ref;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up);
            mAuth = FirebaseAuth.getInstance();
        }
        public void onStart() {
            Button next=findViewById(R.id.next);
            TextInputLayout name=findViewById(R.id.name);
            TextInputLayout user=findViewById(R.id.user);
            TextInputLayout pass=findViewById(R.id.pass);
            TextInputLayout phone=findViewById(R.id.p);
            FloatingActionButton back = findViewById(R.id.floatingActionButton);

            super.onStart();
            //Listens to next button
            next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(name.getEditText().getText().toString().isEmpty() ||user.getEditText().getText().toString().isEmpty() ||pass.getEditText().getText().toString().isEmpty() ||phone.getEditText().getText().toString().isEmpty())
                        {
                            Toast.makeText(com.example.driveeasy.SignUp.this, "One or more fields left blank. Fill all fields to continue.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            rootNode = FirebaseDatabase.getInstance();
                            reference = rootNode.getReference("Users");
                            ref = reference.child("+91" + phone.getEditText().getText().toString());
                            UserHelperClass HelperClass = new UserHelperClass(name.getEditText().getText().toString(), user.getEditText().getText().toString(), pass.getEditText().getText().toString(), phone.getEditText().getText().toString());
                            createAccount(phone.getEditText().getText().toString(), user.getEditText().getText().toString(), pass.getEditText().getText().toString());
                            ref.setValue(HelperClass);
                        }


                    }


            });
            //Goes back to signin
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(com.example.driveeasy.SignUp.this, SignIn.class);
                    startActivity(intent);
                    finish();
                }
            });
        }


        private void createAccount(String phone,String email, String password) {
            // [START create_user_with_email]
            Button next=findViewById(R.id.next);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                Log.d(TAG, "createUserWithEmail:success");
                                next.setVisibility(View.GONE);
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                ref.child("+91"+phone).removeValue();//removes child since login failed
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(com.example.driveeasy.SignUp.this, task.getException().toString(),
                                        Toast.LENGTH_SHORT).show();
                                next.setVisibility(View.VISIBLE);
                                reload();
                            }
                        }
                    });
            // [END create_user_with_email]
        }
        private void updateUI(FirebaseUser user) {
            TextInputLayout phone=findViewById(R.id.p);
            //Signing up using phone
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+91"+phone.getEditText().getText().toString(),
                    60,
                    TimeUnit.SECONDS,
                    com.example.driveeasy.SignUp.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            //Removes user from auth if authentication fails
                            user.delete();
                            Toast.makeText(com.example.driveeasy.SignUp.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            reload();
                        }

                        @Override
                        public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                            String s = verificationID;
                            //Goes to Verify otp activity
                            Intent intent= new Intent(getApplicationContext(), VerifyOTP.class);
                            intent.putExtra("mobile",phone.getEditText().getText().toString());
                            intent.putExtra("verify",s);
                            Toast.makeText(com.example.driveeasy.SignUp.this,"Verification Code Sent",Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                        }
                    }
            );

        }
        private void reload(){
            //Reloads Screen
            Intent intent = new Intent(com.example.driveeasy.SignUp.this, com.example.driveeasy.SignUp.class);
            startActivity(intent);
            finish();
        }
    }
