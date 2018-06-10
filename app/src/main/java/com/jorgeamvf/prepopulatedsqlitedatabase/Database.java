package com.jorgeamvf.prepopulatedsqlitedatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Database {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SQLite.db";
    private static final String DATABASE_TABLE = "List";
    public static final String DATABASE_ID = "_id";
    public static final String DATABASE_GROUP_1 = "_id";
    public static final String DATABASE_GROUP_2 = "language";
    public static final String DATABASE_CHILD_1 = "quantity";
    public static final String DATABASE_CHILD_2 = "percentage";
    private final Context mContext;
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDB;

    public Database(Context context) {
        mContext = context;
    }

    public void open() {
        mDatabaseHelper = new DatabaseHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDatabaseHelper.getWritableDatabase();
    }


    public void close() {
        if (mDatabaseHelper != null) mDatabaseHelper.close();
    }

    public Cursor getDatabase() {
        return mDB.query(DATABASE_TABLE, null, null, null, null, null, DATABASE_GROUP_1);
    }

    public Cursor getID(long rowID) {
        return mDB.query(DATABASE_TABLE, null, "_id" + " = "
                + rowID, null, null, null, null);
    }

    public class DatabaseHelper extends SQLiteAssetHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
    }

}
