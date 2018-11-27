/**
 * Denne klassen implementerer funksjonalitetn til knappen i admin-bildet
 * Den henter informasjon fra databasen ved å bruke informasjon i tekstfeltene
 *
 */

package com.skanner.org.skanner;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.skanner.org.skanner.SqlLogWriter;

class SqlResultListener implements View.OnClickListener {
    private EditText fraDato;
    private EditText tilDato;
    private CheckBox feilBox;
    private TextView result;
    private Context context;

    public SqlResultListener(EditText fraDato, EditText tilDato, CheckBox feilBox, TextView sqlResultText, Context context) {
        this.fraDato = fraDato;
        this.tilDato = tilDato;
        this.feilBox = feilBox;
        this.result = sqlResultText;
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        SqlLogWriter writer = SqlLogWriter.getInstance();
        writer.setContext(context);
        String fra = fraDato.getText().toString();
        String til = tilDato.getText().toString();
        Boolean feil = feilBox.isChecked() ? true : false;

        if (!fra.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
            result.setText("ugyldig dato i fra-feltet");
        }
        else if (!til.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
            result.setText("ugyldig dato i til-feltet");

        }
        else {
            String queryResult = writer.getResults(fra, til, feil);
            result.setText(queryResult);
        }
    }
}
