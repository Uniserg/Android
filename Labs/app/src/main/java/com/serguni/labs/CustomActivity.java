package com.serguni.labs;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class CustomActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        initMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getIntent().getIntExtra("contentLayoutId", R.layout.main_activity));
        init();
    }

    public void onClickExit(MenuItem view) {
        finish(); // завершает текущий Activity
        // finishAffinity(); // снимает со стека все Activity и закрывает приложение
    }

    // Обязательно инициализировать View-элементы из xml-файла после загрузки
    protected abstract void init();
    protected void initMenu(Menu menu) {
    };

}
