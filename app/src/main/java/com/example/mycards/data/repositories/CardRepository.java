package com.example.mycards.data.repositories;

import androidx.lifecycle.LiveData;

import com.example.mycards.data.entities.CardEntity;
import com.example.mycards.data.entities.UserAnswer;

import java.util.List;

public interface CardRepository {

    public void upsert(CardEntity card);

    public void delete(CardEntity card);

    public LiveData<List<CardEntity>> getAllCards();

    public void deleteAllCards();
}
