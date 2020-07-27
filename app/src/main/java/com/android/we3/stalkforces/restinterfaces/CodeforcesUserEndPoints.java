package com.android.we3.stalkforces.restinterfaces;

import com.android.we3.stalkforces.models.CodeforcesUserJson;
import com.android.we3.stalkforces.models.UserContestsResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


// interface for codeforces user end point
public interface CodeforcesUserEndPoints {

    @GET("user.info")
    Call<CodeforcesUserJson> getUser(@Query("handles") String userHandle);
}
