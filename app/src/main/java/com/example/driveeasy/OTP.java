package com.example.driveeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


    public  class OTP extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_o_t_p);

            final EditText MobileNo=findViewById(R.id.MobileNo);
            Button getOTP=findViewById(R.id.getOTP);

            final ProgressBar progressBar=findViewById(R.id.progressBar);
            FirebaseAuth.getInstance().signOut();
            getOTP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(MobileNo.getText().toString().trim().isEmpty()){
                        Toast.makeText(com.example.driveeasy.OTP.this,"Enter Mobile", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    getOTP.setVisibility(View.INVISIBLE);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91"+MobileNo.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            com.example.driveeasy.OTP.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressBar.setVisibility(View.GONE);
                                    getOTP.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressBar.setVisibility(View.GONE);
                                    getOTP.setVisibility(View.VISIBLE);
                                    Toast.makeText(com.example.driveeasy.OTP.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    progressBar.setVisibility(View.GONE);
                                    getOTP.setVisibility(View.VISIBLE);
                                    String s = verificationID;
                                    Intent intent= new Intent(getApplicationContext(), VerifyOTP.class);
                                    intent.putExtra("mobile",MobileNo.getText().toString());
                                    intent.putExtra("verify",s);
                                    Toast.makeText(com.example.driveeasy.OTP.this,s,Toast.LENGTH_SHORT).show();
                                    startActivity(intent);

                                }
                            }
                    );


                }
            });
        }
    }
