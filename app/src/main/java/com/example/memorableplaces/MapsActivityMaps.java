package com.example.memorableplaces;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.memorableplaces.databinding.ActivityMapsMapsBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivityMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsMapsBinding binding;
    private String placeName;
    private double latitude1 = 0.0;
    private double longitude1 = 0.0;


    ArrayList e = new ArrayList<>();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putStringArrayListExtra("ok",e);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent intent = getIntent();
        if (intent != null) {
            if(intent.getIntExtra("check",-1)==1) {
                placeName = intent.getStringExtra("placeName");
                latitude1 = intent.getDoubleExtra("latitude", 0.0);
                longitude1 = intent.getDoubleExtra("longitude", 0.0);
                LatLng location = new LatLng(latitude1, longitude1);
                MarkerOptions markerOptions = new MarkerOptions().position(location).title(placeName);
                Marker marker = googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f));
            }else{
                LatLng sydney = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        }else {
            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                addMarker(latLng);
            }
        });

    }
    private void addMarker(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (((List<?>) addresses).size() > 0) {
                Address address = addresses.get(0);
                String placeName = address.getAddressLine(0);
                e.add(latitude+","+longitude+","+placeName);

                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(placeName);

                Marker marker = mMap.addMarker(markerOptions);

                // Perform any additional operations on the marker if needed

                Toast.makeText(this, "Place: " + placeName, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}