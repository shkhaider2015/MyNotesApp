package com.example.mynotesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton buttonAddTask;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAddTask = findViewById(R.id.floating_button_add);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        getTasks();
    }

    private void getTasks()
    {
        class GetTasks extends AsyncTask<Void, Void, List<Task>>
        {

            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> taskList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .taskDao()
                        .getAll();

                return taskList;
            }

            @Override
            protected void onPostExecute(List<Task> taskList) {
                super.onPostExecute(taskList);
                BackgroundColors backgroundColors = new BackgroundColors();
                int[] colors = backgroundColors.getColors(taskList.size());
                TaskAdapter adapter = new TaskAdapter(MainActivity.this, taskList, colors);
                recyclerView.setAdapter(adapter);
            }

        }

        GetTasks getTasks = new GetTasks();
        getTasks.execute();
    }
}
