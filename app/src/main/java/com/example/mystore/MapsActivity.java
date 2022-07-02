package com.example.mystore;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mystore.databinding.ActivityMapsBinding;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    LatLng NowZone = new LatLng(10.7641034,106.6823031);
    LatLng GiaoTrinhStore = new LatLng(10.7624736,106.6822696);
    LatLng CauVuot = new LatLng(10.7637213,106.6820202);
    LatLng SuPham = new LatLng(10.7621297,106.6825539);

    private ArrayList<LatLng> locationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationArrayList = new ArrayList<>();
        locationArrayList.add(NowZone);
        locationArrayList.add(GiaoTrinhStore);
        locationArrayList.add(CauVuot);
        locationArrayList.add(SuPham);
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
        LatLng hcmus = new LatLng(10.844801260229822,106.56099700927736);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hcmus));

        MarkerOptions markerOptions = new MarkerOptions()
                .position(hcmus)
                .title("HCMUS")
                .draggable(true)
                .alpha(0.5f);

        Marker marker = mMap.addMarker(markerOptions);

        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);

        //List of predefined markers
        for (int i = 0; i < locationArrayList.size(); i++)
        {
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title(String.valueOf(i)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(i)));
        }

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
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
        marker.remove();
        return false;
    }
}