package com.example.hotelapp;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textOffset;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {
    double longitude, latitude;
    private MapView mapView;
    private MapboxMap map ;
    private PermissionsManager permissionsManager;
    private LocationManager locationManager;
    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_map);

        Intent intent = getIntent();

        longitude = Double.parseDouble(intent.getStringExtra("longitude"));
        latitude = Double.parseDouble(intent.getStringExtra("latitude"));

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);


        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                ArrayList<Feature> markedLocation = new ArrayList<>();

                Feature hotelLocation = Feature.fromGeometry(Point.fromLngLat(  longitude, latitude));
                hotelLocation.addStringProperty("HotelName", "Singapore Hotels");
                markedLocation.add(hotelLocation);


                mapboxMap.setStyle(Style.MAPBOX_STREETS,
                        new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {


                    }
                });


                mapboxMap.setMinZoomPreference(14);
                mapboxMap.setMaxZoomPreference(20);

                mapboxMap.moveCamera(CameraUpdateFactory.newLatLng(
                        new LatLng(latitude,longitude)
                ));
            }
        });




    }
}