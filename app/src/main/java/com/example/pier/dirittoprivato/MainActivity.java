package com.example.pier.dirittoprivato;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final int ActivityRequestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton30 = (Button) findViewById(R.id.startButton30);
        startButton30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTest(0);
            }
        });

        Button startButton20 = (Button) findViewById(R.id.startButton20);
        startButton20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTest(1);
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        TextView quizSvolti = (TextView)findViewById(R.id.quiz_svolti);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.storico), Context.MODE_PRIVATE);
        int numQuiz = sharedPref.getInt(getString(R.string.quiz_svolti),0);
        quizSvolti.setText(getString(R.string.quiz_svolti_textView) + " " + numQuiz);
        //PER PULIRE LE PREFERENCES
        //sharedPref.edit().clear().commit();
    }

    public void startTest(int i){
        Intent intent = new Intent(this,TestActivity.class);
        intent.putExtra("quizType",i);
        startActivityForResult(intent, ActivityRequestCode);
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
        if (id == R.id.menu) {
            Intent intent = new Intent(this,ProfileActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startProfileActivity(View view) {
        Intent intent = new Intent(this,ProfileActivity.class);
        startActivity(intent);
    }
}
