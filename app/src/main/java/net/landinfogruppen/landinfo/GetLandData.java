package net.landinfogruppen.landinfo;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by andborlar on 23.10.2014.
 */
public class GetLandData extends AsyncTask<Void, Void, JSONArray> {



    @Override
    protected JSONArray doInBackground(Void... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        String landDataJsonStr = null;
        JSONArray landArray = null;
        try {
            URL url = new URL("http://restcountries.eu/rest/v1");

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if( inputStream == null){

                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine())!= null){
                buffer.append(line + "\n");
            }
            if ( buffer.length() == 0){
                return null;

            }
            landDataJsonStr = buffer.toString();
            Log.d("json stringen ", landDataJsonStr);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {



            JSONObject jsonObject = new JSONObject(landDataJsonStr);
             landArray = jsonObject.getJSONArray("LandArray");

        //    SharedPreferences prefs = .getSharedPreferences("net.landinfogruppen.landinfo", Context.MODE_PRIVATE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return landArray;
    }
}
