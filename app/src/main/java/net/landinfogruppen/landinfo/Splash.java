package net.landinfogruppen.landinfo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class Splash extends Activity {
    private final int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            ActionBar actionBar = getActionBar();
            actionBar.hide();
        }catch (NullPointerException i){

        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {


                Intent intent = new Intent(Splash.this,MainActivity.class);
                Splash.this.startActivity(intent);
                Splash.this.finish();

            }
        },SPLASH_TIME);
    }



}
