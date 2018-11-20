package com.skanner.org.skanner;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CheckEqualityAction implements TextWatcher {
    private EditText first;
    private EditText second;
    private TextView result;
    private MediaPlayer mp;
    Context context;

    public CheckEqualityAction(Context context, EditText first, EditText second, TextView result) {
        this.first = first;
        this.second = second;
        this.result = result;
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable e) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        checkEquality();
    }

    public void checkEquality() {
        if (!second.getText().toString().equals("")) {
//            LogWriter writer = TextFileLogWriter.getInstance();
            LogWriter writer = SqlLogWriter.getInstance();
            writer.setContext(context);

            if (first.getText().toString().equals(second.getText().toString())) {
                writer.appendEntry(first.getText().toString(), second.getText().toString(), false);
//                result.setTextColor(Color.BLACK);
//                result.setText("OK");
//                result.setBackgroundColor(Color.GREEN);
                Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();

                first.requestFocus();

                first.setText("");
                second.setText("");
            } else {
                writer.appendEntry(first.getText().toString(), second.getText().toString(), true);
                result.setTextColor(Color.WHITE);
                result.setText("Feil");
                result.setBackgroundColor(Color.RED);
                playSound();
            }
        }
        else {
            first.requestFocus();
        }
    }

    public void playSound() {
        if (mp == null) {
            mp = MediaPlayer.create(context, R.raw.stop);
        }
        else if (mp.isPlaying()) {
            mp.stop();
            mp.release();
            mp = MediaPlayer.create(context, R.raw.stop);
        }
        else {
            mp.release();
            mp = MediaPlayer.create(context, R.raw.stop);
        }

        mp.start();
    }
}
