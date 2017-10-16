package com.example.pier.dirittoprivato.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by pier on 22/09/17.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "DBOpenHelper";

    private static DbHelper sInstance;

    public DbHelper(Context context) {
        super(context, DbContract.DATABASE_NAME, null, DbContract.DATABASE_VERSION);
    }

    public static synchronized DbHelper getInstance(Context context) {
        if (sInstance == null)
            sInstance = new DbHelper(context.getApplicationContext());
        return sInstance;
    }

    //SQL-statement per la creazione della tabella del database.
    public static final String SQL_CREATE_TABLE = "create table " //
            + DbContract.DomandaItem.TABLE_NAME + " (" //
            + DbContract.DomandaItem.COLUMN_NAME_DOMANDA + " text not null, " //
            + DbContract.DomandaItem.COLUMN_NAME_RX1 + " text not null, " //
            + DbContract.DomandaItem.COLUMN_NAME_RX2 + " text not null, " //
            + DbContract.DomandaItem.COLUMN_NAME_RX3 + " text not null, " //
            + DbContract.DomandaItem.COLUMN_NAME_RX4 + " text not null, " //
            + DbContract.DomandaItem.COLUMN_NAME_CORRETTA + " int not null, " //
            + DbContract.DomandaItem.COLUMN_NAME_CAPITOLO + " int not null " //
            + ");";


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE_TABLE);
            Log.v("Database","Created correctly");
        } catch (SQLException e) {
            Log.e(LOG_TAG, e.getMessage());
            throw e;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + DbContract.DomandaItem.TABLE_NAME);
        db.execSQL(SQL_CREATE_TABLE);
        Log.v("Database","Upgraded correctly");
    }
}
