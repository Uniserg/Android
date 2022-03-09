package com.example.lesson1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ClickerActivity extends CustomActivity {

    Button inc;
    Button dec;
    Button reset;
    TextView scoreView;
    long score = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.clicker_activity_title);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void init() {
        inc = findViewById(R.id.inc);
        dec = findViewById(R.id.dec);
        reset = findViewById(R.id.reset);

        scoreView = findViewById(R.id.score);

        inc.setOnClickListener(view -> scoreView.setText(getString(R.string.score) + ++score));

        dec.setOnClickListener(view -> {
            if (score != 0)
                scoreView.setText(getString(R.string.score) + --score);
        });

        reset.setOnClickListener(view -> {
            scoreView.setText("Score: 0");
            score = 0;
        });
    }
}
