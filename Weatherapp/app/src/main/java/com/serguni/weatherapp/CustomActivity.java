package com.serguni.weatherapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class CustomActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        initMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getIntent().getIntExtra("contentLayoutId", R.layout.activity_main));
        init();
    }

    public void onClickExit(MenuItem view) {
        finish(); // завершает текущий Activity
        // finishAffinity(); // снимает со стека все Activity и закрывает приложение
    }


    protected abstract void init();
    protected void initMenu(Menu menu) {
    }

}
