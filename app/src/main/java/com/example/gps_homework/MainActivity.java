package com.example.gps_homework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private Boolean isTracking = true;
    Location location; // Location
    double latitude; // Latitude
    double longitude; // Longitude
    double altitude; // Altitude

    TextView txtLat, txtLng, txtAlt;
    Button toggleBtn;

    private static final long TIME_BW_UPDATES = 1000 * 5;//1000 * 60 * 1; // 1 minute

    protected LocationManager locationManager;
    protected LocationListener locationListener;

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        altitude = location.getAltitude();

//        txtLat = (TextView) findViewById(R.id.text_latitude);
//        txtLng = (TextView) findViewById(R.id.text_longitude);
//        txtAlt = (TextView) findViewById(R.id.text_altitude);

//        Toast.makeText(this,latitude+" "+longitude+" "+altitude,1000*2);
        txtLat.setText("Latitude : "+ latitude);
        txtLng.setText("Longitude : "+ longitude);
        txtAlt.setText("Altitude : "+ altitude);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            String[] perm = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, perm, 270);

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_BW_UPDATES, 0, this);
            } catch (SecurityException e) {
                Toast.makeText(this, e.getMessage(), 1000 * 2).show();
            }
        }else{
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_BW_UPDATES, 0, this);
            } catch (SecurityException e) {
                Toast.makeText(this, e.getMessage(), 1000 * 2).show();
            }
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        txtLat = (TextView) findViewById(R.id.text_latitude);
        txtLng = (TextView) findViewById(R.id.text_longitude);
        txtAlt = (TextView) findViewById(R.id.text_altitude);

        toggleBtn = (Button) findViewById(R.id.btn_tracking_change);

        toggleBtn.setOnClickListener(new View.OnClickListener() {

            Button btn;
            public void onClick(View v) {
                btn = (Button) v;
                if (isTracking) {
                    isTracking = false;
                    btn.setText("ON");
//                    toggleBtn.setText("ON");
                    stopUsingGPS();
                } else {
                    isTracking = true;
                    btn.setText("OFF");
//                    toggleBtn.setText("OFF");
                    startUsingGPS();
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(this);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(isTracking){
            startUsingGPS();
        }else{
            // none
        }
    }

    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    public void startUsingGPS() {
        if (locationManager != null) {
            if (
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                return;
            }else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_BW_UPDATES, 0, this);
            }

        }
    }
}
