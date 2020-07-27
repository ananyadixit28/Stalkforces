package com.android.we3.stalkforces.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.adapters.UpcomingContestsAdapter;
import com.android.we3.stalkforces.adapters.UserContestsAdapter;
import com.android.we3.stalkforces.apiservice.APIClient;
import com.android.we3.stalkforces.models.UpcomingContests;
import com.android.we3.stalkforces.models.UpcomingContestsResult;
import com.android.we3.stalkforces.restinterfaces.UpcomingContestsEndPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingContestsActivity extends AppCompatActivity implements UpcomingContestsAdapter.onAddReminderClickListener{
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private UpcomingContestsAdapter contestsAdapter;
    private ArrayList<UpcomingContests> contestsList;
    private ArrayList<UpcomingContests> upcomingContestsArrayList;
    public static int cSize;
    // on Create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_contests);
        recyclerView = (RecyclerView)findViewById(R.id.upcomingContestsRecyclerView);
        layoutManager = new LinearLayoutManager(UpcomingContestsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        contestsList = new ArrayList<>();
        upcomingContestsArrayList=new ArrayList<>();
        contestsAdapter = new UpcomingContestsAdapter(upcomingContestsArrayList,this);
        recyclerView.setAdapter(contestsAdapter);
        loadUpcomingContests();
    }

    // function to load all the upcoming contests
    private void loadUpcomingContests()
    {
        final UpcomingContestsEndPoint contestsEndPoint = APIClient.getClient().create(UpcomingContestsEndPoint.class);
        Call<UpcomingContestsResult> userContestsResultCall = contestsEndPoint.getUpcomingContests();
        userContestsResultCall.enqueue(new Callback<UpcomingContestsResult>() {
            @Override
            public void onResponse(Call<UpcomingContestsResult> call, Response<UpcomingContestsResult> response) {
                if(response.body()==null)
                    Log.i("MSG","failed!!");
                // if response is successful and result status is ok
                if(response.isSuccessful() && response.body().getStatus().equals("OK")) {
                    contestsList.clear();
                    contestsList.addAll(response.body().getUpcomingContestResult());
                    cSize=contestsList.size();
                    Log.i("MSG",String.valueOf(cSize));
                    for(int i=0;i<contestsList.size();i++)
                    {
                        UpcomingContests upcomingContests=contestsList.get(i);
                        if(upcomingContests.getUpcomingContestPhase().equals("BEFORE")) {
                            upcomingContestsArrayList.add(upcomingContests);
                            Log.i("id",upcomingContests.getUpcomingContestId().toString());
                        }
                    }

                    //if size of the array list is 0 that means no contest is upcoming
                    if(upcomingContestsArrayList.size()==0)
                        Toast.makeText(getApplicationContext(),"No Upcoming Contests!",Toast.LENGTH_SHORT).show();
                    else {
                        Collections.reverse(upcomingContestsArrayList);
                        cSize=upcomingContestsArrayList.size();
                        Log.i("TagIt", String.valueOf(cSize));
                        contestsAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<UpcomingContestsResult> call, Throwable t) {

            }
        });
    }

    // on click to implement add reminder on the specific reminder
    @Override
    public void onAddReminderClick(int pos)
    {
        Intent intent=new Intent(UpcomingContestsActivity.this,ReminderListActivity.class);
        UpcomingContests upcomingContests=upcomingContestsArrayList.get(pos);
        String contestName=upcomingContests.getUpcomingContestName();
        Long contestTime=upcomingContests.getContestStartTime();
        Date dateToSend=new Date(contestTime*1000);
        String dateToSendString=String.valueOf(dateToSend);
        intent.putExtra("contestName",contestName);
        intent.putExtra("contestTime",dateToSendString);
        intent.putExtra("contestId",upcomingContests.getUpcomingContestId().toString());
        startActivity(intent);
        //Toast.makeText(getApplicationContext(),"Reminder Set!",Toast.LENGTH_SHORT).show();
    }

    //on destroy
    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    // on back pressed
    @Override
    public void onBackPressed() {

        finish();
    }
}
