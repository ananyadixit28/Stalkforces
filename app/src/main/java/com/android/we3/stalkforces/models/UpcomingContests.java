package com.android.we3.stalkforces.models;

import com.google.gson.annotations.SerializedName;

public class UpcomingContests {
    @SerializedName("id")
    private Integer upcomingContestId;

    @SerializedName("name")
    private String upcomingContestName;

    @SerializedName("phase")
    private String upcomingContestPhase;

    @SerializedName("durationSeconds")
    private Integer contestDuration;

    @SerializedName("startTimeSeconds")
    private Long contestStartTime;

    public Integer getUpcomingContestId() {
        return upcomingContestId;
    }

    public void setUpcomingContestId(Integer upcomingContestId) {
        this.upcomingContestId = upcomingContestId;
    }

    public String getUpcomingContestName() {
        return upcomingContestName;
    }

    public void setUpcomingContestName(String upcomingContestName) {
        this.upcomingContestName = upcomingContestName;
    }

    public String getUpcomingContestPhase() {
        return upcomingContestPhase;
    }

    public void setUpcomingContestPhase(String upcomingContestPhase) {
        this.upcomingContestPhase = upcomingContestPhase;
    }

    public Integer getContestDuration() {
        return contestDuration;
    }

    public void setContestDuration(Integer contestDuration) {
        this.contestDuration = contestDuration;
    }

    public Long getContestStartTime() {
        return contestStartTime;
    }

    public void setContestStartTime(Long contestStartTime) {
        this.contestStartTime = contestStartTime;
    }
}
