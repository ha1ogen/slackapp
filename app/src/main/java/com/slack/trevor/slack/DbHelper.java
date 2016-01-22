package com.slack.trevor.slack;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONObject;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Slack";
    private static final String TABLE_NAME = "team_members";
    private static final String MEMBERS_COLUMN_NAME = "members";
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    MEMBERS_COLUMN_NAME + " STRING);";

    private static final String TABLE_DELETE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_DELETE);
        onCreate(db);
    }

    public long writeToDb(JSONObject jsonObject) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MEMBERS_COLUMN_NAME, jsonObject.toString());

        // Clear the table
        db.delete(TABLE_NAME, null, null);

        // Insert the new row, returning the primary key value of the new row
        return db.insert(TABLE_NAME, MEMBERS_COLUMN_NAME, values);
    }
}