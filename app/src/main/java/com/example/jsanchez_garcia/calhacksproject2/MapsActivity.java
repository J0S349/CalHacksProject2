package com.example.jsanchez_garcia.calhacksproject2;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private  GoogleMap mMap;

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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);   //this are the cordinates of sydney Australia
        mMap.addMarker(new MarkerOptions().position(sydney).title("Sydney, Australia").icon(BitmapDescriptorFactory.defaultMarker(213)));    //this will set a marker on the map on Sydney Australia
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney)); //this will shift the center of the camera to the position
    }

    public void setUpMapIfNeeded(){
        if(mMap == null)
        {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        }
        if(mMap != null)
        {
            setUpMap();
        }
    }

    public void setUpMap()
    {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Marker"));
        mMap.setMyLocationEnabled(true);
    }

    public void onSearch(View view) throws IOException {
        EditText location_tf = (EditText)findViewById(R.id.TFAddress);
        List<Address> addressList = null;
        String location = location_tf.getText().toString();

        if(location != null || location != "")
        {
            Geocoder geocoder = new Geocoder(this);
            try{
                addressList = geocoder.getFromLocationName(location, 1);

            }catch (IOException s)
            {
                s.printStackTrace();
            }
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        }
    }

    public void changeType(View view)
    {
        if(mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        else
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }
}
