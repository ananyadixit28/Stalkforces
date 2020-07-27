package com.android.we3.stalkforces.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
// class for upcoming contests
public class UpcomingContestsResult {
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private ArrayList<UpcomingContests> upcomingContestResult;

    public UpcomingContestsResult(String status, ArrayList<UpcomingContests> contestResult) {
        this.status = status;
        this.upcomingContestResult = contestResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<UpcomingContests> getUpcomingContestResult() {
        return upcomingContestResult;
    }

    public void setUpcomingContestResult(ArrayList<UpcomingContests> contestResult) {
        this.upcomingContestResult = contestResult;
    }
}
