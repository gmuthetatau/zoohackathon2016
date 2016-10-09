package org.gmuthetatau.wildface;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by zachary on 10/9/16.
 */
public class SendToPhone
{
    private GoogleApiClient googleapi;
    private Context context;
    private static int count = 0;
    public SendToPhone(Context c)
    {
        this.context = c;
        final GoogleApiClient googleapi = new GoogleApiClient.Builder(this.context)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks()
                {
                    @Override
                    public void onConnected(Bundle bundle)
                    {
                        quickToast(context, "onConnected: " + bundle.toString());
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
    public void sendData(PutDataMapRequest pdmr)
    {
        PutDataRequest putDataReq = pdmr.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(googleapi, putDataReq);
    }
    public PutDataMapRequest getDataMap(String path)
    {
        path = path+SendToPhone.count;
        PutDataMapRequest pdmr = PutDataMapRequest.create(path);
        pdmr.setUrgent();
        return pdmr;
    }
    public static void quickToast(Context c, String s)
    {
        Toast.makeText(c,s,Toast.LENGTH_SHORT).show();
    }

}
