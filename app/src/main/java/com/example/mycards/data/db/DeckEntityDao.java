package com.example.mycards.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mycards.data.entities.Card;
import com.example.mycards.data.entities.Deck;

import java.util.List;

@Dao
public interface DeckEntityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void upsert(Deck deck); //YT tutorial has this in kt with 'suspend' for use w/Kotlin coroutines

    @Delete
    void delete(Deck deck); //YT tutorial has this in kt with 'suspend' for use w/Kotlin coroutines

    @Query("SELECT * FROM decks ORDER BY deckId")
    LiveData<List<Deck>> getAllDecks();

    @Query("DELETE FROM decks")
    void deleteAllDecks();
}
