package com.example.memorableplaces;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_PLACES = "places";

    private List<String> getPlaceNames(List<String> places) {
        List<String> placeNames = new ArrayList<>();

        for (String place : places) {
            // Split the combined string using comma as the separator
            String[] placeParts = place.split(",");

            if (placeParts.length >= 3) {
                // Extract the place name (index 2) and add it to the list
                String placeName = placeParts[2].trim();
                placeNames.add(placeName);
            }
        }

        return placeNames;
    }


    public void addplaces() {
        try {
            arrayList = getIntent().getExtras().getStringArrayList("ok");
            List<String> placeNames = getPlaceNames(arrayList);
            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,placeNames);
            listView.setAdapter(arrayAdapter);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void places(View view) {
        Intent intent = new Intent(MainActivity.this,MapsActivityMaps.class);
        this.startActivity(intent);
    }

    public void openMapActivity(double lat, double longi, String name) {
        Intent intent = new Intent(this, MapsActivityMaps.class);
        intent.putExtra("placeName", name);
        intent.putExtra("latitude", lat);
        intent.putExtra("longitude", longi);
        intent.putExtra("check",1);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lvAddPlaces);
        addplaces();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    String[] a = arrayList.get(position).split(",");
                    openMapActivity(Double.parseDouble(a[0]),Double.parseDouble(a[1]),a[2]);
                }catch (Exception e) {}
            }
        });
    }
}