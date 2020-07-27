package com.android.we3.stalkforces.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.models.Reminder;

import java.util.ArrayList;
import java.util.List;

public class ReminderListAdapter extends RecyclerView.Adapter<ReminderListAdapter.ReminderListViewHolder>{
     private List<Reminder>  reminderList;
     private OnDeleteClickListener onDeleteClickListener;
     public interface OnDeleteClickListener{
         void onDeleteClick(int pos);
     }
    @NonNull
    @Override
    public ReminderListAdapter.ReminderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("aaya",parent.toString()+" yo");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_view,parent,false);
        ReminderListAdapter.ReminderListViewHolder viewHolder = new ReminderListAdapter.ReminderListViewHolder(v, onDeleteClickListener);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ReminderListViewHolder holder, int position) {
        Reminder reminder=reminderList.get(position);
        holder.dateText.setText(reminder.getDateReminder());
        holder.timeText.setText(reminder.getTimeReminder());

    }


    public void setReminders(List<Reminder> reminders) {

       reminderList= reminders;

        notifyDataSetChanged();

    }
    @Override
    public int getItemCount() {
        return reminderList.size();
    }
    public  static  class ReminderListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         OnDeleteClickListener onDeleteClickListener;
         public TextView dateText,timeText;
         public ImageView deleteImage;
         public ReminderListViewHolder(View view,OnDeleteClickListener onDeleteClickListener)
         {
             super(view);
             dateText=(TextView)view.findViewById(R.id.dateTitleReminderTextView);
             timeText=(TextView)view.findViewById(R.id.TimeTitleReminderTextView);
             deleteImage=(ImageView)view.findViewById(R.id.DeleteReminderImageView);
             this.onDeleteClickListener=onDeleteClickListener;
             deleteImage.setOnClickListener(this);
         }
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.DeleteReminderImageView)
                onDeleteClickListener.onDeleteClick(getAdapterPosition());
        }

    }
    public ReminderListAdapter(List<Reminder> list,OnDeleteClickListener onDeleteClickListener)
    {
        reminderList=list;
        this.onDeleteClickListener=onDeleteClickListener;
    }

}
