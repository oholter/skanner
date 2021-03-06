/**
 * Denne klassen gir funksjonlitet til elementene i Skan-vinduet
 */
package com.skanner.org.skanner;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.skanner.org.skanner.CheckEqualityAction;
import com.skanner.org.skanner.ClearAllAction;
import com.skanner.org.skanner.R;

public class SkanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText topText = findViewById(R.id.topScanField);
        EditText bottomText = findViewById(R.id.bottomScanField);
        TextView resultText = findViewById(R.id.resultView);

        // Ikke bruke keyboard for disse tekstfeltene
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            topText.setShowSoftInputOnFocus(false);
            bottomText.setShowSoftInputOnFocus(false);
        }
        else {
            topText.setTextIsSelectable(true);
            bottomText.setTextIsSelectable(true);
        }

        bottomText.addTextChangedListener(new CheckEqualityAction(this, topText, bottomText, resultText));

        ImageButton startPaaNyttButton = findViewById(R.id.startButton);
        startPaaNyttButton.setOnClickListener(new ClearAllAction(topText, bottomText, resultText));
    }
}
