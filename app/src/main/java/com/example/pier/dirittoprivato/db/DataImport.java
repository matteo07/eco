package com.example.pier.dirittoprivato.db;

import android.app.Activity;
import android.util.Log;

import com.example.pier.dirittoprivato.Domanda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by pier on 06/10/17.
 */

public class DataImport {
    public void importCSV(DbAdapter dbAdapter, Activity context) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("raw/csv1.csv")));
        // read file line by line
        String line = null;
        Scanner scanner = null;
        int index = 0;

        while((line = reader.readLine()) != null){

            String[] splitted = line.split("\\|");
            Log.d("Insert DB" , splitted[0] + "  !!  " + index);

            Domanda domanda = new Domanda(splitted[0], splitted[1], splitted[2], splitted[3],
                    splitted[4], Integer.parseInt(splitted[5]), Integer.parseInt(splitted[5]));

            dbAdapter.insertDomanda(domanda);
        }
        reader.close();
    }
}
