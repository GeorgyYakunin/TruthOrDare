package com.example.truthordare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.truthordare.model.Player;
import com.example.truthordare.model.TruthOrDare;
import com.example.truthordare.types.GameMode;
import com.example.truthordare.types.QuestionType;
import java.util.ArrayList;
import java.util.List;

public class DBTruthDareAdapter {
    public static void Add_Question(Context context, TruthOrDare truthOrDare) {
        Add_Question(DatabaseHelper.getInstance(context).getWritableDatabase(), truthOrDare);
    }

    public static void Add_Question(SQLiteDatabase db, TruthOrDare truthOrDare) {
        try {
            ContentValues values = new ContentValues();
            values.put(TruthOrDare.KEY_QUE, truthOrDare.question);
            values.put(TruthOrDare.KEY_QTYPE, truthOrDare.questionType != null ? truthOrDare.questionType.getName() : "");
            values.put(TruthOrDare.KEY_USER, truthOrDare.isAddedByUser);
            values.put(TruthOrDare.KEY_MODE, truthOrDare.gameMode != null ? truthOrDare.gameMode.getName() : "");
            db.insert(DatabaseHelper.TABLE_TURTHDARE, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Delete_Question(Context context, int id) {
        try {
            SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
            db.delete(DatabaseHelper.TABLE_TURTHDARE, "_id = ?", new String[]{String.valueOf(id)});
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<TruthOrDare> Get_Display_Questions(Context context, QuestionType questionType, Integer isAddedByUser) {
        try {
            SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
            return getQuestions(db, db.query(DatabaseHelper.TABLE_TURTHDARE, TruthOrDare.COLS, "question_type = ?  " + (isAddedByUser == null ? "" : "AND add_by_user = ? "), isAddedByUser == null ? new String[]{questionType.getName()} : new String[]{questionType.getName(), String.valueOf(isAddedByUser)}, null, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    private static List<TruthOrDare> getQuestions(SQLiteDatabase db, Cursor cursor) {
        List<TruthOrDare> truthOrDareArrayList = new ArrayList();
        while (cursor.moveToNext()) {
            try {
                TruthOrDare truthOrDare = new TruthOrDare(cursor.getString(1), QuestionType.forName(cursor.getString(2)), Integer.valueOf(cursor.getInt(3)), GameMode.forName(cursor.getString(4)));
                truthOrDare.id = cursor.getInt(0);
                truthOrDareArrayList.add(truthOrDare);
            } catch (Exception e) {
                Log.e("all_contact", "" + e);
            }
        }
        cursor.close();
        db.close();
        return truthOrDareArrayList;
    }

    public static void Add_Player(Context context, Player player) {
        try {
            SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Player.KEY_NAME, player.playerName);
            db.insert(DatabaseHelper.TABLE_PLAYER, null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Delete_Player(Context context, int id) {
        try {
            SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
            db.delete(DatabaseHelper.TABLE_PLAYER, "_id = ?", new String[]{String.valueOf(id)});
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Player> Get_players(Context context) {
        List<Player> playerArrayList = new ArrayList();
        try {
            SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();
            Cursor cursor = db.query(DatabaseHelper.TABLE_PLAYER, new String[]{TruthOrDare.KEY_ID, Player.KEY_NAME}, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    playerArrayList.add(new Player(cursor.getInt(0), cursor.getString(1)));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e("all_contact", "" + e);
        }
        return playerArrayList;
    }
}
