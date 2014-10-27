package net.landinfogruppen.landinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by andborlar on 27.10.2014.
 */
public class Detail extends Activity {
    String landName;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String selectedFromList = intent.getStringExtra(Result.EXTRA_LANDSRC);

        GetDetailData getDetailData = new GetDetailData();
        getDetailData.execute(selectedFromList);
    }

    public class GetDetailData extends AsyncTask<String,Void,JSONArray>
    {

        @Override
        protected JSONArray doInBackground(String... params) {

           String selectedFromList = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String landDataJsonStr = null;
            JSONArray SpesificLandArray = null;
            try {
                URL url = new URL("http://restcountries.eu/rest/v1/name/"+selectedFromList);

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
                Log.d("json stringen from Detail", landDataJsonStr);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                SpesificLandArray = new JSONArray(landDataJsonStr);
                //JSONObject jsonObject = new JSONObject(landDataJsonStr);
                //landArray = jsonObject.getJSONArray("list");

                //    SharedPreferences prefs = .getSharedPreferences("net.landinfogruppen.landinfo", Context.MODE_PRIVATE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return SpesificLandArray;
        }
    }




}
