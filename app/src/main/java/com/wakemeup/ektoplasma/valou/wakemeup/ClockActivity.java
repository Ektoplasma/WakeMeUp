package com.wakemeup.ektoplasma.valou.wakemeup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

/**
 * Created by Valentin on 24/07/2016.
 */
public class ClockActivity extends Fragment {

    public static TextView AlarmTextView;
    public static TimePicker alarmTimePicker;

    public void setAlarmText(String alarmText)
    {
        AlarmTextView.setText(alarmText);
    }

    public int GetHours ()
    {
        return alarmTimePicker.getCurrentHour();
    }

    public int GetMinutes ()
    {
        return alarmTimePicker.getCurrentMinute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        AlarmTextView = (TextView) view.findViewById(R.id.AlarmText);
        AlarmTextView.setText("Bonjour");
        alarmTimePicker = (TimePicker) view.findViewById(R.id.ClockAlarmPicker);
        alarmTimePicker.is24HourView();
        //ToggleButton SetAlarmButton = (ToggleButton) getView().findViewById(R.id.SetAlarmButton);
        return view;
    }
}
