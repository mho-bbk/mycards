package com.example.mycards.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mycards.data.entities.Card;

import java.util.List;

@Dao
public interface CardEntityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void upsert(Card card); //YT tutorial has this in kt with 'suspend' for use w/Kotlin coroutines

    @Delete
    void delete(Card card); //YT tutorial has this in kt with 'suspend' for use w/Kotlin coroutines

    @Query("SELECT * FROM cards ORDER BY id")
    LiveData<List<Card>> getAllCards();

    @Query("SELECT * FROM cards WHERE related_word = :relatedWord")
    LiveData<List<Card>> getCards(String relatedWord);

    @Query("SELECT * FROM cards WHERE related_word IN (:relatedWords)")
    LiveData<List<Card>> getCards(List<String> relatedWords);

    @Query("DELETE FROM cards")
    void deleteAllCards();
}
