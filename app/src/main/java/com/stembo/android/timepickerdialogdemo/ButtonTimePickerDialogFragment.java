package com.stembo.android.timepickerdialogdemo;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TimePicker;

/**
 * Created by myxom on 16/02/16.
 */
public class ButtonTimePickerDialogFragment extends DialogFragment {
    Handler mHandler ;
    int mHour;
    int mMinute;

    //public ButtonTimePickerDialogFragment(){}; //solves a lint issue

    public ButtonTimePickerDialogFragment(Handler h){ //another lint issue
        //get a reference to the message handler instantiated in mainactivity
        mHandler = h;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        /** Creating a bundle object to pass currently set time to the fragment */
        Bundle b = getArguments();

        /** Getting the hour of day from bundle */
        mHour = b.getInt("current_hour");

        /** Getting the minute of hour from bundle */
        mMinute = b.getInt("current_minute");

        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                mHour = hourOfDay;
                mMinute = minute;

                /** Creating a bundle object to pass currently set time to the fragment */
                Bundle b = new Bundle();

                /** Adding currently set hour to bundle object */
                b.putInt("current_hour", mHour);

                /** Adding currently set minute to bundle object */
                b.putInt("current_minute", mMinute);

                /** Adding Current time in a string to bundle object */
                b.putString("current_time", "Set Time : " + Integer.toString(mHour) + " : " + Integer.toString(mMinute));

                /** Creating an instance of Message */
                Message m = new Message();

                /** Setting bundle object on the message object m */
                m.setData(b);

                /** Message m is sending using the message handler instantiated in MainActivity class */
                mHandler.sendMessage(m);
            }
        };

        /** Opening the TimePickerDialog window */
        return new TimePickerDialog(getActivity(), listener, mHour, mMinute, true);
    }
}