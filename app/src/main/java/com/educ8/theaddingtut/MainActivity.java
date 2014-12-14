package com.educ8.theaddingtut;

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

public class MainActivity extends Activity implements OnItemClickListener {

    private int mathSymbol;
    private String[] items = {"Addition", "Subtraction", "Multiplication", "Division",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);

        ListView mylist = (ListView) findViewById(R.id.myList);
        mylist.setAdapter(adapter);

        mylist.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                            long arg3) {
        Intent intent = new Intent(this, FlashCard.class);
        switch (items[arg2]) {
            case "Addition":
                mathSymbol = 0;
                break;
            case "Subtraction":
                mathSymbol = 1;
                break;
            case "Multiplication":
                mathSymbol = 2;
                break;
            case "Division":
                mathSymbol = 3;
                break;
            case "R.id.action_settings":
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
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, Settings.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
