package com.example.mycards.data.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mycards.R;
import com.example.mycards.data.entities.JMDictEntry;
import com.example.mycards.jmdict.typeconverters.JMDictEntryTypeConverters;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Based on: https://gist.github.com/florina-muntenescu/697e543652b03d3d2a06703f5d6b44b5

@Database(entities = JMDictEntry.class, version = 1)
@TypeConverters(value = JMDictEntryTypeConverters.class)
public abstract class JMDictEntryDatabase extends RoomDatabase {

    public abstract JMDictEntryDao getJMDictEntryDao();

    private static volatile JMDictEntryDatabase instance;    //to make our db a singleton

    //As AsyncTask is deprecated...
    public static final ExecutorService jmDictDatabaseWriteExecutor = Executors.newSingleThreadExecutor();

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
        JMDictEntryDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                JMDictEntryDatabase.class, "jmdict_database")
                //prepopulate the db after onCreate is called
//                .addCallback(new Callback() {
//                    @Override
//                    public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
//                        System.out.println("I'm inside the JMDictDB buildDatabase() callback...");
//                        super.onCreate(db);
//                        //execute it on a thread to prevent freezing main thread
//                        jmDictDatabaseWriteExecutor.execute(
//                                () -> getInstance(context)
//                                        .getJMDictEntryDao()
//                                        .insertAll(getPrePopulatedData(context))
//                        );
//                    }
//                })
                .build();

        return db;
    }

    private static synchronized List<JMDictEntry> getPrePopulatedData(Context context) {
        List<JMDictEntry> dictEntries = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            dictEntries = mapper.readValue(context.getResources()
                    .openRawResource(R.raw.reverse_jmdictentries_plain_sample),
                    new TypeReference<List<JMDictEntry>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dictEntries;
    }
}
