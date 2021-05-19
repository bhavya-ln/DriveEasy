package com.example.driveeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class PaymentAuth extends AppCompatActivity {
    private Button auth;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_auth);

        auth=findViewById(R.id.auth);

        executor= ContextCompat.getMainExecutor(this);
        biometricPrompt=new BiometricPrompt(PaymentAuth.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(PaymentAuth.this,"Authentication Error:"+errString,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //authentication succeeded then continue!
                Toast.makeText(PaymentAuth.this,"Success!",Toast.LENGTH_LONG);
                Intent intent = new Intent(PaymentAuth.this, Dashboard.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //failed authentication
                Toast.makeText(PaymentAuth.this,"Failed Authentication",Toast.LENGTH_LONG);
            }
        });
        //Setting up titile, description on auth dialog
        promptInfo= new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Verify your payment using a finger print scan")
                .setNegativeButtonText("Payment Authentication")
                .build();
        //handle auth
        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }
}