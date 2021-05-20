package com.example.driveeasy;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyViewHolder> {

    Context context;
    ArrayList<Car> cars;

    public CarAdapter(Context context, ArrayList<Car> cars) {
        this.context = context;
        this.cars = cars;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
       return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Car car = cars.get(position);
        holder.name.setText("Model: "+car.getName());
        holder.type.setText(car.getType());
        holder.ft.setText(car.getFt());
        holder.numplate.setText(car.getNumplate());
        holder.noS.setText(car.getNoS());
        holder.price.setText("Rate per Day: "+car.getPrice());
        Picasso.with(context).load(car.getImgID()).resize(260, 260).into(holder.imgID);
//        holder.imgID.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseDatabase rootNode;
//                DatabaseReference reference,ref;
//                rootNode= FirebaseDatabase.getInstance();
//                reference=rootNode.getReference("Users");
//                ref=reference.child();
//            }
//        });
        //            view = itemView;
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                }
//            });
        holder.imgID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase rootNode;
                DatabaseReference reference,ref;
                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                rootNode= FirebaseDatabase.getInstance();
                reference=rootNode.getReference("Users");
                ref=reference.child(mAuth.getCurrentUser().getPhoneNumber());
                ref.child("selectedCar").setValue(car.getName());
                ref.child("imgID").setValue(car.getImgID());
                ref.child("ft").setValue(car.getFt());
                ref.child("noS").setValue(car.getNoS());
                ref.child("numPlate").setValue(car.getNumplate());
                ref.child("price").setValue(car.getPrice());

                Log.i("W4K","Click-"+position);
                context.startActivity(new Intent(context,ItemScreen.class));
            }
        });


    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,noS,numplate,ft,type,price;
        ImageView imgID;
        public View view;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            noS = itemView.findViewById(R.id.seats);
            numplate = itemView.findViewById(R.id.numberplate);
            ft = itemView.findViewById(R.id.fuel);
            type = itemView.findViewById(R.id.type);
            price = itemView.findViewById(R.id.price);
            imgID = itemView.findViewById(R.id.imgID);
//            view = itemView;
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(com.example.driveeasy.CarAdapter.this, ItemScreen.class);
//                    startActivity(intent);
//                }
//            });

        }

    }



}


