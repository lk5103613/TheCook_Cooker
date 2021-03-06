package com.like.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper mDBHelper;

    public static DBHelper getInstance(Context context) {
        if(mDBHelper == null)
            mDBHelper = new DBHelper(context);
        return mDBHelper;
    }

    private DBHelper(Context context) {
        super(context, DBContract.DB_NAME, null, DBContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContract.ProvienceContract.SQL_CREATE_ENTRIES);
        db.execSQL(DBContract.CityContract.SQL_CREATE_ENTRIES);
        db.execSQL(DBContract.DistrictsContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
