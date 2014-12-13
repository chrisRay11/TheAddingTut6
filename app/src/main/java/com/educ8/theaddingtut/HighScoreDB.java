package com.educ8.theaddingtut;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

public class HighScoreDB extends SQLiteOpenHelper {
	//name | score/trys | total time | date
	private static final String DATABASE_NAME = "tutLeaderBoard";
	static final String NAME = "name"; 
	static final String RATE_RANK = "scoreByTrys";
	static final String DATE = "dateT";
	static final String TOTAL_TIME = "totalTime";
	static final String _ID =  "_id";
	
	
	
	public HighScoreDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, null, 1);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE highscores (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, scoreByTrys TEXT, dateT TEXT, totalTime REAL);");
		
		
	}

	

	public void insertField(String _id, String name, String scoreByTrys, String dateT, String totalTime) {
		// TODO Auto-generated method stubStri
		ContentValues cv= new ContentValues();
		
		cv.put(_ID, _id);
		cv.put(NAME, name);
		cv.put(RATE_RANK, scoreByTrys);
		cv.put(DATE, dateT);
		cv.put(TOTAL_TIME, totalTime);
		getWritableDatabase().insert("highscores", NAME, cv);
		
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		android.util.Log.w("highscores", "Upgrading database, which will destroy all old data");
		
		db.execSQL("DROP TABLE IF EXISTS highscores");
		onCreate(db);
	}

	public void delete (int id){
		//this.getWritableDatabase().delete("highscores", "_id =" iRow, null);
		this.getWritableDatabase().delete("highscores", "_id =" + id, null);

		
		
		//this.getWritableDatabase().rawQuery("DELETE FROM highscores WHERE _id =" + id, null);
		//SQLiteCursor constantsCursor = (SQLiteCursor) this.getWritableDatabase().rawQuery("DELETE FROM highscores WHERE _id =" + id, null);
		//SQLiteCursor constantsCursor = (SQLiteCursor) this.getWritableDatabase().rawQuery("DELETE FROM highscores _id ORDER BY dateT", null);
		//db.execSQL("CREATE TABLE highscores (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, scoreByTrys TEXT, dateT TEXT, totalTime REAL);");
	}
}
