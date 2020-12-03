package com.example.julio.inspector;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class INFO extends AppCompatActivity {
private TextView info_PANEL1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        //CONTROLES
        info_PANEL1=(TextView)findViewById(R.id.INFO_PANEL1);
        info_PANEL1.setMovementMethod(new ScrollingMovementMethod());
        for (int n=1;n<200;n++){
            String T=String.valueOf(n);
            info_PANEL1.setText(T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+T+"\n");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
