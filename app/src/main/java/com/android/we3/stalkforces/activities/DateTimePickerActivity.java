package com.android.we3.stalkforces.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.we3.stalkforces.R;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateTimePickerActivity extends AppCompatActivity {
    public static final String REMINDER_DATE_ADDED = "new_reminder_date";
    public static final String REMINDER_TIME_ADDED = "new_reminder_time";
    TextView txtDate;
    TextView txtTime;
    String contestNameTitle;
    TextView contestTitleTextView;
    Button setReminder;
    String contestTimeTitle;
    TextView contestTimeTitleTextView;

    private int year;
    private int month;
    private int day;
    private int OriginalHour;
    private String cId;
    private int hour;
    private int minute;
    String contestId;
    static final int DATE_DIALOG_ID = 999;
    static final int TIME_DIALOG_ID = 998;
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_picker);

        contestNameTitle=getIntent().getStringExtra("contestName");
        contestTimeTitle=getIntent().getStringExtra("contestTime");
        contestId=getIntent().getStringExtra("contestId");
        contestTitleTextView=(TextView)findViewById(R.id.contestDateTimeTitleTextView);
        contestTimeTitleTextView=(TextView)findViewById(R.id.contestTimeDescTextView);
        contestTimeTitleTextView.setText(contestTimeTitle);
        contestTitleTextView.setText(contestNameTitle);
        setReminder=(Button)findViewById(R.id.setReminderButton);
        setCurrentDateAndTimeOnView();
    }
    // sets current date and reminder when the activity starts
    public void setCurrentDateAndTimeOnView() {
        txtDate = (TextView)findViewById(R.id.dateTextView);
        txtTime = (TextView)findViewById(R.id.timeTextView);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        hour = c.get(Calendar.HOUR_OF_DAY);
        OriginalHour=hour;
        minute = c.get(Calendar.MINUTE);
        String a=AMorPM(hour);
        hour=FormatTheHour(hour);
        txtTime.setText(new StringBuilder().append(pad(hour))
                .append(":").append(pad(minute)).append(" ").append(a));

        txtDate.setText(new StringBuilder()
                .append(pad(day)).append("-").append(pad(month+1)).append("-")
                .append(year).append(" "));
    }
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            txtDate.setText(new StringBuilder().append(pad(day))
                    .append("-").append(pad(month+1)).append("-").append(year)
                    .append(" "));
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;
            OriginalHour=hour;
            String amPm=AMorPM(hour);
            hour=FormatTheHour(hour);
            txtTime.setText(new StringBuilder().append(pad(hour))
                    .append(":").append(pad(minute)).append(" ").append(amPm));
        }
    };
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        datePickerListener, year, month,day);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,
                        timePickerListener, hour, minute,false);
        }
        return null;
    }
    // function to pad and add zero if the digit is a single digit
    private String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public void editTextDate(View view){
        showDialog(DATE_DIALOG_ID);
    }

    public void editTextTime(View view){
        showDialog(TIME_DIALOG_ID);
    }
    // implementatin of onclick of set reminder button
    public void btnAddReminder(View view) {

        long timeAtButtonClick=System.currentTimeMillis();
        Log.i("MSG",String.valueOf(timeAtButtonClick));
        long timeSecondsAtMillis= 0;
        try {
            timeSecondsAtMillis = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(pad(month+1)+"/"+pad(day)+"/"+year+" "+pad(OriginalHour)+":"+pad(minute)+":00").getTime()/1000;
            Log.i("MSG0",String.valueOf(timeSecondsAtMillis));
            Date d=new Date(timeSecondsAtMillis*1000);
            Date d2=new Date(timeAtButtonClick);
            Log.i("MSG*",String.valueOf(d2));
            Log.i("MSG2",String.valueOf(d));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent resultIntent = new Intent();
        // if date or time is empty then the result is cancelled
        if (TextUtils.isEmpty(txtDate.getText())|| TextUtils.isEmpty(txtTime.getText())) {

            setResult(RESULT_CANCELED, resultIntent);

        } else {
            // else all the information is passed to reminder list activity to set reminder
                String date = txtDate.getText().toString();
                String time = txtTime.getText().toString();
                resultIntent.putExtra(REMINDER_DATE_ADDED, date);
                resultIntent.putExtra(REMINDER_TIME_ADDED, time);
                resultIntent.putExtra("contestId", contestId);
                cId = txtTime.getText().toString();
                resultIntent.putExtra("contestNameTitle", contestNameTitle);
                resultIntent.putExtra("contestTimeTitle", contestTimeTitle);
                resultIntent.putExtra("time", cId);
                String a = Long.toString(timeSecondsAtMillis);
                String b = Long.toString(timeAtButtonClick);
                Log.i("kittu", a + " " + b);
                resultIntent.putExtra("timeMillis", a);
                resultIntent.putExtra("timeClick", b);
                setResult(RESULT_OK, resultIntent);

        }
        finish();
    }


    private String DisplayTheMonthInCharacters(int passedMonth){
        switch(passedMonth){
            case 0:
                return "Jan";
            case 1:
                return"Feb";
            case 2:
                return"Mar";
            case 3:
                return"Apr";
            case 4:
                return"May";
            case 5:
                return"Jun";
            case 6:
                return"Jul";
            case 7:
                return"Aug";
            case 8:
                return"Sept";
            case 9:
                return"Oct";
            case 10:
                return"Nov";
            case 11:
                return"Dec";

        }
        return "";
    }

    private int FormatTheHour(int passedHour){
        if (passedHour > 12){ passedHour -= 12; }
        return passedHour;
    }

    // function to concert time in AM or PM
    private String AMorPM(int passedHour){
        if (passedHour > 12){ return "PM"; }
        else{ return "AM"; }
    }

    //on Destroy
    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

}
