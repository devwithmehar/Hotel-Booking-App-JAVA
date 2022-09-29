package com.example.hotelapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends AppCompatActivity {
    SignInButton btnSignIn;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_hotel);

        btnSignIn = findViewById(R.id.btnLogin);

        //Initialize sign in option
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
        ).requestIdToken("939813189630-k9967l0slebfep79lj6jq3k38dr6i0jb.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Initiazlise sign in client
        googleSignInClient = GoogleSignIn.getClient(SignInActivity.this,googleSignInOptions);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize sign in Intent
                Intent intent = googleSignInClient.getSignInIntent();

                // start activity for result
                startActivityForResult(intent,100);
            }
        });
        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        // Check condition
        if(firebaseUser != null){
            CustomerData customerData = new CustomerData();
            customerData.setCustomerName(firebaseUser.getDisplayName());
            customerData.setCustomerEmail(firebaseUser.getEmail());

            startActivity(new Intent(SignInActivity.this,HotelList.class)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check condition
        if(requestCode == 100){
            // When request code is equal to 100
            //Intialize task
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            //Check condition
            if(signInAccountTask.isSuccessful()){
                //When google sign in successfull
                String s = " Google Sign in is successfull";
                displayToast(s);

                // Initialize sign in account
                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask
                            .getResult(ApiException.class);

                    // Check condition
                    if(googleSignInAccount != null){
                        // Initialize Auth Credentials
                        AuthCredential authCredential = GoogleAuthProvider
                                .getCredential(googleSignInAccount.getIdToken()
                                ,null);

                        // Check Credentials
                        firebaseAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // Check Condition
                                        if(task.isSuccessful()){
                                            // When task is successful
                                            FirebaseUser user = firebaseAuth.getCurrentUser();

                                            String customerName = user.getDisplayName();
                                            String customerEmail = user.getEmail();

                                            CustomerData customerData = new CustomerData();
                                            customerData.setCustomerName(customerName);
                                            customerData.setCustomerEmail(customerEmail);

                                            Intent intent = new Intent(SignInActivity.this,HotelList.class);
                                            startActivity(intent);



                                        }
                                    }
                                });

                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displayToast(String s){
        Toast.makeText(SignInActivity.this, s, Toast.LENGTH_SHORT).show();
    }
}