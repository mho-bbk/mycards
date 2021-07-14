package com.example.mycards.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mycards.data.db.CardEntityDao;
import com.example.mycards.data.db.CardEntityDatabase;
import com.example.mycards.data.entities.Card;

import java.util.List;

public class DefaultCardRepository implements CardRepository {

    private CardEntityDao cardEntityDao;

    public DefaultCardRepository(Application application) {
        CardEntityDatabase db = CardEntityDatabase.getInstance(application);
        cardEntityDao = db.getCardEntityDao();
    }

    //These are the methods exposed to the ViewModel
    //This is how we create the abstraction layer
    //Use executor to try and avoid memory leak
    public void upsert(Card card) {
        CardEntityDatabase.databaseWriteExecutor.execute( () -> {
            cardEntityDao.upsert(card);
        });
    }

    public void delete(Card card) {
        CardEntityDatabase.databaseWriteExecutor.execute( () -> {
            cardEntityDao.delete(card);
        });
    }

    public LiveData<List<Card>> getAllCards() { return cardEntityDao.getAllCards(); }

    public void deleteAllCards() {
        CardEntityDatabase.databaseWriteExecutor.execute( () -> {
            cardEntityDao.deleteAllCards();
        });
    }
}
