package com.example.zachary.zoohackathon_android;

import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by zachary on 10/9/16.
 */
public class SendToPhone
{
    private GoogleApiClient googleapi;
    public SendToPhone()
    {
        GoogleApiClient googleapi = new GoogleApiClient.Builder(ReportingWatchFace.context)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks()
                {
                    @Override
                    public void onConnected(Bundle bundle)
                    {
                        ReportingWatchFace.quickToast("onConnected: " + bundle.toString());
                        //use the data layer api
                    }

                    @Override
                    public void onConnectionSuspended(int i)
                    {
                        ReportingWatchFace.quickToast("Suspended " + i);
                    }
                }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener()
                {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult)
                    {
                        ReportingWatchFace.quickToast("Failed to connect");
                    }
                }).addApi(Wearable.API).build();
    }
    public int sendData(byte[] arr, String path)
    {
        PutDataRequest pdr = PutDataRequest.create(path);
        if (pdr == null)
            return -1;
        pdr.setData(arr);
        Wearable.DataApi.putDataItem(googleapi, pdr);
        return 1;
    }

}
