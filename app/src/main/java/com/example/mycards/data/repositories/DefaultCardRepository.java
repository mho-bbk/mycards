package com.example.mycards.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mycards.data.db.AnswersDatabase;
import com.example.mycards.data.db.CardEntityDao;
import com.example.mycards.data.db.CardEntityDatabase;
import com.example.mycards.data.db.UserAnswerDao;
import com.example.mycards.data.entities.CardEntity;
import com.example.mycards.data.entities.UserAnswer;

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
    public void upsert(CardEntity card) {
        CardEntityDatabase.databaseWriteExecutor.execute( () -> {
            cardEntityDao.upsert(card);
        });
    }

    public void delete(CardEntity card) {
        CardEntityDatabase.databaseWriteExecutor.execute( () -> {
            cardEntityDao.delete(card);
        });
    }

    public LiveData<List<CardEntity>> getAllCards() { return cardEntityDao.getAllCards(); }

    public void deleteAllCards() {
        CardEntityDatabase.databaseWriteExecutor.execute( () -> {
            cardEntityDao.deleteAllCards();
        });
    }
}
