package com.example.bruno.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected int numCoffees = 0;
    protected TextView quantityTextView;
    protected CheckBox whippedCreamCheckBox;
    protected CheckBox chocolateCheckBox;
    protected EditText nameTextEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        whippedCreamCheckBox = (CheckBox) findViewById(R.id.whiped_cream_checkbox);
        chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        nameTextEdit = (EditText) findViewById(R.id.name_edit_text);
    }

    protected void setQuantity(int num) {
        quantityTextView.setText(String.valueOf(num));
    }

    protected String calculateTotal(int pricePerCoffee) {
        int additionals = 0;
        additionals += (whippedCreamCheckBox.isChecked()) ? 1 : 0;
        additionals += (chocolateCheckBox.isChecked()) ? 2 : 0;
        return String.valueOf((pricePerCoffee + additionals )* numCoffees);
    }

    public void increment(View view) {
        numCoffees += 1;
        setQuantity(numCoffees);
    }

    public void decrement(View view) {
        numCoffees = (numCoffees == 0 ? numCoffees : numCoffees - 1);
        setQuantity(numCoffees);
    }

    public void order(View view) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{});
        i.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + nameTextEdit.getText());
        i.putExtra(Intent.EXTRA_TEXT   ,
                "Name: " + nameTextEdit.getText() +
                        "\nAdd whipped cream? " + String.valueOf(whippedCreamCheckBox.isChecked()) +
                        "\nAdd chocolate? " + String.valueOf(chocolateCheckBox.isChecked()) +
                        "\nQuantity: " + String.valueOf(numCoffees) +
                        "\nTotal: " + calculateTotal(5) +
                        " R$\nThank you!"
        );
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}