package com.stembo.android.timepickerdialogdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends FragmentActivity {

    int mHour = 15;
    int mMinute = 15;

    //This handles the message send from ButtonTimePickerDialogFragment (TPDF)
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            //create a bundle object to pass the current set time
            Bundle bundle = msg.getData();//Obtains a Bundle of arbitrary data associated with this event, lazily creating it if necessary.
            //getting the hour of day from bundle
            mHour = bundle.getInt("current_hour");
            //getting the minute from bundle
            mMinute = bundle.getInt("current_minute");
            //displaying a short time message containing the time set by the TPDF
            Toast.makeText(getBaseContext(), bundle.getString("current_time"),Toast.LENGTH_SHORT).show();
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
                Snackbar.make(view, R.string.hello_world, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //click event handler for button
        OnClickListener btn_listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate a new calendar object to set current time
                GregorianCalendar calendar = new GregorianCalendar();
                //create a bundle object to pass current time set
                Bundle b = new Bundle();
                //add currently set minute+30 to bundle object
                mMinute = calendar.get(Calendar.MINUTE)+30;
                if (mMinute <=29){//if loop not working correctly TODO
                    //add currently set hour+1 to bundle object
                    mHour = calendar.get(Calendar.HOUR_OF_DAY)+1;
                }else{
                    //add currently set hour to bundle object
                    mHour = calendar.get(Calendar.HOUR_OF_DAY);
                }
                b.putInt("current_hour", mHour);
                b.putInt("current_minute", mMinute);
                //instantiate TPDF
                ButtonTimePickerDialogFragment timePicker = new ButtonTimePickerDialogFragment(mHandler);
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
        btnSet.setOnClickListener(btn_listener);

        //click event handler for edittext
        View.OnClickListener et_listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GregorianCalendar calendar = new GregorianCalendar();
                Bundle b = new Bundle();
                mMinute = calendar.get(Calendar.MINUTE);
                mHour = calendar.get(Calendar.HOUR_OF_DAY);
                b.putInt("current_hour", mHour);
                b.putInt("current_minute", mMinute);
                EditTextTimePickerDialogFragment timePicker = new EditTextTimePickerDialogFragment(mHandler);
                timePicker.setArguments(b);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(timePicker, "time_picker");
                ft.commit();
            }
        };//onclicklistener

        //get an instance of the EditText
        TextView textTimePicker = (TextView) findViewById(R.id.eTextTimePicker);
        //textTimePicker.setInputType(InputType.TYPE_NULL);
        textTimePicker.setOnClickListener(et_listener);

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
