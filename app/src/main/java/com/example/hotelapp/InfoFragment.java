package com.example.hotelapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class InfoFragment extends Fragment {
View view;
private Button btnLogout , btnOrderHistory;
ImageView ivImage;
TextView tvName;
GoogleSignInClient googleSignInClient;
FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_info, container, false);
        btnLogout = view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(logout);

        ivImage = view.findViewById(R.id.iv_image);
        tvName = view.findViewById(R.id.tv_name);
        btnOrderHistory = view.findViewById(R.id.btnOrderHistory);

        btnOrderHistory.setOnClickListener(openOrderHistory);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        // Initialize firebase user

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        // Check condition

        if(firebaseUser != null) {

            Glide.with(getActivity())
                    .load(firebaseUser.getPhotoUrl())
                    .into(ivImage);

            // Set name on textview
            tvName.setText(firebaseUser.getDisplayName());



        }

        // initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(getActivity(),
                GoogleSignInOptions.DEFAULT_SIGN_IN);



        return view;

    }

    View.OnClickListener logout = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          // Implementing signout functionality
            googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // Check condition
                    if(task.isSuccessful()){
                        // When task is successful
                        firebaseAuth.signOut();

                        Toast.makeText(getActivity(),"Logout Successful",Toast.LENGTH_SHORT).show();
                        // Redirect to Sign In Activity
                       startActivity(new Intent(getActivity(),
                               SignInActivity.class)
                       .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));


                    }
                }
            });
        }
    };

    View.OnClickListener openOrderHistory = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity().getApplicationContext(),OrderHistory.class);
            startActivity(intent);
        }
    };
}