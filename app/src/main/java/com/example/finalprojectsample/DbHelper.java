package com.example.finalprojectsample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favorite_jokes.db";
    private static final int DATABASE_VERSION = 1;


    // Table and column names
    private static final String TABLE_FAVORITE_JOKES = "favorite_jokes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_JOKE = "joke";
    private static final String COLUMN_RATING = "rating";

    // Create table query
    private static final String CREATE_TABLE_FAVORITE_JOKES =
            "CREATE TABLE " + TABLE_FAVORITE_JOKES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_JOKE + " TEXT," +
                    COLUMN_RATING + " REAL)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the favorite jokes table
        db.execSQL(CREATE_TABLE_FAVORITE_JOKES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists and create new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_JOKES);
        onCreate(db);
    }

    // Insert a new favorite joke into the database
    public void addFavoriteJoke(String joke, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_JOKE, joke);
        values.put(COLUMN_RATING, rating);
        db.insert(TABLE_FAVORITE_JOKES, null, values);
        db.close();
    }

    // Get all favorite jokes from the database
    public List<String> getAllFavoriteJokes() {
        List<String> favoriteJokes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_FAVORITE_JOKES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                favoriteJokes.add(cursor.getString(cursor.getColumnIndex(COLUMN_JOKE)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favoriteJokes;
    }
}

