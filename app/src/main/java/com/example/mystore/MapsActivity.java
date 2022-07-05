package com.example.mystore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mystore.databinding.ActivityMapsBinding;
import com.example.mystore.model.StoreModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ArrayList<StoreModel> storeModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Map view of Store");
        actionBar.setDisplayHomeAsUpEnabled(true);

//        actionBar.setHomeButtonEnabled(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        storeModelList = getIntent().getParcelableArrayListExtra("StoreModelList");
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
        LatLng hcmus = new LatLng(10.7623016,106.67942);
        float zoomLevel = 15;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus,zoomLevel));
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(hcmus)
                .title("HCMUS")
                .draggable(true)
                .alpha(0.5f);

        Marker marker = mMap.addMarker(markerOptions);

        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);

        //List of predefined markers
        for (int i = 0; i < storeModelList.size(); i++)
        {
            String[] latlng =  storeModelList.get(i).getPosition().split(",");
            LatLng location = new LatLng(Double.parseDouble(latlng[0]), Double.parseDouble(latlng[1]));
            marker = mMap.addMarker(new MarkerOptions().position(location).title(String.valueOf(storeModelList.get(i).getName())));
            marker.setTag(storeModelList.get(i));
            marker.showInfoWindow();
        }

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        putOneMarkerAtLocation(latLng);
    }
    int Counter = 1;
    private void putOneMarkerAtLocation(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(String.valueOf(Counter++));
        mMap.addMarker(markerOptions);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        StoreModel storeModel = (StoreModel) marker.getTag();
        if(storeModel != null){
            Intent i = new Intent(MapsActivity.this, StoreItemsActivity.class);
            i.putExtra("StoreModel", storeModel);
            startActivityForResult(i, 1000);
        }
        else {
            marker.remove();
        }
        return false;
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == Activity.RESULT_OK){
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}

