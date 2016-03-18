package com.georgevich.bruno.ibill;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewBillActivity extends AppCompatActivity {

    private TextView showDateTextView;
    private EditText nameEditText;
    private EditText valueEditText;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bill);
        Calendar newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        showDateTextView = (TextView) findViewById(R.id.show_date_text_view);
        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        valueEditText = (EditText) findViewById(R.id.value_edit_text);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                showDateTextView.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void showDatePickerDialog(View view) {
        datePickerDialog.show();
    }

    public void addBill(View view) {
        String date = showDateTextView.getText().toString();
        String value = valueEditText.getText().toString();
        String name = nameEditText.getText().toString();
        if(date.isEmpty() || value.isEmpty() || name.isEmpty()) {
            Toast t = Toast.makeText(getBaseContext(), "Please, Fill all the fields", Toast.LENGTH_LONG);
            t.show();
            return;
        }
        Bundle b = new Bundle();
        b.putSerializable("bill", new Bill(value,name,date));
        Intent it = getIntent();
        it.putExtras(b);
        setResult(RESULT_OK,it);
        finish();
    }
}
