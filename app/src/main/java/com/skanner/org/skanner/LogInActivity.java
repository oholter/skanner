package com.skanner.org.skanner;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        final EditText userText = findViewById(R.id.unameText);
        final EditText passwordText = findViewById(R.id.passText);
        final ImageButton startButton = findViewById(R.id.startButton);
        final User user = User.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            userText.setShowSoftInputOnFocus(false);
            passwordText.setShowSoftInputOnFocus(false);
        } else {
            userText.setTextIsSelectable(true);
            passwordText.setTextIsSelectable(true);
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                if (!userText.toString().equals("")) {
                    user.logIn(userText.toString());
                    Intent intent = new Intent(getApplicationContext(), SkanActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}