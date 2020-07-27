package com.android.we3.stalkforces.models;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.we3.stalkforces.database.ReminderRoomDatabase;
import com.android.we3.stalkforces.restinterfaces.ReminderDao;

import java.util.List;

// reminders view model to bind live data
public class ReminderViewModel extends AndroidViewModel {


    private String contestId;
    private String TAG = this.getClass().getSimpleName();

    private ReminderDao reminderDao;

    private ReminderRoomDatabase reminderDB;

    private LiveData<List<Reminder>> mAllReminders;



    public ReminderViewModel(Application application) {

        super(application);



        reminderDB = ReminderRoomDatabase.getDatabase (application);

        reminderDao = reminderDB.reminderDao();

     //   mAllReminders = reminderDao.getReminders(contestId);

    }



    public void insert(Reminder reminder) {

        new InsertAsyncTask(reminderDao).execute(reminder);

    }
    public void delete(Reminder reminder) {

        new DeleteAsyncTask(reminderDao).execute(reminder);

    }


    public LiveData<List<Reminder>> getAllReminders(String contestId) {
        return reminderDao.getReminders(contestId);

    }

    @Override

    protected void onCleared() {

        super.onCleared();

        Log.i(TAG, "ViewModel Destroyed");

    }



    private class OperationsAsyncTask extends AsyncTask<Reminder, Void, Void> {



        ReminderDao mAsyncTaskDao;



        OperationsAsyncTask(ReminderDao dao) {

            this.mAsyncTaskDao = dao;

        }



        @Override

        protected Void doInBackground(Reminder... reminders) {

            return null;

        }

    }



    private class InsertAsyncTask extends OperationsAsyncTask {



        InsertAsyncTask(ReminderDao mReminderDao) {

            super(mReminderDao);

        }



        @Override

        protected Void doInBackground(Reminder... reminders) {

            mAsyncTaskDao.insert(reminders[0]);

            return null;

        }

    }


    private class DeleteAsyncTask extends OperationsAsyncTask {



        public DeleteAsyncTask(ReminderDao reminderDao) {

            super(reminderDao);

        }



        @Override

        protected Void doInBackground(Reminder... reminders) {

            mAsyncTaskDao.delete(reminders[0].getId());

            return null;

        }

    }

}
