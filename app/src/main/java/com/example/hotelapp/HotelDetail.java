package com.example.hotelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HotelDetail extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
TextView txtHotelName, txtHotelAddress, txtHotelPrice, txtShowDate , txtDisplayRoom;
ImageView imageView;
String hotelName, hotelStreet, hotelCountry, hotelPhoneNumber ,
        hotelAddress, hotelPostalCode, customerName , customerEmail, dateBooked,arrivalTime,departureTime , hotelAddressForFirebase;
Integer  hotelRoom, hotelPrice, roomBooked ;
FloatingActionButton btnSelectDate, btnTimeFrom, btnTimeTo , btnCurrentLocation;
int t1hour, t1minute , t2hour,t2minute;
TextView tvTimeFrom, tvTimeTo;
Button btnBookNow;
Spinner spnNumbers;
DatabaseReference rootNode;
DatabaseReference reference;
Button btnPhoneCall;
    private static final int REQUEST_CALL = 1;
String priceInString, roomsInString , latitude, longitude;
String userId;
FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

        txtHotelName = findViewById(R.id.txtHotelName);
        txtHotelAddress = findViewById(R.id.txtShowAddress);
       txtHotelPrice = findViewById(R.id.txt_hotel_item_price);


        btnPhoneCall = findViewById(R.id.btnCall);

        btnSelectDate = findViewById(R.id.btnSelectDate);
        txtShowDate = findViewById(R.id.txtShowDate);

        btnTimeFrom = findViewById(R.id.btnFrom);
        tvTimeFrom = findViewById(R.id.txtShowFrom);
        btnTimeTo = findViewById(R.id.btnTo);
        tvTimeTo = findViewById(R.id.txtShowTo);
        btnBookNow = findViewById(R.id.btnBook);
        spnNumbers = findViewById(R.id.spnNumber);
        txtDisplayRoom = findViewById(R.id.showRoom);

        btnCurrentLocation = findViewById(R.id.btnCurrentLocation);

        userId = firebaseUser.getUid();

        // set the items in spinner
        spnNumbers.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.totalRoomToBook, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnNumbers.setAdapter(adapter);


        Intent intent = getIntent();

        hotelName = intent.getStringExtra("hotelName");
        hotelStreet = intent.getStringExtra("streetName");
        hotelCountry = intent.getStringExtra("countryName");
        hotelPhoneNumber = intent.getStringExtra("phoneNumber");
         hotelRoom = Integer.parseInt(intent.getStringExtra("totalRoom"));
        hotelPrice = Integer.parseInt(intent.getStringExtra("price"));
        hotelPostalCode = intent.getStringExtra("postalCode");
        latitude = intent.getStringExtra("latitude").trim();
        longitude = intent.getStringExtra("longitude").trim();

    //  Toast.makeText(HotelDetail.this,"Latitude : " + latitude + ", Longitude :" + longitude,Toast.LENGTH_SHORT).show();

        hotelAddress = hotelStreet + ", \n " + hotelCountry + "," + hotelPostalCode ;
        hotelAddressForFirebase = hotelStreet  + hotelCountry + "," + hotelPostalCode ;

       btnPhoneCall.setText("Call : " + hotelPhoneNumber);

        customerName = CustomerData.customerName;
        customerEmail = CustomerData.customerEmail;

        priceInString = "$ " + Integer.parseInt(intent.getStringExtra("price"));
       // Toast.makeText(HotelDetail.this,priceInString ,Toast.LENGTH_SHORT).show();
        roomsInString = hotelRoom + " Rooms";

        txtHotelName.setText(hotelName);
        txtHotelAddress.setText(hotelAddress);


        txtHotelPrice.setText(priceInString);




//        Toast.makeText(this,customerName,Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,customerEmail,Toast.LENGTH_SHORT).show();

        btnSelectDate.setOnClickListener(showDialog);

        btnTimeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(HotelDetail.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        t1hour = hourOfDay;
                        t1minute = minute;

                        // Store hour and minute in string
                        String time = t1hour + ":" + t1minute;

                        // Initialize 24 hour time format
                        SimpleDateFormat t24Hours = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = t24Hours.parse(time);
                            // Initialize 12 hour time format
                            SimpleDateFormat f12hours = new SimpleDateFormat("HH:mm aa");

                            // set selected time in text view
                            tvTimeFrom.setText(f12hours.format(date));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                },12,0,false
                );
                // Set Transparent background
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Display the previous selected time
                timePickerDialog.updateTime(t1hour,t1minute);
                //show dialog
                timePickerDialog.show();
            }
        });

        btnTimeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(HotelDetail.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        t2hour = hourOfDay;
                        t2minute = minute;

                        // Store hour and minute in string
                        String time = t2hour + ":" + t2minute;

                        // Initialize 24 hour time format
                        SimpleDateFormat t24Hours = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = t24Hours.parse(time);
                            // Initialize 12 hour time format
                            SimpleDateFormat f12hours = new SimpleDateFormat("HH:mm aa");

                            // set selected time in text view
                            tvTimeTo.setText(f12hours.format(date));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                },12,0,false
                );
                // Set Transparent background
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Display the previous selected time
                timePickerDialog.updateTime(t2hour,t2minute);
                //show dialog
                timePickerDialog.show();
            }
        });

        btnPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });

        // On Click of Book Now Button
        btnBookNow.setOnClickListener(collectDetails);
        btnCurrentLocation.setOnClickListener(openMap);
    }

    View.OnClickListener openMap = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(HotelDetail.this,MapActivity.class);
            intent.putExtra("longitude",longitude);
            intent.putExtra("latitude",latitude);
            startActivity(intent);
        }
    };


    public void makePhoneCall()
  {
      if(hotelPhoneNumber.isEmpty()){
          Toast.makeText(HotelDetail.this,"Please Enter Number !", Toast.LENGTH_SHORT).show();
      }
      else if(hotelPhoneNumber.trim().length() <10){
          Toast.makeText(HotelDetail.this,"Phone Number should only have 10 digits !", Toast.LENGTH_SHORT).show();
      }
      {
          // ContextCompat contains other methods for functional of API 22+
          // such as checking permissions or adding multiple activity to stack
          if(ContextCompat.checkSelfPermission(HotelDetail.this,
                  Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

              // ActivityCompat helps in accessing features in Activity in a backwards compatible fashion

              ActivityCompat.requestPermissions(HotelDetail.this,
                      new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
          }
          else {

              // do the final part if permission is given
              String s = "tel:" + hotelPhoneNumber;
              // Here intent is the object of Intent of type Intent.ACTION_CALL

              Intent intent = new Intent(Intent.ACTION_CALL);

              // set data to that object with the URI parse method
              intent.setData(Uri.parse(s));

              // Start that activity now
              startActivity(intent);
          }
      }

  }



    View.OnClickListener showDialog = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

//            Toast.makeText(HotelDetail.this, new Date().getTime() + "", Toast.LENGTH_SHORT).show();

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    HotelDetail.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    setListener,year,month,day);

            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
          datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        }

    };

    private DatePickerDialog.OnDateSetListener setListener = new
            DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day){
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.YEAR,year);
                    c.set(Calendar.MONTH,month);
                    c.set(Calendar.DAY_OF_MONTH,day);
                    Integer age;

                    String format = new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
                    txtShowDate.setText(format);

                    Calendar currentDate = Calendar.getInstance();
                    if(currentDate.get(Calendar.DAY_OF_MONTH) < c.get(Calendar.DAY_OF_MONTH)){
                        age = currentDate.get(Calendar.YEAR) - c.get(Calendar.YEAR);
                        age --;
                    }else {
                        age = currentDate.get(Calendar.YEAR) - c.get(Calendar.YEAR);
                    }

                }
            };

    // When user will click on Book Now Button

    View.OnClickListener collectDetails = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(txtShowDate.getText().toString().equals("") && (tvTimeFrom.getText().toString().equals("") && tvTimeTo.getText().toString().equals(""))){
                Toast.makeText(HotelDetail.this,"Fields are required",Toast.LENGTH_SHORT).show();
                txtShowDate.setError("Fields are required");
                tvTimeFrom.setError("Fields are required");
                tvTimeTo.setError("Fields are required");
            }
            else if(txtShowDate.getText().toString().equals("") && tvTimeFrom.getText().toString().equals("")){
                Toast.makeText(HotelDetail.this,"Fields are required",Toast.LENGTH_SHORT).show();
                txtShowDate.setError("Fields are required");
                tvTimeFrom.setError("Fields are required");
            }
            else if(txtShowDate.getText().toString().equals("") && tvTimeTo.getText().toString().equals("") ){
                Toast.makeText(HotelDetail.this,"Fields are required",Toast.LENGTH_SHORT).show();
                txtShowDate.setError("Fields are required");
                tvTimeTo.setError("Fields are required");
            }
            else if (tvTimeFrom.getText().toString().equals("") && tvTimeTo.getText().toString().equals("")){
                Toast.makeText(HotelDetail.this,"Please select the interval of booking the timing",Toast.LENGTH_SHORT).show();
                tvTimeFrom.setError("Please select the timing");
                tvTimeTo.setError("Please select the timing");
            }
            else if(tvTimeFrom.getText().toString().equals("")){
                Toast.makeText(HotelDetail.this,"Please select the interval of booking the timing",Toast.LENGTH_SHORT).show();
                tvTimeFrom.setError("Please select the timing");
            }
            else if(tvTimeTo.getText().toString().equals("")){
                Toast.makeText(HotelDetail.this,"Please select the interval of booking the timing",Toast.LENGTH_SHORT).show();
                tvTimeTo.setError("Please select the timing");
            }
            else{
                try {
                    dateBooked = txtShowDate.getText().toString();
                    arrivalTime = tvTimeFrom.getText().toString();
                    departureTime = tvTimeTo.getText().toString();


                    rootNode = FirebaseDatabase.getInstance().getReference(userId);
                   reference = rootNode.child("Orders");

                    String id = reference.push().getKey();

                    DatabaseReference customerRefer = rootNode.child("Customers");

                    FirebaseInput firebaseInput = new FirebaseInput(id,hotelName,hotelAddressForFirebase,
                             dateBooked,arrivalTime, departureTime,
                            hotelPrice, roomBooked );


                    reference.push().setValue(firebaseInput).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            CustomerInfo customerInfo = new CustomerInfo(id, customerName, customerEmail);
                            customerRefer.setValue(customerInfo);

                            Intent intent = new Intent(HotelDetail.this,OrderPlaced.class);
                            startActivity(intent);
                            Toast.makeText(HotelDetail.this,"Data is Saved",Toast.LENGTH_SHORT).show();
                        }
                    });




                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String text = adapterView.getItemAtPosition(position).toString();
        txtDisplayRoom.setText(text);
        roomBooked = Integer.parseInt(text);
        Integer hotelCalculatedPrice;
        hotelCalculatedPrice = hotelPrice * roomBooked;
        txtHotelPrice.setText("$ " + hotelCalculatedPrice);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            // if the user will allow the permission
            if (grantResults.length > 0 & grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                // if the user will deny the permission
                Toast.makeText(HotelDetail.this,"Permission DENIED",Toast.LENGTH_SHORT).show();
            }
        }
    }
}