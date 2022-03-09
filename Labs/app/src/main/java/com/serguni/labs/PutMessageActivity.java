package com.serguni.labs;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class PutMessageActivity extends CustomActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.put_message_activity_title);
    }

    @Override
    protected void init() {
        setContentView(R.layout.put_message_activity);

        Button myButton = findViewById(R.id.button);
        TextView t = findViewById(R.id.textView3);

        myButton.setOnClickListener(viewT -> t.setText(((EditText)findViewById(R.id.editText)).getText()));
    }
}
