package com.educ8.theaddingtut;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class HighScoreDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tutLeaderBoard";
    static final String NAME = "name";
    static final String RATE_RANK = "scoreByTrys";
    static final String DATE = "dateT";
    static final String TOTAL_TIME = "totalTime";
    static final String _ID = "_id";


    public HighScoreDB(Context context, String name, CursorFactory factory,
                       int version) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE highscores (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, scoreByTrys TEXT, dateT TEXT, totalTime REAL);");
    }


    public void insertField(String _id, String name, String scoreByTrys, String dateT, String totalTime) {
        ContentValues cv = new ContentValues();
        cv.put(_ID, _id);
        cv.put(NAME, name);
        cv.put(RATE_RANK, scoreByTrys);
        cv.put(DATE, dateT);
        cv.put(TOTAL_TIME, totalTime);
        getWritableDatabase().insert("highscores", NAME, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        android.util.Log.w("highscores", "Upgrading database, which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS highscores");
        onCreate(db);
    }

    public void delete(int id) {
        this.getWritableDatabase().delete("highscores", "_id =" + id, null);
    }
}
