package com.example.mynotesapp;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private Context mCtx;
    private static DatabaseClient mInstance;

    private AppDatabase appDatabase;

    private DatabaseClient(Context mCtx)
    {
        this.mCtx = mCtx;

        // Here MyToDos is the name of the Database
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "MyToDos1" ).build();

    }

    public static synchronized DatabaseClient getInstance(Context mCtx)
    {
        if (mInstance == null)
        {
            mInstance = new DatabaseClient(mCtx);
        }

        return mInstance;
    }

    public AppDatabase getAppDatabase()
    {
        return appDatabase;
    }
}
