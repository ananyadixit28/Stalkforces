package com.android.we3.stalkforces.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// reminder table to add all the reminders in the room database
@Entity(tableName = "reminderListTable")
public class Reminder {
    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    public String getId() {
        return id;
    }
    @NonNull
    @ColumnInfo(name = "unixTime")
    private String unixTime;

    @NonNull
    public String getUnixTime() {
        return unixTime;
    }
    @NonNull
    public void setUnixTime(String unixTime) {
        this.unixTime = unixTime;
    }

    @NonNull
    @ColumnInfo(name = "contestId")
    private String contestId;
    @NonNull
    public String getContestId() {
        return contestId;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setContestId(@NonNull String contestId) {
        this.contestId = contestId;
    }

    public void setDateReminder(@NonNull String dateReminder) {
        this.dateReminder = dateReminder;
    }

    public void setTimeReminder(@NonNull String timeReminder) {
        this.timeReminder = timeReminder;
    }

    @NonNull
    @ColumnInfo(name = "reminder_date")

    private String dateReminder;

    @NonNull
    @ColumnInfo(name = "reminder_time")

    private String timeReminder;
    @NonNull
    public String getDateReminder() {
        return this.dateReminder;
    }


    @NonNull
    public String getTimeReminder() {
        return this.timeReminder;
    }


    public Reminder(String id,String contestId,String dateReminder, String timeReminder,String unixTime)
    {
        this.id=id;
        this.contestId=contestId;
        this.dateReminder=dateReminder;
        this.timeReminder=timeReminder;
        this.unixTime=unixTime;
    }
}
