package com.like.storage;

import android.provider.BaseColumns;

public class DBContract {

    public static final String DB_NAME = "the_cook.db";
    public static final int DB_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String PRIMARY_KEY = " INTEGER PRIMARY KEY AUTOINCREMENT, ";

    public class ProvienceContract implements BaseColumns {
    	public static final String TABLE_NAME = "provience";
    	public static final String COLUMN_NAME_ID = "id";
    	public static final String COLUMN_NAME_NAME = "name";
    	public static final String COLUMN_NAME_NULLABLE = "null";
    	public static final String SQL_CREATE_ENTRIES= "CREATE TABLE " + TABLE_NAME + " (" +
		    	COLUMN_NAME_ID + " INTEGER PRIMARY KEY " + COMMA_SEP +
		    	COLUMN_NAME_NAME + TEXT_TYPE + " )";
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
    
    public class CityContract implements BaseColumns {
    	public static final String TABLE_NAME = "city";
    	public static final String COLUMN_NAME_ID = "id";
    	public static final String COLUMN_NAME_NAME = "name";
    	public static final String COLUMN_NAME_PROVIENCE_ID = "pro_id";
    	public static final String COLUMN_NAME_NULLABLE = "null";
    	public static final String SQL_CREATE_ENTRIES= "CREATE TABLE " + TABLE_NAME + " (" +
		    	COLUMN_NAME_ID + " INTEGER PRIMARY KEY " + COMMA_SEP + 
		    	COLUMN_NAME_NAME + TEXT_TYPE +COMMA_SEP +
		    	COLUMN_NAME_PROVIENCE_ID + TEXT_TYPE + " )";
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
    
    public class DistrictsContract implements BaseColumns {
    	public static final String TABLE_NAME = "districts";
    	public static final String COLUMN_NAME_ID = "id";
    	public static final String COLUMN_NAME_NAME = "name";
    	public static final String COLUMN_NAME_CITY_ID = "city_id";
    	public static final String COLUMN_NAME_NULLABLE = "null";
    	public static final String SQL_CREATE_ENTRIES= "CREATE TABLE " + TABLE_NAME + " (" +
		    	COLUMN_NAME_ID + " INTEGER PRIMARY KEY " + COMMA_SEP +
		    	COLUMN_NAME_NAME + TEXT_TYPE +COMMA_SEP +
		    	COLUMN_NAME_CITY_ID + " )";
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
    
}
