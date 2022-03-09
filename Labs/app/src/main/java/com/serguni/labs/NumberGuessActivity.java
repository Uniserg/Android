package com.serguni.labs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Random;

public class NumberGuessActivity extends CustomActivity {

    Random random;

    TextView tvInfo;
    TextView levelInfo;
    TextView triesInfo;
    EditText etInput;
    Button bControl;

    int min;
    int max;
    int trueNum;
    int count;

    @Override
    protected void initMenu(Menu menu) {
        super.initMenu(menu);

        menu.setGroupVisible(R.id.menu_group_for_number_guess, true);

        int [] itemsIds = {R.id.level1, R.id.level2, R.id.level3};

        for (int itemId:itemsIds) {
            MenuItem item = menu.findItem(itemId);

            item.setOnMenuItemClickListener(menuItem -> {
                startNewGame(itemId);
                return false;
            });
        }
    }

    @Override
    protected void init() {

        random = new Random();

        triesInfo = findViewById(R.id.triesInfo);

        tvInfo = findViewById(R.id.tvInfo);
        tvInfo.setText(R.string.intro);

        etInput = findViewById(R.id.etInput);
        bControl = findViewById(R.id.bControl);
        levelInfo = findViewById(R.id.levelInfo);

        bControl.setVisibility(View.GONE);
        etInput.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle(R.string.number_guess_activity_title);
    }

    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    private void startNewGame (int levelId) {
        count = 0;

        tvInfo.setText("");
        bControl.setVisibility(View.VISIBLE);
        etInput.setVisibility(View.VISIBLE);
        triesInfo.setText(getResources().getString(R.string.tries_info) + count);

        switch (levelId) {
            case R.id.level1:
                initLevelValues(0,20);
                levelInfo.setText(R.string.level1);
                break;
            case R.id.level2:
                initLevelValues(0,100);
                levelInfo.setText(R.string.level2);
                break;
            case R.id.level3:
                initLevelValues(0,1000);
                levelInfo.setText(R.string.level3);
        }


        bControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTry();
            }
        });

    }

    private int randInt() {
         return random.nextInt((max - min) + 1) + min;
    }

    private void initLevelValues(int min, int max) {
        this.min = min;
        this.max = max;
        trueNum = randInt();
    }

    @SuppressLint("SetTextI18n")
    private void makeTry() {
        try {
            int inputNum = Integer.parseInt(etInput.getText().toString());

            if (inputNum > max || inputNum < min) {
                tvInfo.setText(getResources().getString(R.string.wront_interval));
            } else if  (inputNum > trueNum) {
                tvInfo.setText(getResources().getString(R.string.ahead));
            } else if (inputNum < trueNum) {
                tvInfo.setText(getResources().getString(R.string.behind));
            } else {
                endGame();
            }
        } catch (Exception e) {
            tvInfo.setText(getResources().getString(R.string.error));
        }

        triesInfo.setText(getResources().getString(R.string.tries_info) + ++count);
        etInput.setText("");
    }

    private void endGame() {
        tvInfo.setText(getResources().getString(R.string.hit));
        bControl.setOnClickListener(null);
        levelInfo.setText("");
        bControl.setVisibility(View.GONE);
        etInput.setVisibility(View.GONE);
    }

}
