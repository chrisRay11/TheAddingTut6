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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore);
        vName = this.getIntent().getStringExtra("fName");
        vScoreByTrys = this.getIntent().getStringExtra("fScoreByTrys");
        vCurrentDate = this.getIntent().getStringExtra("currentDate");
        vTotalTime = this.getIntent().getIntExtra("fTotalTime", 0);
        rInjection = this.getIntent().getStringExtra("insert");

        if (rInjection.equalsIgnoreCase("t")) {
            insertToView();
            rInjection = "r";
        } else if (rInjection.equalsIgnoreCase("r")) {
            setRefreshListView();
        }

        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {

                constantsCursor = (SQLiteCursor) arg0.getItemAtPosition(arg2);

                TextView idTextView = (TextView) findViewById(R.id._id);
                dbHelp = new HighScoreDB(ViewHighScore.this, DATABASE_NAME,
                        null, 1);

                dbHelp.delete(Integer.parseInt(constantsCursor.getInt(0) + ""));

                setRefreshListView();
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

        adapter = new SimpleCursorAdapter(this, R.layout.viewhighscore,
                constantsCursor, new String[]{HighScoreDB._ID,
                HighScoreDB.NAME, HighScoreDB.RATE_RANK,
                HighScoreDB.DATE, HighScoreDB.TOTAL_TIME}, new int[]{
                R.id._id, R.id.textView1, R.id.textView2,
                R.id.textView3, R.id.textView4}, 0);

        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
        Toast.makeText(ViewHighScore.this, "Deleted" + arg2, Toast.LENGTH_LONG)
                .show();

        return false;
    }

}
