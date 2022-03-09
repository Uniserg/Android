package com.example.lesson1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class CalculatorActivity extends CustomActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.calculator_activity_title);
    }

    @Override
    protected void init() {
        setContentView(R.layout.calculator_activity);

        EditText editText = findViewById(R.id.editField);
        TextView textView = findViewById(R.id.calcResult);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable) {
                String exp = editable.toString();
                try {

                    double res = eval(exp);

                    if (res % 1 == 0)
                        textView.setText(Integer.toString(((int) res)));
                    else
                        textView.setText(Double.toString(res));
                } catch (Exception e) {
                    textView.setText("");
                }
            }
        });

        int[] signs = {R.id.num0, R.id.num1,R.id.num2,
                R.id.num3,R.id.num4,R.id.num5,
                R.id.num6,R.id.num7,R.id.num8,R.id.num9,
                R.id.comma, R.id.div, R.id.mul, R.id.minus, R.id.perc,R.id.plus
        };

        for (int id:signs) {
            Button btn = findViewById(id);
            btn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View view) {
                    editText.setText(editText.getText().toString()+ btn.getText());
                    editText.setSelection(editText.getText().length());
                }
            });
        }

        Button C = findViewById(R.id.C);
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });

        Button del = findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int last = editText.getText().length() - 1;
                if (last >= 0) {
                    editText.setText(editText.getText().delete(last, last + 1));
                    editText.setSelection(editText.getText().length());

                }
            }
        });

        Button eq = findViewById(R.id.eq);
        eq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(textView.getText().toString());
                editText.setSelection(editText.getText().length());
            }
        });



//        Button percent = findViewById(R.id.perc);
//        percent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                editText.setText(editText.getText());
//            }
//        });

    }


    private static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);

                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation
                if (eat('%')) x = x / 100;

                return x;
            }
        }.parse();
    }

}
