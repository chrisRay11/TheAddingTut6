package com.educ8.theaddingtut;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Settings extends Activity {

    private int userSelectedTime;
    private int userSelectedCardAmount;
    private String userInputName;
    private String sInjection;
    private EditText InputName;

    private SharedPreferences sharedSettingsPref;
    private Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_settings);
        sharedSettingsPref = getSharedPreferences("selectedTimePref", 0);
        editor = sharedSettingsPref.edit();
        InputName = (EditText) findViewById(R.id.userEditText0);
        InputName.setText(sharedSettingsPref.getString("inputedUserName", userInputName));
    }

    public void r10s(View r) {
        userSelectedTime = 10;
        editor.putInt("userTimeSelected", userSelectedTime);
        editor.commit();
    }

    public void r20s(View r) {
        userSelectedTime = 20;
        editor.putInt("userTimeSelected", userSelectedTime);
        editor.commit();
    }

    public void r30s(View r) {
        userSelectedTime = 30;
        editor.putInt("userTimeSelected", userSelectedTime);
        editor.commit();
    }

    public void rOff(View r) {
        userSelectedTime = 86400;//a day per question
        editor.putInt("userTimeSelected", userSelectedTime);
        editor.commit();
    }

    public void c10(View c) {
        userSelectedCardAmount = 10;
        editor.putInt("userCardAmountSelected", userSelectedCardAmount);
        editor.commit();
    }

    public void c20(View c) {
        userSelectedCardAmount = 20;
        editor.putInt("userCardAmountSelected", userSelectedCardAmount);
        editor.commit();
    }

    public void c30(View c) {
        userSelectedCardAmount = 30;
        editor.putInt("userCardAmountSelected", userSelectedCardAmount);
        editor.commit();
    }

    public void c100(View c) {
        userSelectedCardAmount = 100;
        editor.putInt("userCardAmountSelected", userSelectedCardAmount);
        editor.commit();
    }

    public void histClick(View h) {
        Intent intentDB = new Intent(this, ViewHighScore.class);
        //add name to DB
        intentDB.putExtra("fName", "");
        //add score/try amount to DB
        intentDB.putExtra("fScoreByTrys", "");
        //current date
        intentDB.putExtra("currentDate", "");
        //total amount of seconds
        intentDB.putExtra("fTotalTime", "");
        //send insert or not
        sInjection = "r";
        intentDB.putExtra("insert", sInjection);
        startActivity(intentDB);
    }

    public void subText(View s) {
        InputName = (EditText) findViewById(R.id.userEditText0);
        userInputName = InputName.getEditableText().toString();
        editor.putString("inputedUserName", userInputName);
        editor.commit();
    }

    ///////////////////////////
    public void backBtn0(View b) {
        this.finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
