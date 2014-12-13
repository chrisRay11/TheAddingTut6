package com.educ8.theaddingtut;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.zip.Inflater;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.transition.Visibility;
import android.util.Log;
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
import android.util.Log;

//import com.ex.test2.R;

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
	// ------------Send to next activity-------------------
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
	// ///////////////set by settings
	private int userSelectedTime;
	private int userSelectedCardAmount;
	private String userInputName;
	private String sInjection;
	private SharedPreferences sharedSettingsPref;
	private String continueBtnState;

	// = getIntent().getStringExtra("mathSymbol");

	// private int currentColor1;

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

		// Toast.makeText(this, sharedSettingsPref.getInt("userTimeSelected",
		// userSelectedTime) + " " +
		// sharedSettingsPref.getInt("userCardAmountSelected",
		// userSelectedCardAmount) + " " +
		// sharedSettingsPref.getString("inputedUserName", userInputName) + " "
		// + "Pulled From Saved Pref", Toast.LENGTH_SHORT).show();
		// Toast.makeText(this, userTime + " " + userNumTrys + " " + userName +
		// " " + "Values transfered to var's", Toast.LENGTH_SHORT).show();

		if (userTime == 0) {
			userTime = 10;
		}

		if (userNumTrys == 0) {
			userNumTrys = 10;
		}

		// Toast.makeText(FlashCard.this,userTime + "when we start time is",
		// Toast.LENGTH_SHORT).show();

		continueBtn = (ImageButton) findViewById(R.id.imageButton1);

		mathSymbol = getIntent().getIntExtra("mathSymbol1", 0);

		textViewX = (TextView) findViewById(R.id.textView4);
		sByT = (score + "/" + userNumTrys);
		textViewX.setText(score + "/" + userNumTrys);

		// textViewX.setText(mathSymbol);

		if (savedInstanceState != null) {
			score = savedInstanceState.getInt("score");
		}

		timePassed = new ArrayList<Integer>();

		for (int i = 0; i < userNumTrys; i++) {
			// timePassed.clear();
			timePassed.add(i, 0);
		}
		/*
		 * if(mathSymbol == 0){
		 * 
		 * textViewX.setText("were / in" + mathSymbol); }
		 */

		runMath();

		continueBtn.setVisibility(R.drawable.continue_blank);
		continueBtnState = "test";

		if (timerCountdown == true) {
			tickerC = (TextView) findViewById(R.id.textView6);
			turnOffTime();
			countDownWarning = (TextView) findViewById(R.id.textView7);
			countDownWarning.setVisibility(4);
			countDownTimer = new CountDownTimer(userTime * 1000, 1000) {

				public void onTick(long millisUntilFinished) {
					timeElapsed = (millisUntilFinished / 1000);
					tickerC.setText("Time: " + millisUntilFinished / 1000);
					if ((millisUntilFinished / 1000) == 10) {
						countDownWarning.setVisibility(0);
						countDownWarning.setText("10 Secs left");
						// changes countdown red
					}
					if ((millisUntilFinished / 1000) == 3) {
						countDownWarning.setVisibility(0);
						// 0 - visible
						// 4 - invisible
						// 8 - gone
						countDownWarning.setText("3 Secs left");
						// countDownWarning.setText(millisUntilFinished + " 3");
					}
					if ((millisUntilFinished / 1000) == 2) {
						countDownWarning.setVisibility(0);
						countDownWarning.setText("2 Secs left");
						// countDownWarning.setText(millisUntilFinished + " 2");
					}
					if ((millisUntilFinished / 1000) == 1) {

						// countDownWarning.setText(millisUntilFinished + " 1");
						countDownWarning.setText("1 Sec left");

						if ((millisUntilFinished / 1000) >= .9) {
							// tickerC.setText("Air Horn");

							// This will allow for air horn sound trigger

							// countDownWarning.setText("Air Horn");
						}
					}

				}

				public void onFinish() {
					tickerC.setText("Times up!");
					countDownWarning.setText(" ");
					countDownWarning.setVisibility(4);
					timesUp = true;

				}
			}.start();

		} else {
			// make timer textView invisible
		}

		// sets up the settings var to a shared preferences class with the
		// identifyer myprefs and default mode "version" of 0
		settings = getSharedPreferences("MYPREFS", 0);

	}

	public void turnOffTime() {
		if (userTime == 86400) {
			tickerC.setVisibility(4);
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

		// int SS = (int) *;

		// textViewX.setText(""+mathSymbol);

		textViewC = (TextView) findViewById(R.id.textView3);
		// textViewX.setText("" +mathSymbol);

		switch (mathSymbol) {
		case 0:
			ansA = (rand1) + (rand2);
			textViewC.setText("+");
			// textViewX.setText("hey i +");
			// textViewB.setText("me 2");
			break;
		case 1:
			ansA = (rand1) - (rand2);
			textViewC.setText("-");
			// textViewX.setText("hey i -");
			break;
		case 2:
			ansA = (rand1) * (rand2);
			textViewC.setText("*");
			// textViewX.setText("hey i *");
			break;

		case 3:
			if (((rand1 | rand2) != 0)) {
				if (rand2 == 0) {
					rand2 = 1;
				} else {
					ansA = (rand1) / (rand2);
					textViewC.setText("/");
					// textViewX.setText("hey i /");
				}
			} else {
				runMath();
				Toast.makeText(this, "Division reset", Toast.LENGTH_SHORT)
						.show();
			}
			break;/**/
		}

		// ansA = 5;
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
				Toast.makeText(this, "a equal to b reset", Toast.LENGTH_SHORT)
						.show();

			} else if (ansA == ansC) {
				ansC = ansA + 1;
				aList.set(2, ansC);
				Collections.shuffle(aList);
				valueSetter(aList);
				Toast.makeText(this, "a equal to c reset", Toast.LENGTH_SHORT)
						.show();

			} else if (ansB == ansC) {
				ansB += 1;
				aList.set(1, ansB);
				Collections.shuffle(aList);
				valueSetter(aList);
				Toast.makeText(this, "b equal to c reset", Toast.LENGTH_SHORT)
						.show();

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
		// Date = (TextView) findViewById(R.id.txtDate);
		Calendar cal = Calendar.getInstance();
		dd = cal.get(Calendar.DAY_OF_MONTH);
		mm = cal.get(Calendar.MONTH);
		yy = cal.get(Calendar.YEAR);
		// set current date into textview
		// Date.setText(new StringBuilder()
		// Month is 0 based, just add 1

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
			// textViewX.setText("yep passed");

			timeTaken = (int) timeElapsed;
			timePassed.set(timePassedCounter, timeTaken);

		}

		if (passedProblem == true & timesUp == false) {

			continueBtn.setVisibility(1);

			countDownTimer.cancel();

			// buttonAns1.setBackgroundResource(android.R.drawable.btn_default);
			// buttonAns2.setBackgroundResource(android.R.drawable.btn_default);
			// buttonAns3.setBackgroundResource(android.R.drawable.btn_default);

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

			// buttonAns1.setBackgroundResource(android.R.drawable.btn_default);
			// buttonAns2.setBackgroundResource(android.R.drawable.btn_default);
			// buttonAns3.setBackgroundResource(android.R.drawable.btn_default);

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
			// Add button to continue to next

			continueBtnState = "finished";

			buttonAns1.setOnClickListener(null);
			buttonAns2.setOnClickListener(null);
			buttonAns3.setOnClickListener(null);

			for (int i = 0; i < timePassedCounter; i++) {
				// timePassed.clear();
				// Toast.makeText(FlashCard.this, timePassed.get(i) + " " + i +
				// "\n", Toast.LENGTH_SHORT).show();
				// timeSum += (int) timePassed.get(i);
				timeSum = ((int) timeSum + (int) timePassed.get(i));

			}
			totalTestTime = (userNumTrys * userTime) - timeSum;
			// Toast.makeText(FlashCard.this," "+continueBtnState +
			// " continueBtnState",
			// Toast.LENGTH_LONG).show();
			contBtn1(v);
			/*
			 * continueBtn = (ImageView) findViewById(R.id.contBtn1);
			 */
		} else {
			// if the trys aren't specified number
			// continueBtn.setVisibility(1);
		}

	}

	public void contBtn1(View c) {

		if (continueBtnState == "test") {

			quitNcontuinueBtn();
		} else {
			// continueBtn.setVisibility(1);

			// Intent intent1 = new Intent(this, SecondActivity.class);
			// intent1.putExtra("score", 20);
			// startActivity(intent1);
			// continueBtn.setVisibility(android.R.drawable.btn_default);
			// continueBtn.setVisibility(R.drawable.continue_filled);
			continueBtn.setBackgroundResource(R.drawable.continue_filled);
			Toast.makeText(this, "fire fire fire fire", Toast.LENGTH_LONG)
					.show();
			// textViewX.setText("were / in" + mathSymbol);

			/*
			 * Intent intent2 = new Intent(this, ViewHighScore.class);
			 * startActivity(intent2);
			 */

			// add a dialog that inflates the layout and accepts a custom
			// content entry and pass the "name" value along with time score and
			// date to the db.

			// add the name entered into the saved prefences and once you return
			// to the popup auto fill out name

			quitNcontuinueBtn();

		}

	}

	public void quitNcontuinueBtn() {
		currentDate();

		AlertDialog.Builder diaBuilder = new AlertDialog.Builder(this);

		LayoutInflater nameInflater = this.getLayoutInflater();
		final View alertView = nameInflater.inflate(R.layout.nameentrydialog,
				null);

		// Toast.makeText(this, sharedSettingsPref.getString("inputedUserName",
		// userInputName), Toast.LENGTH_SHORT).show();

		answer = (EditText) alertView.findViewById(R.id.editText1);
		answer.setText(sharedSettingsPref.getString("inputedUserName",
				userInputName));

		// final EditText input = new EditText();
		// final EditText input = ((EditText) findViewById(R.id.editText1));

		diaBuilder
				.setMessage(R.string.nameEntryTitle)
				// .setView(nameInflater.inflate(R.layout.nameentrydialog,
				// null))
				.setView(alertView)
				.setPositiveButton(R.string.nameDiaOk,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// If ok is clicked pass the infomation name as
								// well as others
								// EditText answer = (EditText)
								// layout.findViewById(R.id.editText1);

								answer = (EditText) alertView
										.findViewById(R.id.editText1);

								// setting the entered text to the settings via
								// saved name key
								// answer.setText(settings.getString("savedName",
								// ""));

								answerEntered = answer.getEditableText()
										.toString();

								// ///////////////////////////////////

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

								// //////////////////////////////////////////////////////////////////

								// ////////////////////////////////////////////////////////
								/*
								 * 
								 * 
								 * 
								 * 
								 * 
								 * 
								 * 
								 * String someStringText = "Hellow there candy";
								 * 
								 * Intent i = new Intent(FlashCard.this,
								 * ViewHighScore.class);
								 * i.putExtra("testerString", someStringText);
								 * startActivity(i);
								 */
								// ////////////////////////////////////////////////////////

							}
						})
				.setNegativeButton(R.string.nameDiaCancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});

		AlertDialog dialog = diaBuilder.create();
		diaBuilder.show();
	}

	/*
	 * 
	 * public class nameEntry extends DialogFragment {
	 * 
	 * @Override public Dialog onCreateDialog(Bundle savedInstanceState) { //
	 * TODO Auto-generated method stub
	 * 
	 * AlertDialog.Builder diaBuilder = new AlertDialog.Builder(getActivity());
	 * 
	 * LayoutInflater nameInflater = getActivity().getLayoutInflater();
	 * 
	 * diaBuilder.setMessage(R.string.nameEntryTitle)
	 * .setView(nameInflater.inflate(R.layout.nameentrydialog, null))
	 * .setPositiveButton(R.string.nameDiaOk, new
	 * DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) { //
	 * TODO Auto-generated method stub //If ok is clicked pass the infomation
	 * name as well as others EditText answer = (EditText)
	 * findViewById(R.layout.nameentrydialog);
	 * 
	 * 
	 * } }) .setNegativeButton(R.string.nameDiaCancel, new
	 * DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) { //
	 * TODO Auto-generated method stub
	 * 
	 * } });
	 * 
	 * 
	 * return diaBuilder.create(); }
	 * 
	 * }
	 */

	@Override
	protected void onRestoreInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(outState);
		/*
		 * vName = savedInstanceState.getInt("fName"); vName =
		 * savedInstanceState.getInt("fName"); vName =
		 * savedInstanceState.getInt("fName"); vName =
		 * savedInstanceState.getInt("fName");
		 */

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
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	  // ignore orientation/keyboard change
	  super.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// userTime = userSelectedTime;
		// userNumTrys = userSelectedCardAmount;
		// userName = userInputName;

		/*
		 * SharedPreferences sharedSettingsPref =
		 * getSharedPreferences("selectedTimePref", 0);
		 * 
		 * userTime = sharedSettingsPref.getInt("userTimeSelected",
		 * userSelectedTime); userNumTrys =
		 * sharedSettingsPref.getInt("userCardAmountSelected",
		 * userSelectedCardAmount); userName =
		 * sharedSettingsPref.getString("inputedUserName", userInputName);
		 * 
		 * Toast.makeText(this, sharedSettingsPref.getInt("userTimeSelected",
		 * userSelectedTime) + " " +
		 * sharedSettingsPref.getInt("userCardAmountSelected",
		 * userSelectedCardAmount) + " " +
		 * sharedSettingsPref.getString("inputedUserName", userInputName) + " "
		 * + "Pulled From Saved Pref", Toast.LENGTH_SHORT).show();
		 * Toast.makeText(this, userTime + " " + userNumTrys + " " + userName +
		 * " " + "Values transfered to var's", Toast.LENGTH_SHORT).show();
		 */
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		// Saves on rotate

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
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		SharedPreferences settings = getSharedPreferences("MYPREFS", 0);
		SharedPreferences.Editor settingsEditor = settings.edit();
        if(answer != null){
            settingsEditor.putString("savedName", answer.getText().toString());
            settingsEditor.commit();
        }else{

        }


		finish();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		finish();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		// use xml to create menus
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem itemXYZ) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		/*
		 * Toast.makeText(this, itemXYZ.getTitle(), Toast.LENGTH_SHORT).show();
		 * switch (itemXYZ.getItemId()) {
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * case R.id.menuSettings: //MediaPlayer myMedia =
		 * MediaPlayer.create(this, R.raw.symbolcrash1); // myMedia.start();
		 * //myMedia.stop();
		 * 
		 * Toast.makeText(FlashCard.this,"Clicked Menu",
		 * Toast.LENGTH_SHORT).show(); return true; case R.id.t10:
		 * 
		 * return true; case R.id.t20:
		 * 
		 * return true; case R.id.t30:
		 * 
		 * return true; case R.id.cardAmount:
		 * 
		 * return true; case R.id.ca10:
		 * 
		 * return true; case R.id.ca20:
		 * 
		 * return true; case R.id.ca30:
		 * 
		 * return true; case R.id.timeronoff:
		 * 
		 * return true; case R.id.Ton:
		 * 
		 * return true; case R.id.Toff:
		 * 
		 * return true; case R.id.posNegM:
		 * 
		 * return true; case R.id.pos:
		 * 
		 * return true; case R.id.posneg:
		 * 
		 * return true; default: return super.onOptionsItemSelected(itemXYZ);
		 */
		int id = itemXYZ.getItemId();
		if (id == R.id.action_settings) {
			// Toast.makeText(getApplicationContext(),
			// item.getTitle(),Toast.LENGTH_SHORT).show();
			Intent settingsIntent = new Intent(this, Settings.class);
			startActivity(settingsIntent);
			return true;
		}
		/*
		 * once finished we get a new activity that asks for name gives score
		 * out of questions ammount (10,20,30) total time and date name |
		 * score/trys | total time | date
		 */
		return true;
	}
	/*
	 * pblic void delete (int id){ this.getwritabledatabase().delete(Table,
	 * "_id = " + id, null); }
	 */

	// System.exit(0);

	// Alphabets
	// Word reconition
	// Sentences
	// animals
	// shapes
	// letter sounds
	// colors
	// number reconition
	// adding
	//

}
