package com.example.pier.dirittoprivato.db;

import android.content.ContentValues;
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

    public ArrayList<String> getErroriPerCapitolo () {
        ArrayList<String> result = new ArrayList<String>();

        String order = DbContract.ErroriItem.COLUMN_NAME_ERRORI + " desc";

        Cursor cursor = db.query(DbContract.ErroriItem.TABLE_NAME, null, null, null, null, null, order, null );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            result.add("" + cursor.getInt(0));
            Log.d("ERRORI 1","" + cursor.getInt(0));
            Log.d("ERRORI 2","" + cursor.getInt(1));
            cursor.moveToNext();
        }
        return result;

    }

    public void incrementError(int capitolo){
        Log.d("Database","Aggiornato numero errori in capitolo " + capitolo);
        ContentValues cv = new ContentValues();
        cv.put(DbContract.ErroriItem.COLUMN_NAME_CAPITOLO, capitolo);
        cv.put(DbContract.ErroriItem.COLUMN_NAME_ERRORI,2);
        db.update(DbContract.ErroriItem.TABLE_NAME,cv,DbContract.ErroriItem.COLUMN_NAME_CAPITOLO + "=" + capitolo, null);
    }

   public void clearDomande() {
        Log.v("Database","Rimosse domande");
        db.execSQL("delete from " + DbContract.DomandaItem.TABLE_NAME);
    }
}
