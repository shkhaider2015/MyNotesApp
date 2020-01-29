package com.example.mynotesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateTaskActivity extends AppCompatActivity {

    private EditText editTextTask, editTextDesc, editTextFinishedBy;
    private CheckBox checkBoxFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        editTextTask = findViewById(R.id.editTextTask);
        editTextDesc = findViewById(R.id.editTextDesc);
        editTextFinishedBy = findViewById(R.id.editTextFinishBy);

        checkBoxFinished = findViewById(R.id.checkBoxFinished);


        final Task task = (Task) getIntent().getSerializableExtra("task");

        loadTask(task);

        findViewById(R.id.button_update)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT)
                                .show();
                        updateTask(task);
                    }
                });

        findViewById(R.id.button_delete)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTaskActivity.this);
                        builder.setTitle("Are You Sure ?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteTask(task);
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
    }

    private void loadTask(Task task)
    {
        editTextTask.setText(task.getTask());
        editTextDesc.setText(task.getDesc());
        editTextFinishedBy.setText(task.getFinishBy());
        checkBoxFinished.setChecked(task.isFinished());
    }

    private void updateTask(final Task task)
    {
        final String sTask = editTextTask.getText().toString().trim();
        final String sDesc = editTextDesc.getText().toString().trim();
        final String sFinishedBy = editTextFinishedBy.getText().toString().trim();
        final boolean sIsChecked = checkBoxFinished.isChecked();

        if (sTask.isEmpty())
        {
            editTextTask.setError("Task required !");
            editTextTask.requestFocus();
            return;
        }
        if (sDesc.isEmpty())
        {
            editTextDesc.setError("Description required !");
            editTextDesc.requestFocus();
            return;
        }
        if (sFinishedBy.isEmpty())
        {
            editTextFinishedBy.setError("Finished By required !");
            editTextFinishedBy.requestFocus();
            return;
        }


        class UpdateTask extends AsyncTask<Void, Void, Void>
        {

            @Override
            protected Void doInBackground(Void... voids) {
                task.setTask(sTask);
                task.setDesc(sDesc);
                task.setFinishBy(sFinishedBy);
                task.setFinished(sIsChecked);

                DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .taskDao()
                        .update(task);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT)
                        .show();
                finish();
                startActivity(new Intent(UpdateTaskActivity.this, MainActivity.class));;

            }
        }

        UpdateTask updateTask = new UpdateTask();
        updateTask.execute();
    }

    private void deleteTask(final Task task)
    {
        class DeleteTask extends AsyncTask<Void, Void, Void>
        {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .taskDao()
                        .delete(task);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT)
                        .show();
                finish();
                startActivity(new Intent(UpdateTaskActivity.this, MainActivity.class));
            }
        }

        DeleteTask deleteTask = new DeleteTask();
        deleteTask.execute();
    }
}
