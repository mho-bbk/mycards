package com.example.mycards.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mycards.data.entities.Card;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = Card.class, version = 1)
public abstract class CardEntityDatabase extends RoomDatabase {

    public abstract CardEntityDao getCardEntityDao();

    private static volatile CardEntityDatabase instance;    //to make our db a singleton

    //complete singleton pattern
    public static synchronized CardEntityDatabase getInstance(Context context) {
        //lazy instantiation
        if(instance == null) {
            //Can extract below to own method eg createDatabase to keep separate if desired
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CardEntityDatabase.class, "card_database")
                    .build();
        }
        return instance;
    }

}
