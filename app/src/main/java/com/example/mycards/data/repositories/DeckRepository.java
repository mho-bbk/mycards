package com.example.mycards.data.repositories;

import androidx.lifecycle.LiveData;

import com.example.mycards.data.entities.Card;
import com.example.mycards.data.entities.Deck;

import java.util.List;

public interface DeckRepository {

    public void upsert(Deck deck);

    public void delete(Deck deck);

    public LiveData<List<Deck>> getAllDecks();

    public void deleteAllDecks();

}
