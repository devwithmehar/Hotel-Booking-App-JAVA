package com.example.hotelapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class HotelAdapter extends ArrayAdapter<Hotel> {
    private Context context;
    private int layout;


    public HotelAdapter(@NonNull Context context, int resource, @NonNull List<Hotel> hotelDetail) {
        super(context, resource, hotelDetail);

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

        TextView hotelName = view.findViewById(R.id.tv_hotelName);
        TextView countryName = view.findViewById(R.id.tv_country);
        ImageView imageView = view.findViewById(R.id.iv_hotelImage);
        TextView  price = view.findViewById(R.id.tv_price);

        Hotel hotel = getItem(position);

        hotelName.setText(hotel.getHotelName());
        countryName.setText(hotel.getCountryName());
        price.setText("$ " + hotel.getPrice());

        Glide.with(context)
                 .load(hotel.getImageUrl())
                .into(imageView);


        return view;
    }
}

