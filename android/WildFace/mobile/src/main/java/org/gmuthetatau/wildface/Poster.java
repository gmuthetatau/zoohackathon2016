package org.gmuthetatau.wildface;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zachary on 10/8/16.
 */
public class Poster
{
    protected String url;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private Context context;

    public Poster(Context c, String url)
    {
        this.url = url;
        this.context = c;
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
            OkHttpClient ok = new OkHttpClient();
            StringBuilder sb = new StringBuilder();
            for (JSONObject json : jsonObjects)
            {
                RequestBody body = RequestBody.create(JSON, json.toString());
                Request req = new Request.Builder().url(url).addHeader("Accept", "application/json").post(body).build();
                Response response = null;
                try
                {
                    response = ok.newCall(req).execute();
                    System.err.println(response.toString());
                    System.err.println(response.body().string());
                }
                catch (IOException e)
                {
                    quickToast(context,e.toString());
                }
                if (response != null)
                    sb.append(response.body().toString());
            }
            return sb.toString();
        }

        protected void onProgressUpdate(Double... doubles)
        {

        }

        protected void onPostExecute(String result)
        {
            quickToast(context,result);
        }
    }
    public static void quickToast(Context c, String s)
    {
        Toast.makeText(c,s,Toast.LENGTH_SHORT).show();
    }
}

