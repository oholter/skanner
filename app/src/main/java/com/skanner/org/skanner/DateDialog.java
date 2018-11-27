/**
 * Denne klassen inneholder funksjonaliteten med dialogboksene for valg av dato
 * i admin-bildet
 */

package com.skanner.org.skanner;

import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class DateDialog implements View.OnClickListener {

    private Context context;
    private EditText textField;


    public DateDialog(EditText textField, Context context) {
        this.textField = textField;
        this.context = context;
    }

    @Override
    public void onClick(View w) {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        android.app.DatePickerDialog picker = new android.app.DatePickerDialog(context,
                new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String monthString;
                        String dayString;
                        month++;
                        if (month < 10) { // ensure proper date format with 2 digits
                            monthString = "0"+month;
                        }
                        else {
                            monthString = ""+month;
                        }

                        if (dayOfMonth < 10) {
                            dayString = "0"+dayOfMonth;
                        }
                        else {
                            dayString = ""+dayOfMonth;
                        }
                        textField.setText(year + "-" + monthString + "-" + dayString);
                    }
                }, cldr.get(Calendar.YEAR), cldr.get(Calendar.MONTH), cldr.get(Calendar.DAY_OF_MONTH));
        picker.show();
    }
}
