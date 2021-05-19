package com.example.driveeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ItemScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_screen);
        TextView carname = findViewById(R.id.carname);
        TextView fueltypeinput = findViewById(R.id.FuelTypeInput);
        TextView seatnumberinput = findViewById(R.id.SeatNumberInput);
        TextView description = findViewById(R.id.descriptionInput);
        TextView cost = findViewById(R.id.CostInput);

    }
}