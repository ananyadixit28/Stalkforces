package com.android.we3.stalkforces.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.we3.stalkforces.models.Reminder;
import com.android.we3.stalkforces.restinterfaces.ReminderDao;


// room database
@Database(entities = Reminder.class, version = 3)
public abstract class ReminderRoomDatabase extends RoomDatabase {
    private static ReminderRoomDatabase database;
    Context context;
    public abstract ReminderDao reminderDao();
    private static volatile ReminderRoomDatabase reminderRoomInstance;



    public static ReminderRoomDatabase getDatabase(final Context context) {

        if (reminderRoomInstance == null) {

            synchronized (ReminderRoomDatabase.class) {

                if (reminderRoomInstance == null) {

                    reminderRoomInstance = Room.databaseBuilder(context.getApplicationContext(),

                            ReminderRoomDatabase.class, "reminderListTable").fallbackToDestructiveMigration()

                            .build();

                }

            }

        }
        return reminderRoomInstance;

    }

}
