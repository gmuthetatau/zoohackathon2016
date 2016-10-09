package com.example.zachary.zoohackathon_android;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

/**
 * Created by zachary on 10/8/16.
 */

public class MyGPS
{
    private GoogleApiClient googleapi;
    private Context context;
    public static MyGPS gps;
    private static final LocationRequest request = MyGPS.createLocationRequest();
    private static boolean canAccessGPS;

    public MyGPS(Context context)
    {
        this.context = context;
        MyGPS.gps = this;
        MyGPS.canAccessGPS = false;
        init();
    }

    public boolean start()
    {
        googleConnection();
        googleapi.connect();
        return true;
    }
    public boolean stop()
    {
        if (googleapi != null)
            googleapi.disconnect();
        return false;
    }

    private void init()
    {
        googleConnection();
        locationSettings();
    }

    private void googleConnection()
    {
        if (googleapi == null)
        {
            googleapi = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(new MyConnectionCallbackListener())
                    .addOnConnectionFailedListener(new MyConnectionFailedListener())
                    .addApi(LocationServices.API)
                    .build();
        }
    }
    private class MyConnectionCallbackListener implements GoogleApiClient.ConnectionCallbacks
    {

        public void onConnectionSuspended(int x)
        {
            googleConnection();
        }

        public void onConnected(Bundle b)
        {
            Zoohackathon_Reporting.quickToast("Connected to Google API");
        }
    }
    private class MyConnectionFailedListener implements GoogleApiClient.OnConnectionFailedListener
    {
        public void onConnectionFailed(ConnectionResult res)
        {
            googleConnection();
        }
    }
    private static LocationRequest createLocationRequest()
    {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }
    public void locationSettings()
    {
        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder().addLocationRequest(MyGPS.request);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleapi, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>()
        {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult)
            {
                final Status status = locationSettingsResult.getStatus();
                final LocationSettingsStates locationSettingsStates =
                        locationSettingsResult.getLocationSettingsStates();
                switch (status.getStatusCode())
                {
                    case LocationSettingsStatusCodes.SUCCESS:
                        MyGPS.canAccessGPS = true;
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try
                        {
                            status.startResolutionForResult((Activity) (Zoohackathon_Reporting.context), 0);
                            MyGPS.canAccessGPS = true;
                        }
                        catch (IntentSender.SendIntentException e)
                        {
                            //ignore
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Zoohackathon_Reporting.quickToast("Cannot change settings");
                        break;
                }
            }
        });
    }
    public static final Criteria getCriteria()
    {
        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        c.setPowerRequirement(Criteria.POWER_LOW);
        c.setAltitudeRequired(false);
        c.setBearingRequired(false);
        c.setSpeedRequired(false);
        c.setCostAllowed(true);
        c.setHorizontalAccuracy(Criteria.ACCURACY_FINE);
        c.setVerticalAccuracy(Criteria.ACCURACY_FINE);
        return c;
    }
    public boolean hasLocationPermissions() {
        return ContextCompat.checkSelfPermission((Activity)context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestLocationPermission() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions((Activity)context,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
}
