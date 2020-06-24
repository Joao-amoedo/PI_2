package com.example.vprojetos.Activity;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.example.vprojetos.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-16.078817, -47.989307);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        final Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                try {
                    List<Address> fromLocation = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    Address address = fromLocation.get(0);
                    String pais = address.getCountryName();
                    String estado = address.getAdminArea().replace("State of", "");

                    retornaResultado(pais, estado);



                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });




    }

    private void retornaResultado(String pais, String estado) {
        Intent intent = new Intent();
        intent.putExtra("pais", pais);
        intent.putExtra("estado", estado);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void mensagem(Object s){
        Toast.makeText(this, s.toString() , Toast.LENGTH_SHORT).show();

    }
}
