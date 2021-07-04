package com.example.mycards.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mycards.data.entities.UserAnswer;

import java.util.List;

@Dao
public interface UserAnswerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void upsert(UserAnswer userAnswer); //YT tutorial has this in kt with 'suspend' for use w/Kotlin coroutines

    @Delete
    void delete(UserAnswer userAnswer); //YT tutorial has this in kt with 'suspend' for use w/Kotlin coroutines

    @Query("SELECT * FROM user_answers ORDER BY id")
    LiveData<List<UserAnswer>> getAllAnswers();

    @Query("DELETE FROM user_answers")
    void deleteAllAnswers();
}
