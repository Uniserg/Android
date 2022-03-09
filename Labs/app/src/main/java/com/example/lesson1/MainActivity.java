package com.example.lesson1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends CustomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    @Override
    protected void init() {
    }

    private Intent getIntentWithContentLayout(int contentLayoutId, Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("contentLayoutId", contentLayoutId);
        return intent;
    }

    public void displayMenus(View view) {
        startActivity(getIntentWithContentLayout(R.layout.menus_activity, MenuActivity.class));
    }

    public void displayCalculator(View view) {
        startActivity(getIntentWithContentLayout(R.layout.calculator_activity, CalculatorActivity.class));
    }

    public void displayClicker(View view) {
        startActivity(getIntentWithContentLayout(R.layout.clicker_activity, ClickerActivity.class));
    }

    public void displayPutMessage(View view) {
        startActivity(getIntentWithContentLayout(R.layout.put_message_activity, PutMessageActivity.class));
    }

    public void displayNumberGuess(View view) {
        startActivity(getIntentWithContentLayout(R.layout.number_guess_activity, NumberGuessActivity.class));
    }

    public void displayBasicElements(View view) {
        startActivity(getIntentWithContentLayout(R.layout.basic_elements_activity, BasicElementsActivity.class));
    }
}


// Обратиться к ресурсу по имени ???
//    int getResId(String ResName, String className, Context ctx) {
//        try {
//            return ctx.getResources().getIdentifier(ResName, className, ctx.getPackageName());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return -1;
//        }
//    }