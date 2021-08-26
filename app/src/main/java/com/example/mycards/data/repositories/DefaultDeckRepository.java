package com.example.mycards.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mycards.data.db.CardEntityDao;
import com.example.mycards.data.db.CardEntityDatabase;
import com.example.mycards.data.db.DeckEntityDao;
import com.example.mycards.data.db.DeckEntityDatabase;
import com.example.mycards.data.entities.Card;
import com.example.mycards.data.entities.Deck;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class DefaultDeckRepository implements DeckRepository {

    private final DeckEntityDao deckEntityDao;
    private final ExecutorService executorService;

    @Inject
    public DefaultDeckRepository(Application application, ExecutorService executorService) {
        DeckEntityDatabase db = DeckEntityDatabase.getInstance(application);
        this.deckEntityDao = db.getDeckEntityDao();
        this.executorService = executorService;
    }

    //These are the methods exposed to the client class.
    //This is how we create the abstraction layer
    //Use executor to try and avoid memory leak
    @Override
    public void upsert(Deck deck) {
        deckEntityDao.upsert(deck);
    }

    @Override
    public void delete(Deck deck) {
        executorService.execute( () -> {
            deckEntityDao.delete(deck);
        });
    }

    @Override
    public LiveData<List<Deck>> getAllDecks() { return deckEntityDao.getAllDecks(); }

    @Override
    public void deleteAllDecks() {
        executorService.execute(deckEntityDao::deleteAllDecks);
    }

}
