package org.gmuthetatau.wildface;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

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
    private Context context;
    public SendToPhone(Context c)
    {
        this.context = c;
        GoogleApiClient googleapi = new GoogleApiClient.Builder(this.context)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks()
                {
                    @Override
                    public void onConnected(Bundle bundle)
                    {
                        quickToast(context, "onConnected: " + bundle.toString());
                        //use the data layer api
                    }

                    @Override
                    public void onConnectionSuspended(int i)
                    {
                        quickToast(context, "Suspended " + i);
                    }
                }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener()
                {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult)
                    {
                        quickToast(context, "Failed to connect");
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
    public static void quickToast(Context c, String s)
    {
        Toast.makeText(c,s,Toast.LENGTH_SHORT).show();
    }

}
