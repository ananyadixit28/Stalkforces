package com.android.we3.stalkforces.restinterfaces;


import com.android.we3.stalkforces.models.UpcomingContestsResult;

import retrofit2.Call;
import retrofit2.http.GET;


// interface for codeforces upcoming contests end point
public interface UpcomingContestsEndPoint {
    @GET("contest.list")
    Call<UpcomingContestsResult> getUpcomingContests(

    );
}
