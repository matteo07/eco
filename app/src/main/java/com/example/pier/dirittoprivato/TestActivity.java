package com.example.pier.dirittoprivato;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pier.dirittoprivato.db.DbAdapter;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    private ArrayList<Domanda> domande;
    private ArrayList<Domanda> sbagliate = new ArrayList<Domanda>();
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        DbAdapter dbAdapter = DbAdapter.getInstance(this);
        dbAdapter.open();
        domande = dbAdapter.selectDomandeFromCap(2);
        setLayout(domande.get(index));

        setButtonsListener();
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

    public void endGame(int i){
        if(!domande.get(index).isCorrect(i)){
            sbagliate.add(domande.get(index));
        }

        if(++index < domande.size()) {
            setLayout(domande.get(index));
        } else {
            Intent intent = new Intent();

            this.setResult(sbagliate.size(), intent);
            this.finish();
        }
    }

    public void setButtonsListener() {
        Button btA = (Button) findViewById(R.id.btA);
        btA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endGame(1);
            }
        });

        Button btB = (Button) findViewById(R.id.btB);
        btB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endGame(2);
            }
        });

        Button btC = (Button) findViewById(R.id.btC);
        btC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endGame(3);
            }
        });

        Button btD = (Button) findViewById(R.id.btD);
        btD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endGame(4);
            }
        });
    }
}
