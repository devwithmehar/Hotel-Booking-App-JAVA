package com.example.hotelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderHistory extends AppCompatActivity {

    ListView mlistView;
    String userId;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root;
    List<FirebaseInput> orderHistoryList;
    List<FirebaseInput> tempList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        mlistView = findViewById(R.id.lv_orderHistoryList);
        orderHistoryList = new ArrayList<>();

        userId = firebaseUser.getUid();
        root = db.getReference(userId).child("Orders");


    }

    @Override
    protected void onStart() {
        super.onStart();

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                 orderHistoryList.clear();

                for(DataSnapshot orderHistorySnapShot : snapshot.getChildren()){
                    FirebaseInput firebaseInput = orderHistorySnapShot.getValue(FirebaseInput.class);


                        orderHistoryList.add(firebaseInput);


                }

                Collections.reverse(orderHistoryList);

                OrderHistoryAdapter adapter = new OrderHistoryAdapter(OrderHistory.this,R.layout.order_history_items,orderHistoryList);
                mlistView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}