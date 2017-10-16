package com.example.pier.dirittoprivato;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pier.dirittoprivato.db.DataImport;
import com.example.pier.dirittoprivato.db.DbAdapter;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final int ActivityRequestCode = 1;
    DbAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAdapter = DbAdapter.getInstance(this);
        dbAdapter.open();
        dbAdapter.clearDomande();

        DataImport leggi = new DataImport();
        try {
            leggi.importCSV(dbAdapter, this);
            Log.d("IMPRORT CSV", "ENDED");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTest();
            }
        });
    }

    public void startTest() { // open TestActivity
        Intent intent = new Intent(this,TestActivity.class);
        startActivityForResult(intent, ActivityRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int result, Intent intent) {
        TextView res = (TextView) findViewById(R.id.resultText);
        res.setText("Sbagliato " + result + " domande");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
