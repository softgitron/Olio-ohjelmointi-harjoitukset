package com.example.finnkino;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (getActivity() instanceof AsyncInterface) {
            String date = String.format("%02d.%02d.%04d", day, month + 1, year);
            Context context = getContext();
            ((AsyncInterface)context).onAsyncComplete(AsyncInterface.AsyncType.DATE, AsyncInterface.AsyncStatus.OK, date);
        } else {
            System.out.println("Date fragment received context without Async interface!\nThat should not happen");
        }
        /* EditText dateText = getActivity().findViewById(R.id.dateText);
        dateText.setText(String.format("%02d.%02d.%04d", day, month + 1, year)); */
    }
}
