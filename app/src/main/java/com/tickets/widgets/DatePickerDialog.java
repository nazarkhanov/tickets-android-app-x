package com.tickets.widgets;

import android.content.Context;
import android.widget.DatePicker;

import androidx.annotation.NonNull;

import com.tickets.R;

import java.util.Calendar;

public class DatePickerDialog extends android.app.DatePickerDialog {

    final private Calendar calendar;

    public DatePickerDialog(@NonNull Context context) {
        super(context, R.style.ThemeDatePickerDialog);

        calendar = Calendar.getInstance();
    }

    public void setOnDateSetListener(OnDateSetListener listener) {
        super.setOnDateSetListener(new android.app.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                listener.onDateSet(calendar);
            }
        });
    }

    public interface OnDateSetListener {
        void onDateSet(Calendar calendar);
    }
}