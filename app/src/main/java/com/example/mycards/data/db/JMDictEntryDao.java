package com.example.mycards.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mycards.data.entities.JMDictEntry;

import java.util.List;

@Dao
public interface JMDictEntryDao {

    @Insert
    public void insertAll(List<JMDictEntry> jmDictEntries);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void upsert(JMDictEntry dictEntry); //YT tutorial has this in kt with 'suspend' for use w/Kotlin coroutines

    @Delete
    public void delete(JMDictEntry dictEntry); //YT tutorial has this in kt with 'suspend' for use w/Kotlin coroutines

    @Query("SELECT * FROM jmdict WHERE inner_gloss = :gloss ORDER BY gloss_order, sense_order, gloss_count LIMIT 1")
    public LiveData<JMDictEntry> getFirstJMDictEntry(String gloss);

    @Query("SELECT * FROM jmdict WHERE inner_gloss = :gloss")
    public LiveData<List<JMDictEntry>> getAllJMDictEntries(String gloss);

    @Query("DELETE FROM jmdict")
    public void deleteAllJMDictEntries();
}
