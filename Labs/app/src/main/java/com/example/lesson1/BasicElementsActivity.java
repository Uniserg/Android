package com.example.lesson1;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;

public class BasicElementsActivity extends CustomActivity {
    TextView textView;
    EditText editText;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.base_elements_activity_title);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void init() {
        textView = findViewById(R.id.be_TextView);
        textView.setText("Set in Java!");
        editText = findViewById(R.id.be_TextEdit);
        listView = findViewById(R.id.be_listView);
        arrayList = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<>(this,
                R.layout.simple_list_item,
                arrayList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.d("omg android", i + ": " + arrayList.get(i));
            textView.setText(arrayList.get(i) + " is learning Android development!");
        });

    }

    // Собственная реализация бинарного поиска (не нужна, так как Collections.binarySearch выдает позицию элемента, если его нет -pos - 1
    private void insertItem(String item) {
        int a = 0;
        int b = arrayList.size();
        int c = 0;

        if (b > 0) {
            while ((b - a) > 1) {
                c = (a + b) / 2;
                int compare = item.compareTo(arrayList.get(c));

                if (compare < 0) {
                    b = c;
                } else if (compare > 0) {
                    a = c;
                } else {
                    throw new RuntimeException("Error: unexpected result");
                }
            }

            if (item.compareTo(arrayList.get(a)) > 0) {
                c = b;
            } else {
                c = a;
            }
        }

        arrayList.add(c, item);
    }

    @SuppressLint("SetTextI18n")
    public void updateTextView(View view) {
        textView.setText(editText.getText().toString() + " is learning Android development!");
        String input = editText.getText().toString();

        int insertPosition = Collections.binarySearch(arrayList, input);
        if (insertPosition < 0) {
            arrayList.add(Math.abs(insertPosition + 1), input);
        }
        arrayAdapter.notifyDataSetChanged();
    }



}
