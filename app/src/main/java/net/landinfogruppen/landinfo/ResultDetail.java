package net.landinfogruppen.landinfo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ResultDetail extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);

        try {
            ActionBar actionBar = getActionBar();
            actionBar.hide();
        }catch (NullPointerException i){

        }

        Intent intent = getIntent();
        String selectedFromList = intent.getStringExtra(Result.EXTRA_LANDSRC);
        String landDataJsonStr = intent.getStringExtra(Result.EXTRA_LANDDATA);
        try {
            JSONArray jsonArray = new JSONArray(landDataJsonStr);
            String landName;
            String nativeName;
            String capital;
            String region;
            String subregion;
            String population;
            String area;
            String demonym;
            String callingCodes;
            String currencies;
            String topLevelDomain;

            for (int i = 0; i<jsonArray.length();i++){
                try {
                    JSONObject land = jsonArray.getJSONObject(i);
                    landName = land.getString("name");

                    if (landName.equals(selectedFromList)){

                        setTitle(landName);

                        nativeName = land.getString("nativeName");
                        demonym = land.getString("demonym");
                        capital = land.getString("capital");
                        region = land.getString("region");
                        subregion = land.getString("subregion");
                        population = land.getString("population");
                        area = land.getString("area");
                        callingCodes = land.getString("callingCodes");
                        currencies = land.getString("currencies");
                        topLevelDomain = land.getString("topLevelDomain");

                        TextView textView = (TextView) findViewById(R.id.textview_name);
                        textView.setText(landName);

                        TextView textView1 = (TextView) findViewById(R.id.textview_native_name);
                        textView1.setText(nativeName);

                        TextView textView2 = (TextView) findViewById(R.id.textview_demonym);
                        textView2.setText(demonym);

                        TextView textView3 = (TextView) findViewById(R.id.textview_capital);
                        textView3.setText(capital);

                        TextView textView4 = (TextView) findViewById(R.id.textview_region);
                        textView4.setText(region);

                        TextView textView5 = (TextView) findViewById(R.id.textview_subregion);
                        textView5.setText(subregion);

                        TextView textView6 = (TextView) findViewById(R.id.textview_population);
                        textView6.setText(population);

                        TextView textView7 = (TextView) findViewById(R.id.textview_area);
                        textView7.setText(area);

                        TextView textView8 = (TextView) findViewById(R.id.textview_calling_code);
                        textView8.setText(callingCodes);

                        TextView textView9 = (TextView) findViewById(R.id.textview_currencies);
                        textView9.setText(currencies);

                        TextView textView10 = (TextView) findViewById(R.id.textview_top_level_domain);
                        textView10.setText(topLevelDomain);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



}
