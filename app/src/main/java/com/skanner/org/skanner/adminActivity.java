package com.skanner.org.skanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class adminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        TextView text= findViewById(R.id.adminText);
        SqlLogWriter writer = SqlLogWriter.getInstance();
        writer.setContext(getApplicationContext());
        //writer.clearLog();
        String line = writer.getLastEntry();
        text.setText(line);
    }
}
