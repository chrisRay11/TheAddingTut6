package com.educ8.theaddingtut;

//import com.lyfecycleprocess.lyfecycle.FlashCard;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener {

		private int mathSymbol;
		private String[] items = { "Addition", "Subtraction","Multiplication", "Division", };
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		                android.R.layout.simple_list_item_1, items);
		
		ListView mylist = (ListView) findViewById(R.id.myList);
		mylist.setAdapter(adapter);
		
			
		//make the click
		mylist.setOnItemClickListener(this);
		
	}

	
	
	@Override
	//(AdapterView<?> parent, View view, int position,long id)
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		//Toast.makeText(this, "you clicked on:" + items[arg2], Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, FlashCard.class);
		
		
		switch (items[arg2]) {
		case "Addition":
			Toast.makeText(this, "+:" + items[arg2], Toast.LENGTH_SHORT).show();
			mathSymbol = 0;
		//	intent.putExtra("mathSymbol1", mathSymbol);
		//	startActivity(intent);
			break;
		case "Subtraction":
			Toast.makeText(this, "+:" + items[arg2], Toast.LENGTH_SHORT).show();
			mathSymbol = 1;
		//	intent.putExtra("mathSymbol1", mathSymbol);
		//	startActivity(intent);
			break;
		case "Multiplication":
			Toast.makeText(this, "+:" + items[arg2], Toast.LENGTH_SHORT).show();
			mathSymbol = 2;
		//	intent.putExtra("mathSymbol1", mathSymbol);
		//	startActivity(intent);
			break;
		case "Division":
			Toast.makeText(this, "+:" + items[arg2], Toast.LENGTH_SHORT).show();
			mathSymbol = 3;
		//	intent.putExtra("mathSymbol1", mathSymbol);
		//	startActivity(intent);
			break;
		case "R.id.action_settings":
			Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
		//	intent.putExtra("mathSymbol1", mathSymbol);
		//	startActivity(intent);
			break;
		}
		
		intent.putExtra("mathSymbol1", mathSymbol);
		startActivity(intent);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = new MenuInflater(this);
		  menuInflater.inflate(R.menu.settings, menu);
		  return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.action_settings){
			//Toast.makeText(getApplicationContext(), item.getTitle(),Toast.LENGTH_SHORT).show();
			Intent settingsIntent = new Intent(this, Settings.class);
			startActivity(settingsIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	//onDestroy onPause onRestart onRestoreInstanceState onResume onSaveInstanceState onStart onStop

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		//mathSymbol = savedInstanceState.getString("mathSymbol");
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		//outState.putString("mathSymbol", mathSymbol);
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
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	


		
	
}
