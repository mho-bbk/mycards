package com.example.mycards.data.db;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mycards.R;
import com.example.mycards.data.entities.JMDictEntry;
import com.example.mycards.usecases.jptranslate.jmdict.typeconverters.JMDictEntryTypeConverters;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Database(entities = JMDictEntry.class, version = 1)
@TypeConverters(value = JMDictEntryTypeConverters.class)
public abstract class JMDictEntryDatabase extends RoomDatabase {

    private static final String TAG = "JMDictEntryDatabase";
    public abstract JMDictEntryDao getJMDictEntryDao();

    private static volatile JMDictEntryDatabase instance;    //to make our db a singleton

    //complete singleton pattern
    public static synchronized JMDictEntryDatabase getInstance(Context context) {
        //lazy instantiation
        if(instance == null) {
            //Can extract below to own method eg createDatabase to keep separate if desired
            instance = buildDatabase(context);
        }
        return instance;
    }

    private static synchronized JMDictEntryDatabase buildDatabase(Context context) {
//        long startTime = System.nanoTime();
//        Log.d(TAG, "Waiting for the jmdict db to pre-populate...");

        JMDictEntryDatabase prepopDb = Room.databaseBuilder(context.getApplicationContext(),
                JMDictEntryDatabase.class, "jmdict_prepop.db")
                .createFromAsset("jmdict_database_v0.1.db")
                .build();
//
//        long endTime = System.nanoTime();
//        long duration = (endTime - startTime) / 1_000_000;  //divide by 1000000 to get milliseconds.
//        Log.d(TAG, "jmdict db has pre-populated in " + duration + "ms!");

        return prepopDb;
    }
}
