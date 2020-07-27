package com.android.we3.stalkforces.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.adapters.ReminderListAdapter;
import com.android.we3.stalkforces.models.Reminder;
import com.android.we3.stalkforces.models.ReminderViewModel;
import com.android.we3.stalkforces.receivers.Notifier;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReminderListActivity extends AppCompatActivity implements ReminderListAdapter.OnDeleteClickListener {

    private static final int NEW_REMINDER_ACTIVITY_REQUEST_CODE = 1;
    Long timeSecondsAtMillis;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ReminderListAdapter reminderListAdapter;
    private List<Reminder> reminderArrayList;
    private ReminderViewModel reminderViewModel;
    String contestNameTitle;
    String contestTimeTitle;
    String date;
    String time;
    String contestId;
    private TextView contestNameReminder,contestDateTimeReminder;
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);
        contestNameTitle = getIntent().getStringExtra("contestName");
        contestTimeTitle=getIntent().getStringExtra("contestTime");
        contestId=getIntent().getStringExtra("contestId");
        recyclerView = (RecyclerView)findViewById(R.id.reminderRecyclerView);
        layoutManager = new LinearLayoutManager(ReminderListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
       reminderArrayList = new ArrayList<>();
        createNotificationChannel();
        reminderListAdapter = new ReminderListAdapter(reminderArrayList,this);
        recyclerView.setAdapter(reminderListAdapter);
        contestNameReminder=(TextView)findViewById(R.id.contestTitleReminderTextView);
        contestDateTimeReminder=(TextView)findViewById(R.id.dateTimeReminderTextView);
        contestNameReminder.setText(contestNameTitle);
        contestDateTimeReminder.setText(contestTimeTitle);
        reminderArrayList.clear();
        reminderViewModel = new ViewModelProvider(this).get(ReminderViewModel.class);



        reminderViewModel.getAllReminders(contestId).observe(this, new Observer<List<Reminder>>() {

            @Override

            public void onChanged(@Nullable List<Reminder> reminders) {

                reminderListAdapter.setReminders(reminders);
                reminderArrayList=reminders;

            }

        });
    }
    //To delete a previously set reminder
    @Override
    public void onDeleteClick(int position)
    {
        Reminder reminder=reminderArrayList.get(position);
        reminderViewModel.delete(reminder);
        Intent intent2 = new Intent(ReminderListActivity.this, Notifier.class);
        Long deleteTime=Long.parseLong(reminder.getUnixTime());
        Log.i("kitkit",Long.toString(deleteTime)+" ");

        //cancelling the pending intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderListActivity.this, Integer.parseInt(Long.toString((deleteTime)%100007)+getIntent().getStringExtra("contestId")), intent2, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(getApplicationContext(),"Reminder Deleted!",Toast.LENGTH_SHORT).show();
    }
    // menu shown
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_reminder, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            //to add a single reminder
            case R.id.addReminder:
                Intent intent=new Intent(ReminderListActivity.this,DateTimePickerActivity.class);
                intent.putExtra("contestName",contestNameTitle);
                intent.putExtra("contestTime",contestTimeTitle);
                intent.putExtra("contestId",contestId);
                Log.i("MSG",contestNameTitle+" "+contestTimeTitle+" "+contestId);
                startActivityForResult(intent, NEW_REMINDER_ACTIVITY_REQUEST_CODE);
                return true;
                // to delete all reminders
            case R.id.deleteAllReminders:
                if(reminderArrayList.size()==0)
                {
                    Toast.makeText(getApplicationContext(),"No Reminders!",Toast.LENGTH_SHORT).show();
                    return true;
                }
                for(int i=0;i<reminderArrayList.size();i++)
                {
                    Reminder reminderIt=reminderArrayList.get(i);

                    Intent intent3 = new Intent(ReminderListActivity.this, Notifier.class);
                    Long deleteTimeIt=Long.parseLong(reminderIt.getUnixTime());
                    Log.i("kitkat",Long.toString(deleteTimeIt)+" ");
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderListActivity.this, Integer.parseInt(Long.toString((deleteTimeIt)%100007)+getIntent().getStringExtra("contestId")), intent3, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                    reminderViewModel.delete(reminderIt);
                }
                Toast.makeText(getApplicationContext(),"Deleted All Reminders!",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // on activity result od date and time picker activity
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == NEW_REMINDER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            date=data.getStringExtra("new_reminder_date");
            time=data.getStringExtra("new_reminder_time");
          String Id=data.getStringExtra("contestId");
            String contestName=data.getStringExtra("contestNameTitle");
            String contestTime=data.getStringExtra("contestTimeTitle");
            String timeNotifier=data.getStringExtra("time");
           timeSecondsAtMillis=Long.parseLong(data.getStringExtra("timeMillis"));
            String u=data.getStringExtra("timeMillis");
            timeSecondsAtMillis=Long.parseLong(u);
            Long timeAtButtonClick=Long.parseLong(data.getStringExtra("timeClick"));
          final  String reminder_id=contestId+time;
            Log.i("Ananya",reminder_id + " "+contestId+" "+date+ " "+time);
            Reminder reminder = new Reminder(reminder_id,contestId,date,time,u);

            reminderViewModel.insert(reminder);

            Intent intent=new Intent(ReminderListActivity.this, Notifier.class);
            intent.putExtra("contestNameTitle",contestName);
            intent.putExtra("contestTimeTitle",contestTime);
            intent.putExtra("contestId",Id);
            intent.putExtra("time",timeNotifier);
            Log.i("Aditya",contestId+timeNotifier);
            PendingIntent pendingIntent=PendingIntent.getBroadcast(ReminderListActivity.this,Integer.parseInt(Long.toString((timeSecondsAtMillis)%100007)+contestId),intent,PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
            //if reminder of past time is set then
            if(timeSecondsAtMillis*1000<=timeAtButtonClick)
                Toast.makeText(getApplicationContext(),"Reminder Of Past Time Or Current Time can't be set",Toast.LENGTH_LONG).show();
            else {
                // else set the reminder
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
                {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeSecondsAtMillis * 1000, pendingIntent);
                }
                else
                {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, timeSecondsAtMillis * 1000, pendingIntent);
                }
                Toast.makeText(this, "Reminder Set!", Toast.LENGTH_SHORT).show();
            }

        } else {
            // in case of error
            Toast.makeText(

                    getApplicationContext(),"Reminder Not set!",

                    Toast.LENGTH_LONG).show();

        }

    }

    // creating notification channel
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name="ReminderChannel";
            String description="Channel for Reminder";
            int importance= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel=new NotificationChannel("reminderChannel",name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
