package net.landinfogruppen.landinfo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {
    public final static String EXTRA_LANDSRC = "net.landinfogruppen.landinfo.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button);

        try {
            ActionBar actionBar = getActionBar();
            actionBar.hide();
        }catch (NullPointerException i){

        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Result.class);
                EditText editText = (EditText) findViewById(R.id.editText);
                String landSrc = editText.getText().toString();
                intent.putExtra(EXTRA_LANDSRC, landSrc);
                startActivity(intent);

            }
        });
    }


}
