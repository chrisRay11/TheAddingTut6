package com.educ8.theaddingtut;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ViewHighScore extends Activity implements OnItemLongClickListener {


	SimpleCursorAdapter adapter;
	private static final String DATABASE_NAME = "tutLeaderBoard";

	private String vName;
	private String vScoreByTrys;
	private String vCurrentDate;
	private int vTotalTime;
	private int iRow;
	private SQLiteCursor constantsCursor;
	private String vId;
	private HighScoreDB dbHelp;
	private String rInjection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscore);
		/*
		 * vName = getIntent().getExtras("fName", " "); vScoreByTrys =
		 * getIntent().getIntExtra("fScoreByTrys", " "); vTotalTime =
		 * getIntent().getIntExtra("fTotalTime", " "); vCurrentDate =
		 * getIntent().getIntExtra("fCurrentDate", "xx-XX-xxXX");
		 */
		vName = this.getIntent().getStringExtra("fName");
		vScoreByTrys = this.getIntent().getStringExtra("fScoreByTrys");
		vCurrentDate = this.getIntent().getStringExtra("currentDate");
		vTotalTime = this.getIntent().getIntExtra("fTotalTime", 0);
		rInjection = this.getIntent().getStringExtra("insert");
		
		
		
		// //////////////////////////////////////////////////////////
		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * Intent intentRec = getIntent(); String testRec =
		 * intentRec.getExtras().getString("testerString");
		 * 
		 * if (testRec == null){ testRec = "Nothing in there"; }
		 * 
		 * TextView testy = (TextView) findViewById(R.id.test);
		 * testy.setText(testRec);
		 */
		// ///////////////////////////////////////////////////////////////
		// Toast.makeText(this,"rInjection = " + rInjection,
		// Toast.LENGTH_LONG).show();
		if (rInjection.equalsIgnoreCase("t")) {
			insertToView();
			rInjection = "r";
		} else if (rInjection.equalsIgnoreCase("r")) {

			
			setRefreshListView();
		}

		 Toast.makeText(this,"injection = " + rInjection + " bla = " + "b",
					 Toast.LENGTH_LONG).show();
		
		// insertToView();

		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				// /////////////
				constantsCursor = (SQLiteCursor) arg0.getItemAtPosition(arg2);

				TextView idTextView = (TextView) findViewById(R.id._id);
				// Toast.makeText(ViewHighScore.this,R.id._id + "",
				// Toast.LENGTH_LONG).show();
				dbHelp = new HighScoreDB(ViewHighScore.this, DATABASE_NAME,
						null, 1);

				// dbHelp.delete(Integer.parseInt(idTextView.getText()+""));
				dbHelp.delete(Integer.parseInt(constantsCursor.getInt(0) + ""));

				// Toast.makeText(ViewHighScore.this,"Delete " + arg0 + " naaa "
				// + Integer.parseInt(idTextView.getText()+""),
				// Toast.LENGTH_LONG).show();

				setRefreshListView();

				// dbHelp.delete("highscores", "id + =" + arg2, null);

				// /////////////
				return true;
			}

		});

	}

	public void homebtn1(View h) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		startActivity(intent);
	}

	public void backBtn1(View b1) {
		this.finish();
	}

	public void insertToView() {
		dbHelp = new HighScoreDB(this, DATABASE_NAME, null, 1);
		dbHelp.insertField(vId, vName, vScoreByTrys, vCurrentDate, vTotalTime
				+ "s");
		setRefreshListView();
	}

	public void setRefreshListView() {
		dbHelp = new HighScoreDB(this, DATABASE_NAME, null, 1);

		constantsCursor = (SQLiteCursor) dbHelp
				.getReadableDatabase()
				.rawQuery(
						"SELECT _ID, name, scoreByTrys, dateT, totalTime FROM highscores ORDER BY dateT",
						null);

		// constantsCursor = (SQLiteCursor)
		// dbHelp.getReadableDatabase().rawQuery("SELECT _ID, name, scoreByTrys, dateT, totalTime FROM highscores ORDER BY dateT",
		// null);

		// SQLiteCursor constantsCursor = (SQLiteCursor)
		// dbHelp.getReadableDatabase().rawQuery("SELECT _ID, name, FROM TutLeaderBoard ORDER BY name",
		// null);

		adapter = new SimpleCursorAdapter(this, R.layout.viewhighscore,
				constantsCursor, new String[] { HighScoreDB._ID,
						HighScoreDB.NAME, HighScoreDB.RATE_RANK,
						HighScoreDB.DATE, HighScoreDB.TOTAL_TIME }, new int[] {
						R.id._id, R.id.textView1, R.id.textView2,
						R.id.textView3, R.id.textView4 }, 0);

		// iRow = constantsCursor.getColumnIndex("_ID");
		// Toast.makeText(this,"ok i am trying to access " + iRow,
		// Toast.LENGTH_LONG).show();

		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);

	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	  // ignore orientation/keyboard change
	  super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(ViewHighScore.this, "goodbye" + arg2, Toast.LENGTH_LONG)
				.show();

		return false;
	}

	/*
	 * @Override public void onItemLongClick(AdapterView<?> arg0, View arg1, int
	 * arg2, long arg3) { // TODO Auto-generated method stub
	 * 
	 * Cursor cursor = (Cursor) listView.getItemAtPosition(position); // TODO
	 * Auto-generated method stub
	 * 
	 * switch (arg2) { case 0: Toast.makeText(this,"hi",
	 * Toast.LENGTH_LONG).show(); break;
	 * 
	 * default: break; }
	 * 
	 * return false;
	 */
	
	
	
	
}

// Read up on documentation on requery

// }
