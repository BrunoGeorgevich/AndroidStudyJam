package com.example.bruno.teste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LinearLayout buttonsLinearLayout;
    TextView labelTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonsLinearLayout = (LinearLayout) findViewById(R.id.buttons_linear_layout);
        labelTextView = (TextView) findViewById(R.id.label_text_view);
        initButtons();
    }

    protected void initButtons() {
        ArrayList<String> strings1 = new ArrayList<String>();
        strings1.add("1");strings1.add("2");strings1.add("3");strings1.add("+");
        buttonsLinearLayout.addView(new DelegateC(this,strings1,labelTextView));
        ArrayList<String> strings2 = new ArrayList<String>();
        strings2.add("4");strings2.add("5");strings2.add("6");strings2.add("-");
        buttonsLinearLayout.addView(new DelegateC(this,strings2,labelTextView));
        ArrayList<String> strings3 = new ArrayList<String>();
        strings3.add("7");strings3.add("8");strings3.add("9");strings3.add("*");
        buttonsLinearLayout.addView(new DelegateC(this,strings3,labelTextView));
        ArrayList<String> strings4 = new ArrayList<String>();
        strings4.add(".");strings4.add("0");strings4.add("/");strings4.add("=");
        buttonsLinearLayout.addView(new DelegateC(this,strings4,labelTextView));
    }

}
