package com.georgevich.bruno.ibill;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class DetailedBillActivity extends AppCompatActivity {

    TextView detailedName;
    TextView detailedValue;
    CalendarView detailedDate;
    ImageView detailedRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_bill);
        Intent it = getIntent();
        Bundle bd = it.getExtras();
        Bill b = (Bill) bd.getSerializable("bill");
        initVariables();
        detailedName.setText(b.geName());
        detailedValue.setText(b.getValue());
        detailedRepeat.setImageResource(b.isEveryMonth() ? R.drawable.repeat : R.drawable.norepeat);
        initCalendar(b.getDueDate());
    }

    private void initCalendar(String date) {
        String parts[] = date.split("/");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        long milliTime = calendar.getTimeInMillis();
        detailedDate.setDate(milliTime, true, true);
        long a = detailedDate.getDate();
        detailedDate.setMaxDate(a);
    }

    private void initVariables() {
        detailedName = (TextView) findViewById(R.id.detailed_name_text_view);
        detailedDate = (CalendarView) findViewById(R.id.detailed_date_calendar);
        detailedValue = (TextView) findViewById(R.id.detailed_value_text_view);
        detailedRepeat = (ImageView) findViewById(R.id.detailed_repeat_image_view);
    }
}
