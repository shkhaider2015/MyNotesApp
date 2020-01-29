package com.example.mynotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editTextTask, editTextDesc, editTextFinishBy;
    private static final String TAG = "AddTaskActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTextTask = findViewById(R.id.editTextTask);
        editTextDesc = findViewById(R.id.editTextDesc);
        editTextFinishBy = findViewById(R.id.editTextFinishBy);

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });
    }

    private void saveTask()
    {
        final String sTask = editTextTask.getText().toString().trim();
        final String sDesc = editTextDesc.getText().toString().trim();
        final String sFinishedBy = editTextFinishBy.getText().toString().trim();

        if (sTask.isEmpty())
        {
            editTextTask.setError("Task required !!");
            editTextTask.requestFocus();
            return;
        }
        if (sDesc.isEmpty())
        {
            editTextDesc.setError("Description required !!");
            editTextDesc.requestFocus();
            return;
        }
        if (sFinishedBy.isEmpty())
        {
            editTextFinishBy.setError("Finished by required !!");
            editTextFinishBy.requestFocus();
            return;
        }

        class SaveTask extends AsyncTask<Void, Void, Void>
        {

            @Override
            protected Void doInBackground(Void... voids) {

                // Creating a task

                Task task = new Task();
                task.setTask(sTask);
                task.setDesc(sDesc);
                task.setFinishBy(sFinishedBy);
                task.setFinished(false);

                Log.d(TAG, "doInBackground: gfhfg " + task.getTask());

                //adding to database
                DatabaseClient.getInstance(getApplicationContext())
                        .getAppDatabase()
                        .taskDao()
                        .insert(task);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        SaveTask saveTask = new SaveTask();
        saveTask.execute();

    }
}
