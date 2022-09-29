package com.example.hotelapp;

public class CustomerInfo {
    public String id , customerName, customerEmail;

    public CustomerInfo(String id ,String customerName, String customerEmail) {
        this.id = id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
