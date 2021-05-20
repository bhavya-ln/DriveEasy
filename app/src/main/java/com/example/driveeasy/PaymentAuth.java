package com.example.driveeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.biometric.BiometricPrompt;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class PaymentAuth extends AppCompatActivity {
    private Button auth;
    private Button notification;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_auth);

        auth=findViewById(R.id.auth);
        notification=findViewById(R.id.notification);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel= new NotificationChannel("PayMent","DriveEasy notifications",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

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

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message="Notification Message";
                NotificationCompat.Builder builder= new NotificationCompat.Builder(
                        PaymentAuth.this, "PayMent"
                );
                builder.setSmallIcon(R.drawable.ic_message);
                builder.setContentTitle("New notification");
                builder.setContentText(message);
                builder.setAutoCancel(true);
                //Intent intent= new Intent(PaymentAuth.this,NotificationActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //intent.putExtra("message",message);
                //PendingIntent pendingIntent=PendingIntent.getActivity(PaymentAuth.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                //builder.setContentIntent(pendingIntent);

                NotificationManager notificationManager=(NotificationManager)getSystemService(
                        Context.NOTIFICATION_SERVICE
                );
                notificationManager.notify(1,builder.build());

            }
        });
    }
}