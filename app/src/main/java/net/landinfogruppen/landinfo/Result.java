package net.landinfogruppen.landinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.ArrayList;


public class Result extends Activity {

    private ArrayAdapter<String> landDataAdapter;
    private String landName;
    public final static String EXTRA_LANDSRC = "net.landinfogruppen.landinfo.MESSAGE";
    public final static String EXTRA_LANDDATA = "net.landinfogruppen.landingo.LANDDATA";
    private String landDataJsonStr = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String landSrc = intent.getStringExtra(MainActivity.EXTRA_LANDSRC);

        // instanserer ArrayAdapter slik at resultat fra GetLandData kan legges inn
        landDataAdapter = new ArrayAdapter<String>(this, R.layout.listview, new ArrayList<String>());
        final ListView listView = (ListView)findViewById(R.id.land_list);
        listView.setAdapter(landDataAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Result.this,ResultDetail.class);
                String selectedFromList = (String) listView.getItemAtPosition(position);
                intent.putExtra(EXTRA_LANDSRC, selectedFromList);
                intent.putExtra(EXTRA_LANDDATA, landDataJsonStr);
                startActivity(intent);

            }
        });

        // starter AsyncTask for henting av data
        GetLandData getLandData = new GetLandData();
        getLandData.execute(landSrc);
    }



    public class GetLandData extends AsyncTask<String, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(String... params) {

            String landSrc = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            JSONArray landArray = null;
            try {
                URL url;
                if (landSrc.isEmpty()) {
                    url = new URL("http://restcountries.eu/rest/v1/all");
                } else {
                    url = new URL("http://restcountries.eu/rest/v1/name/"+landSrc);
                }


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

                 landArray = new JSONArray(landDataJsonStr);
                //JSONObject jsonObject = new JSONObject(landDataJsonStr);
                //landArray = jsonObject.getJSONArray("list");

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
                Log.d("landArray.length: ",Integer.toString(landArray.length()));
                String[] resultStr = new String[landArray.length()];
                for (int i = 0; i<landArray.length();i++){
                    try {
                        JSONObject land = landArray.getJSONObject(i);
                         landName = land.getString("name");
                        resultStr[i] = landName;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (resultStr.length != 0){
                    //landDataAdapter.clear();
                    for ( String landStr : resultStr){
                        landDataAdapter.add(landStr);
                    }
                }

            }
        }

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


}

