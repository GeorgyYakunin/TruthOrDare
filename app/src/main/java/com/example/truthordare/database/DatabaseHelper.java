package com.example.truthordare.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_PLAYERS_TABLE = "CREATE TABLE player(_id INTEGER PRIMARY KEY,player_name TEXT)";
    public static final String CREATE_TRUTHDARE_TABLE = "CREATE TABLE truthdare(_id INTEGER PRIMARY KEY AUTOINCREMENT, question TEXT, add_by_user INTEGER, question_type TEXT, _gameMode TEXT)";
    public static final String DATABASE_NAME = "truthordareManager";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_PLAYER = "player";
    public static final String TABLE_TURTHDARE = "truthdare";
    private static DatabaseHelper sInstance;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        DatabaseHelper databaseHelper;
        synchronized (DatabaseHelper.class) {
            if (sInstance == null) {
                sInstance = new DatabaseHelper(context.getApplicationContext());
            }
            databaseHelper = sInstance;
        }
        return databaseHelper;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PLAYERS_TABLE);
        db.execSQL(CREATE_TRUTHDARE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE truthdare");
        db.execSQL(CREATE_TRUTHDARE_TABLE);
    }
}
