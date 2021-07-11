package com.example.mycards.data.repositories;

import androidx.lifecycle.LiveData;

import com.example.mycards.data.entities.UserAnswer;

import java.util.List;

public interface AnswerRepository {

    public void upsert(UserAnswer answer);

    public void delete(UserAnswer answer);

    public LiveData<List<UserAnswer>> getAllAnswers();

    public void deleteAllAnswers();
}
