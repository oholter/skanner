/**
 * Denne klassen kobler funksjonalitet til elementene i login-vinduet
 */

package com.skanner.org.skanner;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.text.Editable;


public class LogInActivity extends AppCompatActivity {

    EditText userText;
    EditText passwordText;
    ImageButton startButton;
    User user;


    private void logIn() {
        if (!userText.getText().toString().equals("")) {
            user.logIn(userText.getText().toString());

            if (user.getUserName().equals("2222")) {
                Intent intent = new Intent(getApplicationContext(), adminActivity.class);
                startActivity(intent);
            } else if (user.getUserName().startsWith("B075")) {
                Intent intent = new Intent(getApplicationContext(), SkanActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), user.getUserName()
                        + " er ikke gyldig brukernavn", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        passwordText = findViewById(R.id.passText);
        startButton = findViewById(R.id.startButton);
        userText = findViewById(R.id.unameText);
        user = User.getInstance();

        // Ikke bruke keyboard for disse tekstfeltene
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            userText.setShowSoftInputOnFocus(false);
            passwordText.setShowSoftInputOnFocus(false);
        } else {
            userText.setTextIsSelectable(true);
            passwordText.setTextIsSelectable(true);
        }

        // forsøker automatisk å logge inn etter at bruker har skannet passordet
        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable e) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                logIn();
            }
        });


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                logIn();
            }
        });
    }
}