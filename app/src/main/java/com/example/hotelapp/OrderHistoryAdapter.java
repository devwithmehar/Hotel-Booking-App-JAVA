package com.example.hotelapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class OrderHistoryAdapter extends ArrayAdapter<FirebaseInput> {
    private Context context;
    private int layout;

    public OrderHistoryAdapter(@NonNull Context context, int resource, @NonNull List<FirebaseInput> orderList) {
        super(context, resource, orderList);

        this.context = context;
        this.layout = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(layout , parent, false);
        }

        TextView hotelName = view.findViewById(R.id.tv_orderHotelName);
        TextView hotelAddress = view.findViewById(R.id.tv_orderHotelAddress);
        TextView totalRoomBooked = view.findViewById(R.id.tv_orderRoomBooked);
        TextView totalPrice = view.findViewById(R.id.tv_orderBillSpent);

        FirebaseInput firebaseInput = getItem(position);

         hotelName.setText(firebaseInput.getHotelName());
         hotelAddress.setText(firebaseInput.getHotelAddress());
         totalRoomBooked.setText("Room Booked : " + firebaseInput.getRoomBooked());
         totalPrice.setText("Price : " + "$ " + firebaseInput.getPrice());

        return view;
    }
}
