package com.example.pier.dirittoprivato;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pier.dirittoprivato.db.DbAdapter;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    private ArrayList<Domanda> domande = new ArrayList<>();
    public ArrayList<String> sbagliate = new ArrayList<>();
    private int index = 0;

    DbAdapter dbAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        int quizType = getIntent().getIntExtra("quizType",0);

        dbAdapter = DbAdapter.getInstance(this);
        dbAdapter.open();

        if(quizType == 0){
            for(int i =1; i < 11; i++) {
                domande.addAll(dbAdapter.selectDomandeFromCap(i, "3"));
                setLayout(domande.get(index));
            }
        }else{
            for(int i =1; i < 11; i++) {
                domande.addAll(dbAdapter.selectDomandeFromCap(i, "1"));
                setLayout(domande.get(index));
            }
        }
        setButtonsListener();
    }

    // metodo per gestire il tasto back della softbar
    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Quiz non completato",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void setLayout(Domanda d){
        TextView domanda = (TextView) findViewById(R.id.Domanda);
        domanda.setText(d.getDomanda());

        TextView rx1 = (TextView) findViewById(R.id.rx1);
        rx1.setText("A. " + d.getRx_1());

        TextView rx2 = (TextView) findViewById(R.id.rx2);
        rx2.setText("B. " + d.getRx_2());

        TextView rx3 = (TextView) findViewById(R.id.rx3);
        rx3.setText("C. " + d.getRx_3());

        TextView rx4 = (TextView) findViewById(R.id.rx4);
        rx4.setText("D. " + d.getRx_4());
    }

    @Override
    public String toString() {
        return sbagliate.toString();
    }

    public void checkAnswer(int i){
        Domanda domanda = domande.get(index);
        if(isWrongAnswer(i)){
            sbagliate.add(domanda.getDomanda());
            dbAdapter.incrementError(domanda.getCapitolo());
        }
        if(isNotLastQuestion()) {
            setLayout(domanda);
        } else {
            Intent intent = new Intent(this,ResultsActivity.class);
            intent.putStringArrayListExtra("SBAGLIATE", sbagliate);
            //this.setResult(sbagliate.size(), intent);
            startActivity(intent);
            this.finish();
        }
    }

    private boolean isNotLastQuestion() {
        return ++index < domande.size();
    }

    private boolean isWrongAnswer(int i) {
        return !domande.get(index).isCorrect(i);
    }

    public void setButtonsListener() {
        Button btA = (Button) findViewById(R.id.btA);
        btA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(1);
            }
        });

        Button btB = (Button) findViewById(R.id.btB);
        btB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(2);
            }
        });

        Button btC = (Button) findViewById(R.id.btC);
        btC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(3);
            }
        });

        Button btD = (Button) findViewById(R.id.btD);
        btD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(4);
            }
        });
    }
}
