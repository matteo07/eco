package com.example.pier.dirittoprivato;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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
            Log.d("IMPORT CSV", "ENDED");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Button startButton30 = (Button) findViewById(R.id.startButton30);
        startButton30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTest(0);
            }
        });

        Button startButton20 = (Button)findViewById(R.id.startButton20);
        startButton20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTest(1);
            }
        });

    }

    public void startTest(int i){
        Intent intent = new Intent(this,TestActivity.class);
        intent.putExtra("quizType",i);
        startActivityForResult(intent, ActivityRequestCode);
    }

   /* @Override --> spostati in activity_results
    protected void onActivityResult(int requestCode, int result, Intent intent) {
        TextView res = (TextView) findViewById(R.id.resultText);
        showDomandeSbagliate(intent.getStringArrayListExtra("SBAGLIATE"));
        res.setText("Risposta errata alle seguenti domande");
        //modifiche pier
    }

    public void showDomandeSbagliate(ArrayList<String> sbagliate){  //per listView
        final ListView listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, sbagliate);
        listView.setAdapter(adapter);
    }
*/
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
