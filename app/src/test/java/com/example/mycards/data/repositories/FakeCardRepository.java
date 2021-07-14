package com.example.mycards.data.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mycards.data.entities.CardEntity;
import com.example.mycards.data.entities.UserAnswer;

import java.util.List;

//TODO - Will be used to test network requests
//Write this to test our ViewModel
public class FakeCardRepository implements CardRepository {

    //Simulate the db
    private final List<CardEntity> testCards = List.of(new CardEntity("chocolate", "チョコレート"),
            new CardEntity("bread", "パン"),
            new CardEntity("sweets", "おやつ"));

    private final MutableLiveData<List<CardEntity>> observableTestCards = new MutableLiveData<>();

    {
        observableTestCards.setValue(testCards);
    }

    //TODO - below relates to network calls
    private boolean shouldReturnNetworkError = false;
    private void setShouldReturnNetworkError(boolean bool) {
        shouldReturnNetworkError = bool;
    }

    private void refreshObservableTestCards() {
        observableTestCards.postValue(testCards);
    }

    @Override
    public void upsert(CardEntity card) {
        testCards.add(card);
        refreshObservableTestCards();
    }

    @Override
    public void delete(CardEntity card) {
        testCards.remove(card);
        refreshObservableTestCards();
    }

    @Override
    public LiveData<List<CardEntity>> getAllCards() {
        return observableTestCards;
    }

    @Override
    public void deleteAllCards() {
        testCards.clear();
        refreshObservableTestCards();
    }
}
