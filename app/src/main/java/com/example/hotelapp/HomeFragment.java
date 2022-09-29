package com.example.hotelapp;

import static com.example.hotelapp.JSONParser.*;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;
    ListView mListView;
    JSONParser jsonParser;
    Spinner spinner;
    SearchView searchView;
    HotelAdapter hotelAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_home, container, false);



        // adding the data in the list view
        mListView = view.findViewById(R.id.lv_hotelList);
        hotelAdapter = new HotelAdapter(getActivity().getApplicationContext(),R.layout.hotel_item, hotelDetails);
        mListView.setAdapter(hotelAdapter);

        // Calling the JSONParser in the HomeFragment
        jsonParser = (JSONParser) new JSONParser(getActivity(),getActivity().getApplicationContext()).execute();

        mListView.setOnItemClickListener(click_hotel);

        spinner = view.findViewById(R.id.sortingSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),R.array.sortingTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        //Binding the search bar now
        searchView = view.findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                int charLength = s.length();
                ArrayList<Hotel> tempList = new ArrayList<Hotel>();
                for (Hotel tempData : hotelDetails) {
                    if (charLength <= tempData.getHotelName().length()) {
                        if (tempData.getHotelName().toLowerCase().contains(s.toLowerCase())
                        ) {
                            tempList.add(tempData);
                        }
                    }
                }

                hotelAdapter = new HotelAdapter(getActivity().getApplicationContext(),R.layout.hotel_item, tempList);

                mListView.setAdapter(hotelAdapter);

                return true;

            }

            @Override
            public boolean onQueryTextChange(String s) {
                int charLength = s.length();
                ArrayList<Hotel> tempList = new ArrayList<Hotel>();
                for (Hotel tempData : hotelDetails) {
                    if (charLength <= tempData.getHotelName().length()) {
                        if (tempData.getHotelName().toLowerCase().contains(s.toLowerCase())
                        ) {
                            tempList.add(tempData);
                        }
                    }
                }

                hotelAdapter = new HotelAdapter(getActivity().getApplicationContext(),R.layout.hotel_item, tempList);
                mListView.setAdapter(hotelAdapter);

                return true;

            }
        });

       return view;

    }

    private void sortArrayListByName(){
        Collections.sort(hotelDetails, new Comparator<Hotel>() {
            @Override
            public int compare(Hotel name1, Hotel name2) {
                return name1.getHotelName().compareTo(name2.getHotelName());
            }
        });
        mListView.invalidate();
        jsonParser = (JSONParser) new JSONParser(getActivity(),getActivity().getApplicationContext()).execute();
    }

    private void sortArrayByPrice(){
        Collections.sort(hotelDetails, new Comparator<Hotel>() {
            @Override
            public int compare(Hotel price1, Hotel price2) {
                return price1.getPrice() - price2.getPrice();
            }
        });
        mListView.invalidate();
        jsonParser = (JSONParser) new JSONParser(getActivity(),getActivity().getApplicationContext()).execute();
    }


    AdapterView.OnItemClickListener click_hotel = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Hotel hotel = (Hotel) adapterView.getItemAtPosition(position);
//           Toast.makeText(getActivity().getApplicationContext(),hotel.getTotalRooms() + "", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getActivity().getApplicationContext(),HotelDetail.class);
            intent.putExtra("hotelName",hotel.getHotelName());
            intent.putExtra("totalRoom",hotel.getTotalRooms() + "");
            intent.putExtra("countryName",hotel.getCountryName());
            intent.putExtra("streetName",hotel.getStreet());
            intent.putExtra("phoneNumber",hotel.getPhoneNumber());
            intent.putExtra("price",hotel.getPrice() + "");
            intent.putExtra("postalCode",hotel.getPostalCode() + "");
            intent.putExtra("latitude",hotel.getLatitude() + "");
            intent.putExtra("longitude",hotel.getLongitude() + "");

//            Toast.makeText(getActivity().getApplicationContext(),hotel.getPrice() + ": Price",Toast.LENGTH_SHORT).show();

            startActivity(intent);
        }
    };


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String text = adapterView.getItemAtPosition(position).toString();

        switch (text){
            case "Name":
                sortArrayListByName();
                break;
            case "Price":
                sortArrayByPrice();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}