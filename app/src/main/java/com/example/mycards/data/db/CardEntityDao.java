package com.example.mycards.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mycards.data.entities.CardEntity;

import java.util.List;

@Dao
public interface CardEntityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void upsert(CardEntity cardEntity); //YT tutorial has this in kt with 'suspend' for use w/Kotlin coroutines

    @Delete
    void delete(CardEntity cardEntity); //YT tutorial has this in kt with 'suspend' for use w/Kotlin coroutines

    @Query("SELECT * FROM cards ORDER BY id")
    LiveData<List<CardEntity>> getAllCards();

    @Query("DELETE FROM cards")
    void deleteAllCards();
}
