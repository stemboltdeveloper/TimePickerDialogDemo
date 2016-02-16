package com.stembo.android.timepickerdialogdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

    int mHour = 15;
    int mMinute = 15;

    //This handles the message send from TimePickerDialogFragment (TPDF)
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message m){
            //create a bundle object to pass the current set time
            Bundle b = m.getData();
            //getting the hour of day from bundle
            mHour = b.getInt("set_hour");
            //getting the minute from bundle
            mMinute = b.getInt("set_minute");
            //displaying a short time message containing the time set by the TPDF
            Toast.makeText(getBaseContext(), b.getString("set_time"),Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //click event handler for button
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a bundle object to pass current time set
                Bundle b = new Bundle();
                //add currently set hour to bundle object
                b.putInt("set_hour", mHour);
                //add currently set minute to bundle object
                b.putInt("set_minute", mMinute);
                //instantiate TPDF
                TimePickerDialogFragment timePicker = new TimePickerDialogFragment(mHandler);
                //setting the bundle object on timepicker fragment
                timePicker.setArguments(b);
                //get a fragment manager for this activity
                FragmentManager fm = getSupportFragmentManager();
                //start a fragment transaction
                FragmentTransaction ft = fm.beginTransaction();
                //add the fragment object to the fragment transaction
                ft.add(timePicker, "time_picker");
                //opening the timepicker fragment
                ft.commit();
            }
        };
        //get an instance of Set button
        Button btnSet = (Button)findViewById(R.id.btnSet);
        btnSet.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
