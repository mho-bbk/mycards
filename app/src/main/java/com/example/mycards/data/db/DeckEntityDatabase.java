package com.example.mycards.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mycards.data.entities.Card;
import com.example.mycards.data.entities.Deck;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = Deck.class, version = 1)
public abstract class DeckEntityDatabase extends RoomDatabase {

    public abstract DeckEntityDao getDeckEntityDao();

    private static volatile DeckEntityDatabase instance;    //to make our db a singleton

    //complete singleton pattern
    public static synchronized DeckEntityDatabase getInstance(Context context) {
        //lazy instantiation
        if(instance == null) {
            //Can extract below to own method eg createDatabase to keep separate if desired
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DeckEntityDatabase.class, "deck_database")
                    .build();
        }
        return instance;
    }

}
