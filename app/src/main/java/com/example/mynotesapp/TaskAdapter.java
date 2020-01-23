package com.example.mynotesapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context mCtx;
    private List<Task> taskList;

    public TaskAdapter(Context mCtx, List<Task> taskList)
    {
        this.mCtx = mCtx;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(mCtx)
                .inflate(R.layout.recyclerview_tasks, parent, false);
        return new TaskViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position)
    {
        Task task = new Task();

        holder.textViewTask.setText(task.getTask());
        holder.textViewdesc.setText(task.getDesc());
        holder.textViewFinishedBy.setText(task.getFinishBy());

        if (task.isFinished())
        {
            holder.textViewStatus.setText("Completed");
        }
        else
            holder.textViewStatus.setText("Not Completed");


    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView textViewStatus, textViewTask, textViewdesc, textViewFinishedBy;

        public TaskViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewdesc = itemView.findViewById(R.id.textViewDesc);
            textViewFinishedBy = itemView.findViewById(R.id.textViewFinishBy);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v)
        {
            Task task = taskList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, UpdateTaskActivity.class);
            intent.putExtra("task", task);

            mCtx.startActivity(intent);

        }
    }
}
