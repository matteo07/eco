package com.example.pier.dirittoprivato;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ResultsActivity extends AppCompatActivity {


    private final int ActivityRequestCode = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        showDomandeSbagliate(getIntent().getStringArrayListExtra("SBAGLIATE"));

        Button restarTest = (Button)findViewById(R.id.restartButton);
        restarTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int result, Intent intent) {
        showDomandeSbagliate(intent.getStringArrayListExtra("SBAGLIATE"));
        //modifiche pier
    }*/
    public void showDomandeSbagliate(ArrayList<String> sbagliate){  //per listView
        final ListView listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, sbagliate);
        listView.setAdapter(adapter);
    }
}
