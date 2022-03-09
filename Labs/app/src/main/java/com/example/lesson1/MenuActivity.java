package com.example.lesson1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;


public class MenuActivity extends CustomActivity {

    TextView tv;
    CheckBox chb;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.mymenu, menu);
        menu.setGroupVisible(R.id.for_menus, true);

//            menu.add(0, 1, 0, "add");
//            menu.add(0, 2, 0, "edit");
//            menu.add(0, 3, 3, "delete");
//            menu.add(1, 4, 1, "copy");
//            menu.add(1, 5, 2, "paste");
//            menu.add(1, 6, 4, "exit");


        return super.onCreateOptionsMenu(menu);
    }

    // обновление меню
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.setGroupVisible(R.id.for_menus, true);
        menu.setGroupVisible(R.id.ext_for_menus, chb.isChecked());
        return super.onPrepareOptionsMenu(menu);
    }

    // обработка нажатий
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();

        // Выведем в TextView информацию о нажатом пункте меню
        sb.append("Item Menu");
        sb.append("\r\n groupId: " + item.getGroupId());
        sb.append("\r\n itemId: " + item.getItemId());
        sb.append("\r\n order: " + item.getOrder());
        sb.append("\r\n title: " + item.getTitle());
        tv.setText(sb.toString());

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onClickExit(MenuItem view) {
        finish(); // завершает текущий Activity
        // finishAffinity(); // снимает со стека все Activity и закрывает приложение
    }

    @Override
    protected void init() {
        tv = findViewById(R.id.textView);
        chb = findViewById(R.id.chbExtMenu);
    }
}
