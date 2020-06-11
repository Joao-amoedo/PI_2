package com.example.vprojetos.Activity;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.vprojetos.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add marcador do Brasil
        LatLng brasilia = new LatLng(-15.7938638, -47.8846697);

        mMap.addMarker(
                new MarkerOptions()
                        .position(brasilia)
                        .title("Brasília - DF")
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(brasilia, 5f));

        //add metodo de colocar evento de LongClick
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                //poderia usar esses dados para salvar no firebase para plotar depois
                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;

                Toast.makeText(MapsActivity.this, "LongClick lat: " + latitude + " long: " + longitude, Toast.LENGTH_LONG).show();

                mMap.addMarker(
                        new MarkerOptions()
                                .position(latLng)
                                .snippet("Descrição")
                                .title("Local")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_project))
                );
            }
        });


    }
}
