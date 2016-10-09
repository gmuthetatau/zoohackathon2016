package com.example.zachary.zoohackathon_android;


import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by zachary on 10/8/16.
 */
public class Poster
{
    protected String url;


    public Poster(String url)
    {
        this.url = url;
    }
    public boolean post(JSONObject json)
    {
        new SendData().execute(json);
        return true;
    }
    class SendData extends AsyncTask<JSONObject, Double, String>
    {
        public String doInBackground(JSONObject... jsonObjects)
        {
            String ret = "";
            for (JSONObject json : jsonObjects)
            {
                HttpURLConnection connection = null;
                try {
                    String s = json.toString();
                    ret+=(s+"\n");
                    URL database = new URL(url);
                    connection = (HttpURLConnection)database.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setInstanceFollowRedirects(false);
                    connection.setRequestProperty("charset", "utf-8");
                    connection.setRequestProperty("Content-Length", Integer.toString(s.length()));
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Accept", "application/json");
                    try(DataOutputStream wr = new DataOutputStream(connection.getOutputStream()))
                    {
                        wr.write(s.getBytes(StandardCharsets.UTF_8));
                        wr.flush();
                        wr.close();
                    }
                }
                catch (MalformedURLException e)
                {
                    Zoohackathon_Reporting.quickToast("Malformed URL");
                }
                catch (IOException e)
                {
                    Zoohackathon_Reporting.quickToast("IO Exception");
                }
                catch (ClassCastException e)
                {
                    Zoohackathon_Reporting.quickToast("Unable to cast");
                }
                catch (Exception e)
                {
                    System.err.println(e.toString());
                    e.printStackTrace();
                }
                finally
                {
                    if (connection != null)
                        connection.disconnect();
                }

            }
            return ret;
        }
        protected void onProgressUpdate(Double... doubles)
        {

        }
        protected void onPostExecute(String result)
        {
            Zoohackathon_Reporting.quickToast(result);
        }
    }
}
