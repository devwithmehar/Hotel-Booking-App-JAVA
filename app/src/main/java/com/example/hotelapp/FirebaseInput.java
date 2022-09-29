package com.example.hotelapp;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseInput {
    String id, hotelName,hotelAddress, customerName,customerEmail, date,arrivalTime,departureTime;
    Integer  price,roomBooked;


    public FirebaseInput() {
    }

    public FirebaseInput(String id ,String hotelName, String hotelAddress,

                         String date, String arrivalTime, String departureTime,
                         Integer price, Integer roomBooked) {
        this.id = id;
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.date = date;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.price = price;
        this.roomBooked = roomBooked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getRoomBooked() {
        return roomBooked;
    }

    public void setRoomBooked(Integer roomBooked) {
        this.roomBooked = roomBooked;
    }


}
