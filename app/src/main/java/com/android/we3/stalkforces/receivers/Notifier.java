package com.android.we3.stalkforces.receivers;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.database.ReminderRoomDatabase;
import com.android.we3.stalkforces.restinterfaces.ReminderDao;


// notifier to show notifications at the reminder time
public class Notifier extends BroadcastReceiver {
    private  String contestNameTitle;
    private String contestTimeTitle;
    private String id;
    private String contestId;
    private String time;
    private ReminderRoomDatabase reminderRoomDatabase;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // remove reminder from database
        reminderRoomDatabase=ReminderRoomDatabase.getDatabase(context.getApplicationContext());
        ReminderDao reminderDao=reminderRoomDatabase.reminderDao();
        contestNameTitle=intent.getStringExtra("contestNameTitle");
        contestTimeTitle=intent.getStringExtra("contestTimeTitle");
        contestId=intent.getStringExtra("contestId");
        time=intent.getStringExtra("time");
        id=contestId+time;
        Log.i("msg",id+"  do"+contestId+" "+time);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"reminderChannel")
                .setSmallIcon(R.drawable.cf)
                .setContentTitle(contestNameTitle)
                .setContentText(contestTimeTitle)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT );

        // notification manager
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);

        // show only one reminder at a time for a contest
        notificationManagerCompat.notify( Integer.parseInt(contestId),builder.build());
        new DeleteAsyncTask(reminderDao).execute(id);

    }
         class DeleteAsyncTask extends AsyncTask <String,Void,Void>{

             ReminderDao mAsyncTaskDao;

            public DeleteAsyncTask(ReminderDao reminderDao) {

                this.mAsyncTaskDao=reminderDao;

            }
            @Override
            protected Void doInBackground(String... strings) {
                mAsyncTaskDao.delete(strings[0]);
                return null;
            }
        }
}
