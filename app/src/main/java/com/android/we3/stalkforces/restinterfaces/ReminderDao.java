package com.android.we3.stalkforces.restinterfaces;




import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.we3.stalkforces.models.Reminder;

import java.util.List;



//dao for reminder table
@Dao

public interface  ReminderDao {



    @Insert
    void insert(Reminder reminder);



    @Query("SELECT * FROM reminderListTable WHERE contestId=:contestId")
    LiveData<List<Reminder>> getReminders(String contestId);

    @Query("DELETE FROM reminderListTable WHERE id=:id")
    int delete(String id);

}