package com.example.hotelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HotelList extends AppCompatActivity {
    Button btnHome, btnCustomerInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);

        btnHome = findViewById(R.id.fragementHome);
        btnCustomerInfo = findViewById(R.id.fragementInfo);

        btnHome.setOnClickListener(openHome);
        btnCustomerInfo.setOnClickListener(openCustomerInfo);

        defaultFrament(new HomeFragment());
    }

    View.OnClickListener openHome = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            replaceFragment(new HomeFragment());
        }
    };


    View.OnClickListener openCustomerInfo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            replaceFragment(new InfoFragment());
        }
    };

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frameLayout,fragment);

        fragmentTransaction.commit();


    }

    private void defaultFrament(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frameLayout,fragment);

        fragmentTransaction.commit();


    }



}

