package com.example.hotelapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class JSONParser extends AsyncTask<String,String,Void> {

    public static  List<Hotel> hotelDetails =  new ArrayList<Hotel>();
    BufferedInputStream inputStream;
    JSONArray jsonArray ;
    String result = "";
    public ProgressDialog progressDialog;
    Activity activity;
    Context context;
    Layout layout;
    String apiUrl = "https://api.jael.ee/datasets/hotels?country=singapore";
    String dataParsed = "";
    String singleParsed = "";
    double latitude,longitude;
    String street, city;
    int postalCode, price;
    int min = 1500;
    int max = 5000;
    Button btnSortName, btnSortPrice;
    ListView mListView;



    public JSONParser(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        this.progressDialog = new ProgressDialog(this.context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.dismiss();
        progressDialog.setMessage("Loading....");
        progressDialog.setOnCancelListener((dialogInterface -> {
            JSONParser.this.cancel(true);
        }));
    }



    @Override
    protected Void doInBackground(String... strings) {

        HttpsURLConnection httpsURLConnection = null;
        try{
            URL url = new URL(apiUrl);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());
            result = readStream();
//            result = result.substring(result.indexOf("{") + 1, result.lastIndexOf("}"));
//            name = result.substring(result.indexOf("name:") + 1, result.lastIndexOf(","));



        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


    private String readStream() throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try{
            while((line=bufferedReader.readLine())!= null){
                stringBuilder.append(line);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
       try {
//          Toast.makeText(context, result + "", Toast.LENGTH_SHORT).show();


           jsonArray = new JSONArray(result);
           for(int i = 0; i < jsonArray.length();i++)
           {

               JSONObject jsonObject = (JSONObject) jsonArray.get(i);
               JSONObject location = jsonObject.getJSONObject("location");
               JSONObject address = jsonObject.getJSONObject("address");


//               Toast.makeText(context,  address, Toast.LENGTH_SHORT).show();

               // Get longitude and latitude
               latitude = Double.parseDouble(location.get("latitude").toString())  ;
               longitude =  Double.parseDouble(location.get("longitude").toString());

               street = address.get("street").toString();
               city = address.get("city").toString();
               postalCode = Integer.parseInt(address.get("postalcode").toString());



              // Toast.makeText(context,  street, Toast.LENGTH_SHORT).show();


                   Hotel hotel = new Hotel();
                   hotel.setId(i + 1);

               //Generate random int value from 50 to 100

                int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);


                    hotel.setLatitude(latitude);
                    hotel.setLongitude(longitude);
                    hotel.setStreet(street);
                    hotel.setCity(city);
                    hotel.setPostalCode(postalCode);
                    hotel.setPrice(random_int);
                    hotel.setTotalRooms(Integer.parseInt(jsonObject.get("totalrooms").toString()));
                    hotel.setPhoneNumber(jsonObject.get("phone").toString());
                   hotel.setHotelName(jsonObject.get("name").toString());
                   hotel.setCountryName(jsonObject.get("country").toString());
                   hotel.setImageUrl("https://images.unsplash.com/photo-1517840901100-8179e982acb7?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8aG90ZWx8ZW58MHx8MHx8&ixlib=rb-1.2.1&w=1000&q=80");



              hotelDetails.add(hotel);





           }


            mListView = this.activity.findViewById(R.id.lv_hotelList);
           HotelAdapter hotelAdapter = new HotelAdapter(this.context,R.layout.hotel_item,hotelDetails);
           mListView.setAdapter(hotelAdapter);


       }
       catch (Exception e){
           e.printStackTrace();
       }
    }
}
