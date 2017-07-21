package com.crimebusters.crimebuster;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by awais on 7/16/2017.
 */

public class GPSTracker implements LocationListener {
    Context context;
    Location location = null;
    public GPSTracker(Context context) {
        this.context = context;
    }





    public Location getLocation() {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isNetworkabl = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGpsEnabl = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(isNetworkabl){
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        else if (isGpsEnabl) {

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return location;


        }

        return location;

    }
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}