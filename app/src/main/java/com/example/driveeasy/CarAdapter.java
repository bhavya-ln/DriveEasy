package com.example.driveeasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.name.setText(car.getName());
        holder.type.setText(car.getType());
        holder.ft.setText(car.getFt());
        holder.numplate.setText(car.getNumplate());
        holder.noS.setText(car.getNoS());
        holder.price.setText(car.getPrice());

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,noS,numplate,ft,type,price;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            noS = itemView.findViewById(R.id.seats);
            numplate = itemView.findViewById(R.id.numberplate);
            ft = itemView.findViewById(R.id.fuel);
            type = itemView.findViewById(R.id.type);
            price = itemView.findViewById(R.id.price);

        }
    }


}


