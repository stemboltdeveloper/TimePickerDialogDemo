package com.stembo.android.timepickerdialogdemo;


import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.view.ViewDebug;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by myxom on 16/02/16.
 */
public class EditTextTimePickerDialogFragment extends DialogFragment
                                              implements TimePickerDialog.OnTimeSetListener{

    Handler mHandler;
    int mHour;
    int mMinute;

    TextView mResultText;

    public EditTextTimePickerDialogFragment(Handler h){
        mHandler=h;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        Bundle b = getArguments();
        mHour = b.getInt("current_hour");
        mMinute = b.getInt("current_minute");
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                Bundle b = new Bundle();
                b.putInt("current_hour", mHour);
                b.putInt("current_minute", mMinute);
                b.putString("current_time", Integer.toString(mHour)+":"+Integer.toString(mMinute));
                Message msg = new Message();
                msg.setData(b);
                mHandler.sendMessage(msg);
            }
        };

        return new TimePickerDialog(getActivity(), listener, mHour, mMinute, true);
    }//oncreatedialog

    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        String time = Integer.toString(mHour)+":"+Integer.toString(mMinute);
        mResultText.setText(time);
    }
}
