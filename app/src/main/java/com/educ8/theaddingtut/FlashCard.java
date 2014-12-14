package com.educ8.theaddingtut;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class FlashCard extends Activity implements OnClickListener {

    private int rand1;
    private int rand2;
    private int ansA;
    private int ansB;
    private int ansC;
    private Button buttonAns1, buttonAns2, buttonAns3;
    private boolean passedProblem, timesUp;
    private int score = 0, trys = 0;
    private TextView textViewX, textViewC, tickerC, countDownWarning;
    private ImageButton continueBtn;
    private int mathSymbol;
    private boolean timerCountdown = true;
    private CountDownTimer countDownTimer;
    private SharedPreferences settings;

    private EditText answer;
    private String answerEntered;
    private int userNumTrys;
    private int userTime;
    private String userName;
    private int timePassedCounter = 0;
    private long timeElapsed;
    private int timeTaken;
    private ArrayList<Integer> timePassed;
    private int timeSum;
    private int totalTestTime;
    private int dd;
    private int mm;
    private int yy;
    private String pullDate;
    private String sByT;
    private int userSelectedTime;
    private int userSelectedCardAmount;
    private String userInputName;
    private String sInjection;
    private SharedPreferences sharedSettingsPref;
    private String continueBtnState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        sharedSettingsPref = getSharedPreferences("selectedTimePref", 0);

        userTime = sharedSettingsPref.getInt("userTimeSelected",
                userSelectedTime);
        userNumTrys = sharedSettingsPref.getInt("userCardAmountSelected",
                userSelectedCardAmount);
        userName = sharedSettingsPref.getString("inputedUserName",
                userInputName);

        if (userTime == 0) {
            userTime = 10;
        }

        if (userNumTrys == 0) {
            userNumTrys = 10;
        }

        continueBtn = (ImageButton) findViewById(R.id.imageButton1);

        mathSymbol = getIntent().getIntExtra("mathSymbol1", 0);

        textViewX = (TextView) findViewById(R.id.textView4);
        sByT = (score + "/" + userNumTrys);
        textViewX.setText(score + "/" + userNumTrys);

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt("score");
        }

        timePassed = new ArrayList<Integer>();

        for (int i = 0; i < userNumTrys; i++) {
            timePassed.add(i, 0);
        }

        runMath();

        continueBtn.setVisibility(View.INVISIBLE);
        continueBtnState = "test";

        if (timerCountdown == true) {
            tickerC = (TextView) findViewById(R.id.textView6);
            turnOffTime();
            countDownWarning = (TextView) findViewById(R.id.textView7);
            countDownWarning.setVisibility(View.INVISIBLE);
            countDownTimer = new CountDownTimer(userTime * 1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    timeElapsed = (millisUntilFinished / 1000);
                    tickerC.setText("Time: " + millisUntilFinished / 1000);
                    if ((millisUntilFinished / 1000) == 10) {
                        countDownWarning.setVisibility(View.VISIBLE);
                        countDownWarning.setText("10 Secs left");
                    }
                    if ((millisUntilFinished / 1000) == 3) {
                        countDownWarning.setVisibility(View.VISIBLE);
                        countDownWarning.setText("3 Secs left");
                    }
                    if ((millisUntilFinished / 1000) == 2) {
                        countDownWarning.setVisibility(View.VISIBLE);
                        countDownWarning.setText("2 Secs left");
                    }
                    if ((millisUntilFinished / 1000) == 1) {
                        countDownWarning.setText("1 Sec left");

                        if ((millisUntilFinished / 1000) >= .9) {
                            //final sound
                        }
                    }

                }

                public void onFinish() {
                    tickerC.setText("Times up!");
                    countDownWarning.setText(" ");
                    countDownWarning.setVisibility(View.INVISIBLE);
                    timesUp = true;

                }
            }.start();

        } else {
            // make timer textView invisible
        }
        settings = getSharedPreferences("MYPREFS", 0);
    }

    public void turnOffTime() {
        if (userTime == 86400) {
            tickerC.setVisibility(View.INVISIBLE);
        }
    }

    public void runMath() {
        timesUp = false;

        rand1 = (int) (Math.random() * 10);
        rand2 = (int) (Math.random() * 10);

        TextView textViewA = (TextView) findViewById(R.id.textView1);
        textViewA.setText("" + rand1);
        TextView textViewB = (TextView) findViewById(R.id.textView2);
        textViewB.setText("" + rand2);

        int adjuster1 = rand1 - rand2;
        int adjuster2 = rand1 + rand2;

        textViewC = (TextView) findViewById(R.id.textView3);

        switch (mathSymbol) {
            case 0:
                ansA = (rand1) + (rand2);
                textViewC.setText("+");
                break;
            case 1:
                ansA = (rand1) - (rand2);
                textViewC.setText("-");
                break;
            case 2:
                ansA = (rand1) * (rand2);
                textViewC.setText("*");
                break;

            case 3:
                if (((rand1 | rand2) != 0)) {
                    if (rand2 == 0) {
                        rand2 = 1;
                    } else {
                        ansA = (rand1) / (rand2);
                        textViewC.setText("/");
                    }
                } else {
                    runMath();
                }
                break;
        }

        ansB = adjuster1 + (int) (Math.random() * 10);
        ansC = adjuster2 + (int) (Math.random() * 10);

        if ((ansB | ansC) == 0) {
            runMath();
        } else {

            ArrayList<Integer> aList = new ArrayList<Integer>();
            aList.add(ansA);
            aList.add(ansB);
            aList.add(ansC);

            if (ansA == ansB) {
                ansB = ansA - 1;
                aList.set(1, ansB);
                Collections.shuffle(aList);
                valueSetter(aList);

            } else if (ansA == ansC) {
                ansC = ansA + 1;
                aList.set(2, ansC);
                Collections.shuffle(aList);
                valueSetter(aList);

            } else if (ansB == ansC) {
                ansB += 1;
                aList.set(1, ansB);
                Collections.shuffle(aList);
                valueSetter(aList);


            } else {
                Collections.shuffle(aList);
                valueSetter(aList);
            }

        }

    }

    public void valueSetter(ArrayList<Integer> aList) {
        buttonAns1 = (Button) findViewById(R.id.button1);
        buttonAns1.setText("" + aList.get(0));

        buttonAns2 = (Button) findViewById(R.id.button2);
        buttonAns2.setText("" + aList.get(1));

        buttonAns3 = (Button) findViewById(R.id.button3);
        buttonAns3.setText("" + aList.get(2));

        buttonAns1.setOnClickListener(this);
        buttonAns2.setOnClickListener(this);
        buttonAns3.setOnClickListener(this);
    }

    public void currentDate() {
        Calendar cal = Calendar.getInstance();
        dd = cal.get(Calendar.DAY_OF_MONTH);
        mm = cal.get(Calendar.MONTH);
        yy = cal.get(Calendar.YEAR);

        pullDate = ((mm + 1) + "-" + dd + "-" + yy);

    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        String buttonText = b.getText().toString();
        passedProblem = false;
        switch (v.getId()) {
            case R.id.button1:

                if (buttonText.equals(Integer.toString(ansA))) {
                    passedProblem = true;

                } else {
                    buttonAns1.setBackgroundColor(Color.RED);

                }
                break;

            case R.id.button2:
                if (buttonText.equals(Integer.toString(ansA))) {
                    passedProblem = true;

                } else {
                    buttonAns2.setBackgroundColor(Color.RED);
                }
                break;

            case R.id.button3:
                if (buttonText.equals(Integer.toString(ansA))) {
                    passedProblem = true;

                } else {
                    buttonAns3.setBackgroundColor(Color.RED);
                }
                break;

        }

        if (passedProblem == true) {

            timeTaken = (int) timeElapsed;
            timePassed.set(timePassedCounter, timeTaken);

        }

        if (passedProblem == true & timesUp == false) {
            Toast.makeText(this, "Correct!!!", Toast.LENGTH_SHORT)
                    .show();

            countDownTimer.cancel();
            buttonAns1.setBackgroundResource(R.drawable.appbackbtm1);
            buttonAns2.setBackgroundResource(R.drawable.appbackbtm1);
            buttonAns3.setBackgroundResource(R.drawable.appbackbtm1);
            score++;
            textViewX = (TextView) findViewById(R.id.textView4);
            textViewX.setText(score + "/" + userNumTrys);
            countDownWarning.setText("");
            trys++;
            timePassedCounter++;
            runMath();
            countDownTimer.start();

        } else if (passedProblem == true & timesUp == true) {
            Toast.makeText(this, "Thats right try to be a little faster next time.", Toast.LENGTH_SHORT)
                    .show();
            buttonAns1.setBackgroundResource(R.drawable.appbackbtm1);
            buttonAns2.setBackgroundResource(R.drawable.appbackbtm1);
            buttonAns3.setBackgroundResource(R.drawable.appbackbtm1);
            countDownTimer.cancel();
            countDownWarning.setText("");
            trys++;
            timePassedCounter++;
            runMath();
            countDownTimer.start();
        } else {
            // do nothing if answer value returns false
        }

        if (trys == userNumTrys) {
            continueBtn.setVisibility(View.VISIBLE);
            continueBtnState = "finished";
            buttonAns1.setOnClickListener(null);
            buttonAns2.setOnClickListener(null);
            buttonAns3.setOnClickListener(null);
            for (int i = 0; i < timePassedCounter; i++) {
                timeSum = ((int) timeSum + (int) timePassed.get(i));
            }
            totalTestTime = (userNumTrys * userTime) - timeSum;
            contBtn1(v);
        } else {
            // if the trys aren't specified number
        }

    }

    public void contBtn1(View c) {
        if (continueBtnState == "test") {
            quitNcontuinueBtn();
        } else {
            continueBtn.setBackgroundResource(R.drawable.continue_filled);
            quitNcontuinueBtn();
        }
    }

    public void quitNcontuinueBtn() {
        currentDate();
        AlertDialog.Builder diaBuilder = new AlertDialog.Builder(this);
        LayoutInflater nameInflater = this.getLayoutInflater();
        final View alertView = nameInflater.inflate(R.layout.nameentrydialog,
                null);

        answer = (EditText) alertView.findViewById(R.id.editText1);
        answer.setText(sharedSettingsPref.getString("inputedUserName",
                userInputName));

        diaBuilder
                .setMessage(R.string.nameEntryTitle)
                .setView(alertView)
                .setPositiveButton(R.string.nameDiaOk,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                answer = (EditText) alertView
                                        .findViewById(R.id.editText1);
                                answerEntered = answer.getEditableText()
                                        .toString();
                                Intent intentDB = new Intent(FlashCard.this,
                                        ViewHighScore.class);
                                // add name to DB
                                intentDB.putExtra("fName", answerEntered);
                                // add score/try amount to DB
                                intentDB.putExtra("fScoreByTrys", score + "/"
                                        + userNumTrys);
                                // current date
                                intentDB.putExtra("currentDate", pullDate);
                                // total amount of seconds
                                intentDB.putExtra("fTotalTime", totalTestTime);
                                // send insert or not
                                sInjection = "t";
                                intentDB.putExtra("insert", sInjection);
                                startActivity(intentDB);
                            }
                        })
                .setNegativeButton(R.string.nameDiaCancel,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        });

        AlertDialog dialog = diaBuilder.create();
        diaBuilder.show();
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);

        mathSymbol = outState.getInt("outStateMathSymbol");
        timerCountdown = outState.getBoolean("outTimer");
        trys = outState.getInt("outTrys");
        score = outState.getInt("outStateScore");
        sByT = outState.getString("outStateScoreByTrys");
        totalTestTime = outState.getInt("outStateTotalTime");
        userNumTrys = outState.getInt("outStateUserNumTrys");
        userTime = outState.getInt("outStateUserTime");
        sInjection = outState.getString("outStateSInjection");
        answerEntered = outState.getString("outStateAnswerEntered");
        userTime = outState.getInt("outStateUserTime");
        timeSum = outState.getInt("outTimeSum");
        userNumTrys = outState.getInt("outStateUserNumTrys");
        timePassedCounter = outState.getInt("outStatetimePassedCounter");

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("outStateMathSymbol", mathSymbol);
        outState.putBoolean("outTimer", timerCountdown);
        outState.putInt("outTrys", trys);
        outState.putInt("outStateScore", score);
        outState.putString("outStateScoreByTrys", sByT);
        outState.putInt("outStateTotalTime", totalTestTime);
        outState.putInt("outStateUserNumTrys", userNumTrys);
        outState.putInt("outStateUserTime", userTime);
        outState.putString("outStateSInjection", sInjection);
        outState.putString("outStateAnswerEntered", answerEntered);
        outState.putInt("outStateUserTime", userTime);
        outState.putInt("outTimeSum", timeSum);
        outState.putInt("outStateUserNumTrys", userNumTrys);
        outState.putInt("outStatetimePassedCounter", timePassedCounter);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences settings = getSharedPreferences("MYPREFS", 0);
        SharedPreferences.Editor settingsEditor = settings.edit();
        if (answer != null) {
            settingsEditor.putString("savedName", answer.getText().toString());
            settingsEditor.commit();
        } else {

        }

        finish();
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem itemXYZ) {
        int id = itemXYZ.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, Settings.class);
            startActivity(settingsIntent);
            return true;
        }
        return true;
    }

}
