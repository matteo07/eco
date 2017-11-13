package com.example.pier.dirittoprivato;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    private final String[] CAPITOLI = {"Stato e Costituzione","Lo Stato e le sue forme","Le fonti del diritto","La Costituzione e le leggi Costituzionali","La legge ordinaria statale","Gli atti con valore o forza di legge","La legge ordinaria e statuaria","L’adattamento al diritto interanzionale e le fonti comunitarie","Il Parlamento","Il Governo"};
    private final int ActivityRequestCode = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        showDomandeSbagliate(getIntent().getStringArrayListExtra("SBAGLIATE"));
        incrementDoneQuiz();
        Button restarTest = (Button)findViewById(R.id.restartButton);
        restarTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }

    private void incrementDoneQuiz(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.storico),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if(sharedPref.contains(getString(R.string.quiz_svolti))){
            int value = sharedPref.getInt(getString(R.string.quiz_svolti),0);
            editor.putInt(getString(R.string.quiz_svolti), value + 1);
            editor.apply();
            Log.d("INT SI", String.valueOf(value));
        }else{
            editor.putInt(getString(R.string.quiz_svolti), 1);
            editor.apply();
            Log.d("INT NO", "CIAO");
        }
    }


    public void showDomandeSbagliate(ArrayList<String> sbagliate){  //per listView
        final ListView listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, sbagliate);
        listView.setAdapter(adapter);
    }
}
