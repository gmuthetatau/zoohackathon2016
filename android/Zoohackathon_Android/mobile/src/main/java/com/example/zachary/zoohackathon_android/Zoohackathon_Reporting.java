package com.example.zachary.zoohackathon_android;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Zoohackathon_Reporting extends AppCompatActivity
{
    public static Context context;
    private MyGPS gps;
    private Location mlocation;
    final static Criteria c = MyGPS.getCriteria();
    public static final String database_url = "http://gmuthetatau.org:5001/api/reports/";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Zoohackathon_Reporting.context = this;


        final JSONObject json = new JSONObject();
        try {
            json.put("report_type", "A");
            json.put("latitude", 1);
            json.put("longitude", 2);
            json.put("device_id", "java-test");
            json.put("event_time", "2016-10-07 18:00");
        }
        catch (JSONException e)
        {
            Zoohackathon_Reporting.quickToast(e.toString());
        }


        final Poster poster = new Poster(database_url);
        /*
        gps = new MyGPS(this);

        if (!gps.hasLocationPermissions()) {
            gps.requestLocationPermission();
        }
        */
        setContentView(R.layout.activity_zoohackathon__reporting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //final LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //final Looper looper = null;

        //final GPSTracker tracker = new GPSTracker(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    poster.post(json);
                    //lm.requestSingleUpdate(c, getLocationListener(), looper);
                    //String s = tracker.getLocation().toString();
                    //Zoohackathon_Reporting.quickToast(s);
                }
                catch (SecurityException e)
                {
                    System.err.println(e.toString());
                    //gps.locationSettings();
                    Zoohackathon_Reporting.quickToast(e.toString());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_zoohackathon__reporting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static void quickToast(String s)
    {
        Toast.makeText(Zoohackathon_Reporting.context, s, Toast.LENGTH_SHORT).show();
    }
    protected void onStart()
    {
        //gps.start();
        super.onStart();
    }
    protected void onStop()
    {
        //gps.stop();
        super.onStop();
    }
    private LocationListener getLocationListener()
    {
        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mlocation = location;
                Log.d("Location Changes", location.toString());
                Zoohackathon_Reporting.quickToast(location.toString());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("Status Changed", String.valueOf(status));
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("Provider Enabled", provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Provider Disabled", provider);
            }
        };
        return locationListener;
    }

}
