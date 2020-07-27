package com.android.we3.stalkforces.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.models.UpcomingContests;

import java.util.ArrayList;
import java.util.Date;


public class UpcomingContestsAdapter extends RecyclerView.Adapter<UpcomingContestsAdapter.UpcomingContestsViewHolder> {
    private ArrayList<UpcomingContests> upcomingContestsList;
    private onAddReminderClickListener onAddReminderClickListener;
    public interface onAddReminderClickListener
    {
        void onAddReminderClick(int pos);
    }

    @Override
    public void onBindViewHolder(UpcomingContestsViewHolder holder, int position) {
        UpcomingContests upcomingContests=upcomingContestsList.get(position);
            holder.text1.setText(upcomingContests.getUpcomingContestName());
            Long time = upcomingContests.getContestStartTime();
            Date d = new Date(time * 1000);
            holder.text2.setText(d.toString());
            Integer seconds = upcomingContests.getContestDuration();
            Integer p2 = seconds / 60;
            Integer p3 = p2 % 60;
            p2 = p2 / 60;
            String duration;
            if (p3 == 0)
                duration = p2.toString() + ":" + p3.toString() + "0 hrs";
            else
                duration = p2.toString() + ":" + p3.toString()+" hrs";
            holder.text3.setText(duration);
    }
    public static class UpcomingContestsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView text1, text2,text3;
        public ImageView image1;
        onAddReminderClickListener onAddReminderClickListener;

        public UpcomingContestsViewHolder(View itemView,onAddReminderClickListener onAddReminderClickListener) {
            super(itemView);
            text1 = itemView.findViewById(R.id.upcomingContestNameTextView);
            text2 = itemView.findViewById(R.id.upcomingContestsDateTimeTextView);
            text3=itemView.findViewById(R.id.upcomingContestsDurationTextView);
            image1=(ImageView)itemView.findViewById(R.id.addReminderImageView);
            this.onAddReminderClickListener=onAddReminderClickListener;
            image1.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.addReminderImageView)
                onAddReminderClickListener.onAddReminderClick(getAdapterPosition());
        }
    }

    public UpcomingContestsAdapter(ArrayList<UpcomingContests> list,onAddReminderClickListener onAddReminderClickListener) {
        upcomingContestsList = list;
        this.onAddReminderClickListener=onAddReminderClickListener;
    }

    @Override
    public UpcomingContestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_contests_view, parent, false);
        UpcomingContestsViewHolder viewHolder = new UpcomingContestsViewHolder(v,onAddReminderClickListener);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return upcomingContestsList.size();
    }
}
