package com.example.bruno.teste;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by bruno on 10/03/16.
 */
public class DelegateC extends LinearLayout {

    TextView labelTextView;

    static private double firstNum;
    static private double secondNum;
    static private ArrayList<Button> operators = new ArrayList<>();
    static private ArrayList<Button> numbers = new ArrayList<>();
    static private Button equalBtn;
    static private Operators currentOperator;
    static private boolean equalCanBeEnabled = false;
    static private Toast errorToast;

    protected enum Operators {
        Nenhum, Soma, Subtracao, Multiplicacao, Divisao;
    }

    public DelegateC(Context context, ArrayList<String> strings, TextView labelTextView) {
        super(context);
        if(errorToast == null) errorToast = Toast.makeText(context,"Cannot have more of 1 dot per number",Toast.LENGTH_LONG);
        if (equalBtn == null) equalBtn = new Button(context);
        if(this.labelTextView == null) {
            this.labelTextView = labelTextView;
            this.labelTextView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    TextView tv = (TextView) v;
                    tv.setText("");
                    setOperatorsDisabled();
                    if(equalBtn.isEnabled()) equalBtn.setEnabled(false);
                    equalCanBeEnabled = false;
                    return false;
                }
            });
        }
        setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1));
        for (int i = 0; i < strings.size(); i++) {
            Button btn = new Button(context);
            btn.setTextSize(25);
            btn.setBackgroundColor(R.color.transparent);
            btn.setText(strings.get(i));

            if (btn.getText() == "=")
                equalBtn = btn;
            else if (btn.getText() == "+")
                operators.add(btn);
            else if (btn.getText() == "-")
                operators.add(btn);
            else if (btn.getText() == "*")
                operators.add(btn);
            else if (btn.getText() == "/")
                operators.add(btn);
            else
                numbers.add(btn);

            btn.setOnClickListener(new ButtonOnClickListener());
            btn.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1));
            addView(btn);
        }
        setOperatorsDisabled();
        if(equalBtn.isEnabled()) equalBtn.setEnabled(false);
        equalCanBeEnabled = false;
    }

    private void setOperatorsDisabled() {
        for (int i = 0; i < operators.size(); i++) {
            operators.get(i).setEnabled(false);
        }
        equalCanBeEnabled = true;
    }

    private void setOperatorsEnabled() {
        if (!equalCanBeEnabled)
            for (int i = 0; i < operators.size(); i++) {
                operators.get(i).setEnabled(true);
            }
        else
            setEqualBtnEnabled();
    }

    private void setEqualBtnDisabled() {
        equalBtn.setEnabled(false);
        equalCanBeEnabled = false;
    }

    private void setEqualBtnEnabled() {
        equalBtn.setEnabled(true);
    }

    private class ButtonOnClickListener implements OnClickListener {

        public ButtonOnClickListener() {
            firstNum = 0;
            secondNum = 0;
            currentOperator = Operators.Nenhum;
        }

        private int countOfOcurrences(String str, char c) {
            int counter = 0;
            for( int i=0; i<str.length(); i++ ) if( str.charAt(i) == c ) counter++;
            return counter;
        }

        private boolean validateDot(String num) {
            if(countOfOcurrences(num,'.') >= 2) {
                errorToast.show();
                labelTextView.setText("");
                return false;
            }
            return true;
        }

        public void onClick(View v) {
            Button b = (Button) v;
            if (b.getText() == "=") {
                String secondNumberString = (String) (labelTextView.getText());
                secondNumberString = secondNumberString.split("\n")[2];
                if(validateDot(secondNumberString)) {
                    secondNum = Double.valueOf(secondNumberString);
                    labelTextView.setText(String.valueOf(equalOp()));
                    setEqualBtnDisabled();
                    setOperatorsEnabled();
                }
            } else if (b.getText() == "+") {
                if(validateDot((String) labelTextView.getText())) {
                    firstNum = Double.valueOf((String) labelTextView.getText());
                    labelTextView.setText(labelTextView.getText() + "\n+\n");
                    currentOperator = Operators.Soma;
                    setOperatorsDisabled();
                }
            } else if (b.getText() == "-") {
                if(validateDot((String) labelTextView.getText())) {
                    firstNum = Double.valueOf((String) labelTextView.getText());
                    labelTextView.setText(labelTextView.getText() + "\n-\n");
                    currentOperator = Operators.Subtracao;
                    setOperatorsDisabled();
                }
            } else if (b.getText() == "*") {
                if(validateDot((String) labelTextView.getText())) {
                    firstNum = Double.valueOf((String) labelTextView.getText());
                    labelTextView.setText(labelTextView.getText() + "\n*\n");
                    currentOperator = Operators.Multiplicacao;
                    setOperatorsDisabled();
                }
            } else if (b.getText() == "/") {
                if(validateDot((String) labelTextView.getText())) {
                    firstNum = Double.valueOf((String) labelTextView.getText());
                    labelTextView.setText(labelTextView.getText() + "\n/\n");
                    currentOperator = Operators.Divisao;
                    setOperatorsDisabled();
                }
            } else {
                labelTextView.setText(labelTextView.getText() + (String) b.getText());
                setOperatorsEnabled();
            }
        }

        protected double equalOp() {
            double result = 0;
            switch (currentOperator) {
                case Soma:
                    result = (firstNum + secondNum);
                    break;
                case Subtracao:
                    result = (firstNum - secondNum);
                    break;
                case Divisao:
                    result = (firstNum / secondNum);
                    break;
                case Multiplicacao:
                    result = (firstNum * secondNum);
                    break;
                default:
                    System.out.println("ENTROU NO DEFAULT : " + currentOperator);
            }
            return result;
        }
    }
}
