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
            next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        rootNode= FirebaseDatabase.getInstance();
                        reference=rootNode.getReference("Users");
                        ref=reference.child("+91"+phone.getEditText().getText().toString());
                        UserHelperClass HelperClass = new UserHelperClass(name.getEditText().getText().toString(),user.getEditText().getText().toString(),pass.getEditText().getText().toString(),phone.getEditText().getText().toString());
                        createAccount(name.getEditText().getText().toString(),user.getEditText().getText().toString(),pass.getEditText().getText().toString());
                        ref.setValue(HelperClass);

                    }
                });
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(com.example.driveeasy.SignUp.this, SignIn.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        private void createAccount(String name,String email, String password) {
            // [START create_user_with_email]
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                ref.child(name).removeValue();//removes child since loggin failed
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(com.example.driveeasy.SignUp.this, task.getException().toString(),
                                        Toast.LENGTH_SHORT).show();
                                reload();
                            }
                        }
                    });
            // [END create_user_with_email]
        }
        private void updateUI(FirebaseUser user) {
            TextInputLayout phone=findViewById(R.id.p);
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

                            Toast.makeText(com.example.driveeasy.SignUp.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            reload();
                        }

                        @Override
                        public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                            String s = verificationID;
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
            Intent intent = new Intent(com.example.driveeasy.SignUp.this, com.example.driveeasy.SignUp.class);
            startActivity(intent);
            finish();
        }
    }
