package com.example.driveeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.biometric.BiometricPrompt;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;

public class PaymentAuth extends AppCompatActivity {

    //Initializing
    private Button notification;
    Context context;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    FirebaseDatabase rootNode;
    DatabaseReference reference,ref;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_auth);

        //Initializing variables
        mAuth = FirebaseAuth.getInstance();
        ImageView img = findViewById(R.id.imageView);
        TextInputLayout carname = findViewById(R.id.carname);
        TextInputLayout locSet = findViewById(R.id.locSet);
        TextInputLayout date = findViewById(R.id.date);
        TextInputLayout Numplate = findViewById(R.id.NumberPlateInput);
        TextInputLayout cost = findViewById(R.id.CostInput);
        FirebaseUser user = mAuth.getCurrentUser();

        //Adding logo for notiff
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logof);

        rootNode= FirebaseDatabase.getInstance();
        reference=rootNode.getReference("Users");
        ref=reference.child(user.getPhoneNumber());
        final String[] s = new String[4];

        //listens to Order cancels button
        FloatingActionButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PaymentAuth.this,"Order Cancelled",Toast.LENGTH_LONG);
                Intent intent = new Intent(com.example.driveeasy.PaymentAuth.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        //Gets values of children from database
        ref.child("selectedCar").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                carname.getEditText().setText("MODEL: "+dataSnapshot.getValue(String.class));
                s[2]=dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref.child("imgID").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Picasso.with(context).load(dataSnapshot.getValue(String.class)).resize(600, 600).into(img);
                s[3]=dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref.child("locSet").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                locSet.getEditText().setText("Location of pickup: "+dataSnapshot.getValue(String.class));
                s[0] = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref.child("date").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                date.getEditText().setText("Date of start: "+dataSnapshot.getValue(String.class));
                s[1] = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref.child("numPlate").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Numplate.getEditText().setText("LAST FOUR DIGITS OF NUMBER PLATE: "+dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final int[] x = new int[2];
        ref.child("noD").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                x[1]= dataSnapshot.getValue(int.class);
                date.getEditText().setText(date.getEditText().getText().toString()+" \n[Valid for "+x[1]+" days from this date]");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref.child("price").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                x[0] = Integer. valueOf(s);
                cost.getEditText().setText("TOTAL BILL: ₹"+x[0]*x[1]);
                //do what you want with the email
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Notification and biometric scan settings
        notification=findViewById(R.id.notification);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel= new NotificationChannel("PayMent","DriveEasy notifications",NotificationManager.IMPORTANCE_HIGH);
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
                String message="Proceed to "+s[0]+" on "+s[1]+" with your driver's license in hand for pick up." ;
                NotificationCompat.Builder builder= new NotificationCompat.Builder(
                        PaymentAuth.this, "PayMent"
                );

                builder.setSmallIcon(R.drawable.ic_message);
                builder.setContentTitle("Order Confirmed!");
                builder.setContentText(s[2]+" booked for "+x[1]+" day(s).");
                builder.setAutoCancel(true);
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                builder.setLargeIcon(bitmap);
                builder.setDefaults(Notification.DEFAULT_ALL);
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
                NotificationManager notificationManager=(NotificationManager)getSystemService(
                        Context.NOTIFICATION_SERVICE
                );
                notificationManager.notify(1,builder.build());
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
        //Setting up title, description on auth dialog
        promptInfo= new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Verify your payment using a finger print scan")
                .setNegativeButtonText("Payment Authentication")
                .build();
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                biometricPrompt.authenticate(promptInfo);

            }
        });
    }
}