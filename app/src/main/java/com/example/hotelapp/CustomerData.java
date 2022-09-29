package com.example.hotelapp;

public class CustomerData {
    public static String customerName;
    public static String customerEmail;

    public CustomerData() {
    }


    public static String getCustomerName() {
        return customerName;
    }

    public static void setCustomerName(String customerName) {
        CustomerData.customerName = customerName;
    }

    public static String getCustomerEmail() {
        return customerEmail;
    }

    public static void setCustomerEmail(String customerEmail) {
        CustomerData.customerEmail = customerEmail;
    }
}
