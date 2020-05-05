package com.example.mysmartcity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mysmartcity.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class mapactivity extends AppCompatActivity implements OnMapReadyCallback
{
    private int PLAY_SERVICES_CODE=9002;
    MapView mapView;
    GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapactivity);


        if (ContextCompat.checkSelfPermission(mapactivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(mapactivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(mapactivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(mapactivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        initGoogleMap();
        mapView=findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


    }

    private void initGoogleMap()
    {
        if ( isServicesOk())
        {
            Toast.makeText(this,"Ready to map",Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isServicesOk()
    {
        GoogleApiAvailability googleapi=GoogleApiAvailability.getInstance();
        int result= googleapi.isGooglePlayServicesAvailable(this);
        if (result== ConnectionResult.SUCCESS)
        {
            return true;
        }
        else if (googleapi.isUserResolvableError(result))
        {
            Dialog dialog=googleapi.getErrorDialog(this,result,PLAY_SERVICES_CODE, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface task)
                {
                    Toast.makeText(mapactivity.this, "Dialog is cancelled by user", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();
        }
        else
        {
            Toast.makeText(mapactivity.this, "Play services are required by this application", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults)
    {
        switch (requestCode)
        {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(mapactivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        Log.d("dfgh","onmapready:map is showing");
        mGoogleMap=googleMap;
        gotoLocation(29.685629,76.990547);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true); // zoom buttons in map
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true); // when you clcik on the marker it  will take to the google map applcation to  give you direction from your current loction
        mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
        //29.685629, 76.990547
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.title("title")
                //27.2038° N, 77.5011° E
                .position(new LatLng(29.654650,76.983966));
        mGoogleMap.addMarker(markerOptions);
    }
    public void gotoLocation(double lat,double lng)
    {
        LatLng latLng=new LatLng(lat,lng);
        CameraUpdate  cameraUpdate= CameraUpdateFactory.newLatLngZoom(latLng,10);
        mGoogleMap.moveCamera(cameraUpdate);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
