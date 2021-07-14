package com.example.mycards;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mycards.data.entities.Card;
import com.example.mycards.data.repositories.CardRepository;

import java.util.Iterator;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.R)
public class SharedViewModel extends ViewModel {

    private CardRepository cardRepository;

    private Iterator<Card> deckIterator;
    private Card currentCard = new Card("", "");    //blank card to initiate

    //Creating an observer
    private final Observer<List<Card>> observer = new Observer<List<Card>>() {
        @Override
        public void onChanged(List<Card> cards) {
            setUpDeck(cards);
        }
    };

    private final MutableLiveData<List<String>> userInputs = new MutableLiveData<>();
    public final LiveData<List<Card>> userAnswers = Transformations.switchMap(userInputs, (inputList) -> {
        //Convert input String to CardEntity class here
        for (String s: inputList) {
            Card input = new Card(s, s + " in Japanese");
            cardRepository.upsert(input);
        }
        return cardRepository.getAllCards();
    });

    private void setUpDeck(List<Card> allCards) {
        try {
            deckIterator = allCards.iterator();
            if (deckIterator.hasNext()) {
                currentCard = deckIterator.next();
            }
        } catch(NullPointerException e) {
            System.err.println(e.getStackTrace());
        }
    }

    public SharedViewModel(CardRepository repository) {
        this.cardRepository = repository;

        // Observe the LiveData, passing in the global observer.
        userAnswers.observeForever(observer);
    }

    public void setUserInputs(List<String> allUserInput) {
        userInputs.setValue(allUserInput);
    }

    public Card getCurrentCard() {
        return this.currentCard;
    }

    public Card getNextCard() {
        //TODO - separation of concerns, this needs to be setCurrentCard...
        if(deckIterator.hasNext()) {
            currentCard = deckIterator.next();
        } else {
            currentCard = new Card("Finished deck", "Finished deck");
        }
        return currentCard;
    }

    //TODO - could you do a repeat function with resetDeck() and setUserInputs?
//    private Queue<Card> repeatDeck = new LinkedList<>();
//
//    public Queue<Card> getRepeatDeck() {
//        return repeatDeck;
//    }
//
//    public void addToRepeatDeck(Card card) {
//        this.repeatDeck.add(card);
//    }
//
//    public void setCardIteratorToRepeatDeck() {
//        this.cardIterator = repeatDeck.iterator();
//    }

    //**REPOSITORY/DAO METHODS**
    public LiveData<List<Card>> getAllCards() { return cardRepository.getAllCards(); }

    public void upsert(Card card) {
        cardRepository.upsert(card);
    }

    public void delete(Card card) {
        cardRepository.delete(card);
    }

    public void deleteAllCards() {
        cardRepository.deleteAllCards();
    }

    @Override
    protected void onCleared() {
        userAnswers.removeObserver(observer);
        deleteAllCards();   //TODO - this doesn't seem to work. Why? Related to repositories and persistence?
        super.onCleared();
    }
}