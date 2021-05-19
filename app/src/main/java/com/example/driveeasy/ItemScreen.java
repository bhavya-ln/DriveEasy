package com.example.driveeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ItemScreen extends AppCompatActivity {
    Context context;
    FirebaseDatabase rootNode;
    DatabaseReference reference,ref,ref1;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String[] s = {""};
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_item_screen);
        ImageView img = findViewById(R.id.imageView);
        TextInputLayout carname = findViewById(R.id.carname);
        TextInputLayout fueltypeinput = findViewById(R.id.FuelTypeInput);
        TextInputLayout seatnumberinput = findViewById(R.id.SeatNumberInput);
        TextInputLayout Numplate = findViewById(R.id.NumberPlateInput);
        TextInputLayout cost = findViewById(R.id.CostInput);
        FirebaseUser user = mAuth.getCurrentUser();
        rootNode= FirebaseDatabase.getInstance();
        reference=rootNode.getReference("Users");
        ref=reference.child(user.getPhoneNumber());

        FloatingActionButton buy = findViewById(R.id.Buy);

        ref.child("selectedCar").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                carname.getEditText().setText("MODEL: "+dataSnapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref.child("imgID").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Picasso.with(context).load(dataSnapshot.getValue(String.class)).resize(1000, 1000).into(img);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref.child("ft").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fueltypeinput.getEditText().setText("FUEL TYPE: "+dataSnapshot.getValue(String.class));
                //do what you want with the email
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref.child("noS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                seatnumberinput.getEditText().setText("NUMBER OF SEATS: "+dataSnapshot.getValue(String.class));
                //do what you want with the email
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref.child("numPlate").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Numplate.getEditText().setText("LAST FOUR DIGITS OF NUMBER PLATE: "+dataSnapshot.getValue(String.class));
                //do what you want with the email
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref.child("price").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cost.getEditText().setText("RATE PER HOUR: ₹"+dataSnapshot.getValue(String.class));
                //do what you want with the email
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.driveeasy.ItemScreen.this, Home.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
