package com.georgevich.bruno.ibill;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewBillActivity extends AppCompatActivity {

    private EditText dateEditText;
    private EditText nameEditText;
    private EditText valueEditText;
    private Switch repeatSwitch;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bill);
        Calendar newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        dateEditText = (EditText) findViewById(R.id.date_edit_text);
        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        valueEditText = (EditText) findViewById(R.id.value_edit_text);
        repeatSwitch = (Switch) findViewById(R.id.repeat_switch);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateEditText.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void showDatePickerDialog(View view) {
        datePickerDialog.show();
    }

    public void addBill(View view) {
        String date = dateEditText.getText().toString();
        String value = valueEditText.getText().toString();
        String name = nameEditText.getText().toString();
        boolean repeat = repeatSwitch.isChecked();
        if(date.isEmpty() || value.isEmpty() || name.isEmpty()) {
            Toast t = Toast.makeText(getBaseContext(), "Please, Fill all the fields", Toast.LENGTH_LONG);
            t.show();
            return;
        }
        Bundle b = new Bundle();
        b.putSerializable("bill", new Bill(value,name,date,repeat));
        Intent it = getIntent();
        it.putExtras(b);
        setResult(RESULT_OK,it);
        finish();
    }
}
