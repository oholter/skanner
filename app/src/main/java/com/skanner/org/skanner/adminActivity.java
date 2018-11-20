package com.skanner.org.skanner;

import android.app.DatePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;

public class adminActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        EditText tilDato = findViewById(R.id.tilDatoText);
        EditText fraDato = findViewById(R.id.fraDatoText);
        TextView sqlResultText = findViewById(R.id.sqlResultText);
        CheckBox feilBox = findViewById(R.id.feilskanningBox);
        Button finnButton = findViewById(R.id.finnButton);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tilDato.setShowSoftInputOnFocus(false);
            fraDato.setShowSoftInputOnFocus(false);
        } else {
            tilDato.setTextIsSelectable(true);
            fraDato.setTextIsSelectable(true);
        }

        fraDato.setOnClickListener(new DateDialog(fraDato,this));
        tilDato.setOnClickListener(new DateDialog(tilDato, this));
        finnButton.setOnClickListener(new SqlResultListener(fraDato, tilDato, feilBox, sqlResultText, this.getApplicationContext()));


        //writer.clearLog();


    }
}
