package com.example.pier.dirittoprivato.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.pier.dirittoprivato.Domanda;

import java.util.ArrayList;

/**
 * Created by pier on 22/09/17.
 */

public class DbAdapter {
    // the tag used in LogCat messages
    private static String TAG = "DBAdapter";
    private static DbAdapter sInstance; //singleton instance

    // ADAPTER STATE
    private SQLiteDatabase db; // reference to the DB
    private DbHelper dbHelper; // reference to the OpenHelper

    private DbAdapter(Context context) {
        this.dbHelper = DbHelper.getInstance(context);
    }

    public static synchronized DbAdapter getInstance(Context context) {  // ritorna un riferimento all'oggetto
        if (sInstance == null)
            sInstance = new DbAdapter(context.getApplicationContext());
        return sInstance;
    }

    public DbAdapter open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
            throw e;
        }
        return this;
    }
    public void close() {
        db.close();
    }

    public void insertDomanda(Domanda domanda) {
        db.insert(DbContract.DomandaItem.TABLE_NAME,null, domanda.asContentValues());
    }

    public ArrayList<Domanda> selectDomandeFromCap(int cap, String limit){
        ArrayList<Domanda> result = new ArrayList<Domanda>();

        String whereClause = DbContract.DomandaItem.COLUMN_NAME_CAPITOLO + " = " + cap;
        String order = "RANDOM()";
        Cursor cursor = db.query(DbContract.DomandaItem.TABLE_NAME, null, whereClause, null, null, null, order, limit );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            result.add(new Domanda(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getInt(6)));
            cursor.moveToNext();
        }
        return result;
    }

    public void incrementError(int capitolo){
        String updateQuery = "update " + DbContract.ErroriItem.TABLE_NAME
                + " set " + DbContract.ErroriItem.COLUMN_NAME_ERRORI + " = "
                + DbContract.ErroriItem.COLUMN_NAME_ERRORI + " + 1 where "
                + DbContract.ErroriItem.COLUMN_NAME_CAPITOLO + " = " + capitolo;
        Log.v("Database","Aggiornato numero errori in capitolo " + capitolo);
        db.execSQL(updateQuery);
    }


    public void clearDomande() {
        Log.v("Database","Rimosse domande");
        db.execSQL("delete from " + DbContract.DomandaItem.TABLE_NAME);
    }
}
