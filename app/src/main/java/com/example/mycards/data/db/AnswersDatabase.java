package com.example.mycards.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mycards.data.entities.UserAnswer;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = UserAnswer.class, version = 1)
public abstract class AnswersDatabase extends RoomDatabase {

    public abstract UserAnswerDao getUserAnswerDao();

    private static volatile AnswersDatabase instance;    //to make our db a singleton

    //As AsyncTask is deprecated...
    private static final int NUMBER_OF_THREADS = 5; //TODO - 5 here is an arbitrary number
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //complete singleton pattern
    public static synchronized AnswersDatabase getInstance(Context context) {
        //lazy instantiation
        if(instance == null) {
            //Can extract below to own method eg createDatabase to keep separate if desired
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AnswersDatabase.class, "answer_database")
                    .build();
        }
        return instance;
    }

}
