package org.gmuthetatau.wildface;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ReportingActivity extends AppCompatActivity implements
        DataApi.DataListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{

    private GoogleApiClient googleapi;
    private Poster poster;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting);

        poster = new Poster(this, "/report/");

        googleapi = new GoogleApiClient.Builder(this)
                        .addApi(Wearable.API)
                        .addConnectionCallbacks(this)
                        .build();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        googleapi.connect();
    }
    @Override
    public void onConnected(Bundle bundle)
    {
        Wearable.DataApi.addListener(googleapi, this);
    }
    @Override
    public void onDataChanged(DataEventBuffer dataEvents)
    {
        for (DataEvent event : dataEvents)
        {
            if (event.getType() == DataEvent.TYPE_CHANGED)
            {
                DataItem item = event.getDataItem();
                DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                JSONObject json = jsonFromDataMap(dataMap);
                poster.post(json);
            }
        }
    }
    public void onConnectionSuspended(int reason)
    {
        googleapi.disconnect();
    }
    public void onConnectionFailed(ConnectionResult res)
    {
        quickToast(this, "Connection Failed!");
    }
    public static void quickToast(Context c, String s)
    {
        Toast.makeText(c,s,Toast.LENGTH_SHORT).show();
    }
    private JSONObject jsonFromDataMap(DataMap map)
    {
        JSONObject json = new JSONObject();
        for (String s : map.keySet())
        {
            try {
                double d = Double.parseDouble((String)map.get(s));
                json.put(s,d);
            }
            catch(NumberFormatException e)
            {
                try
                {
                    json.put(s, (String)map.get(s));
                }
                catch (JSONException j)
                {
                    quickToast(this, "Cannot form JSON!");
                }
            }
            catch(JSONException e)
            {
                quickToast(this, "Cannot form JSON!");
            }
        }
        return json;
    }

}
