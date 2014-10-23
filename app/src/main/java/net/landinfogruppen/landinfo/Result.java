package net.landinfogruppen.landinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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


public class Result extends Activity {

    private ArrayAdapter<String> landDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String landSrc = intent.getStringExtra(MainActivity.EXTRA_LANDSRC);
        GetLandData getLandData = new GetLandData();
        getLandData.execute(landSrc);

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(landDataAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class GetLandData extends AsyncTask<String, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(String... params) {

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

        @Override
        protected void onPostExecute(JSONArray landArray) {
            //super.onPostExecute(landArray);
            if (landArray != null){
                String[] resultStr = new String[landArray.length()-1];

                for (int i = 0; i<landArray.length();i++){
                    try {
                        JSONObject land = landArray.getJSONObject(i);
                        String landName = land.getString("name");
                        resultStr[i] = landName;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (resultStr != null){
                    landDataAdapter.clear();
                    for ( String landStr : resultStr){
                        landDataAdapter.add(landStr);
                    }
                }

            }
        }

    }

}
