/**
 * Denne klassen inneholder funksjonalitet for å slette innholdet i begge skanner-tekstfeltene
 * og starte på nytt
 */

package com.skanner.org.skanner;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ClearAllAction implements View.OnClickListener {
    private EditText first;
    private EditText second;
    private TextView result;

    public ClearAllAction (EditText first, EditText second, TextView result) {
        this.first = first;
        this.second = second;
        this.result = result;
    }

    @Override
    public void onClick(View w) {
        first.setText("");
        second.setText("");
        result.setTextColor(Color.LTGRAY);
        result.setBackgroundColor(Color.TRANSPARENT);
        result.setText("Klar");
        first.requestFocus();
    }
}
