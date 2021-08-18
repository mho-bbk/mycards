package com.example.mycards.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mycards.data.db.CardEntityDao;
import com.example.mycards.data.db.CardEntityDatabase;
import com.example.mycards.data.entities.Card;
import com.example.mycards.datamuse.DatamuseAPIService;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class DefaultCardRepository implements CardRepository {

    private final CardEntityDao cardEntityDao;
    private final ExecutorService executorService;

    @Inject
    public DefaultCardRepository(Application application, ExecutorService executorService) {
        CardEntityDatabase db = CardEntityDatabase.getInstance(application);
        this.cardEntityDao = db.getCardEntityDao();
        this.executorService = executorService;
    }

    //These are the methods exposed to the client class.
    //This is how we create the abstraction layer
    //Use executor to try and avoid memory leak
    @Override
    public void upsert(Card card) {
        executorService.execute( () -> {
            cardEntityDao.upsert(card);
        });
    }

    @Override
    public void delete(Card card) {
        executorService.execute( () -> {
            cardEntityDao.delete(card);
        });
    }

    @Override
    public LiveData<List<Card>> getAllCards() { return cardEntityDao.getAllCards(); }

    @Override
    public LiveData<List<Card>> getCards(String deckSeed) {
        //TODO - test null return
        return cardEntityDao.getCards(deckSeed);
    }

    @Override
    public void deleteAllCards() {
        executorService.execute( () -> {
            cardEntityDao.deleteAllCards();
        });
    }

}
