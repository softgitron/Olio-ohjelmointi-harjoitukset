package com.example.finnkino;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (getActivity() instanceof AsyncInterface) {
            String time = String.format("%02d:%02d", hourOfDay, minute);
            Context context = getContext();
            ((AsyncInterface)context).onAsyncComplete(AsyncInterface.AsyncType.TIME, AsyncInterface.AsyncStatus.OK, time);
        } else {
            System.out.println("Time fragment received context without Async interface!\nThat should not happen");
        }
        /* EditText timeText = getActivity().findViewById(R.id.startTimeView);
        timeText.setText(String.format("%02d:%02d", hourOfDay, minute)); */
    }
}
