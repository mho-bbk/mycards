package com.example.mycards.data.repositories;

import androidx.lifecycle.LiveData;

import com.example.mycards.data.entities.Card;
import com.example.mycards.datamuse.DatamuseAPIService;

import java.util.List;

public interface CardRepository {

    public void upsert(Card card);

    public void delete(Card card);

    public LiveData<List<Card>> getAllCards();

    public LiveData<List<Card>> getCards(String deckSeed);

    public void deleteAllCards();

}
