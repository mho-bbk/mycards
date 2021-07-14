package com.example.mycards.data.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mycards.data.entities.Card;

import java.util.List;

//TODO - Will be used to test network requests
//Write this to test our ViewModel
public class FakeCardRepository implements CardRepository {

    //Simulate the db
    private final List<Card> testCards = List.of(new Card("chocolate", "チョコレート"),
            new Card("bread", "パン"),
            new Card("sweets", "おやつ"));

    private final MutableLiveData<List<Card>> observableTestCards = new MutableLiveData<>();

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
    public void upsert(Card card) {
        testCards.add(card);
        refreshObservableTestCards();
    }

    @Override
    public void delete(Card card) {
        testCards.remove(card);
        refreshObservableTestCards();
    }

    @Override
    public LiveData<List<Card>> getAllCards() {
        return observableTestCards;
    }

    @Override
    public void deleteAllCards() {
        testCards.clear();
        refreshObservableTestCards();
    }
}
