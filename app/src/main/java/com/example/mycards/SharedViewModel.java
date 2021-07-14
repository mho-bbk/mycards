package com.example.mycards;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mycards.data.entities.CardEntity;
import com.example.mycards.data.entities.UserAnswer;
import com.example.mycards.data.repositories.AnswerRepository;
import com.example.mycards.data.repositories.CardRepository;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.R)
public class SharedViewModel extends ViewModel {

    private CardRepository cardRepository;

    private List<Card> deck = new ArrayList<>();
    private Iterator<Card> deckIterator;
    private Card currentCard = new Card();

    private Iterator<CardEntity> deckIterator2;
    private CardEntity currentCard2 = new CardEntity("", "");
    //Creating an observer
    final Observer<List<CardEntity>> observer = new Observer<List<CardEntity>>() {
        @Override
        public void onChanged(List<CardEntity> cards) {
            setUpDeck(cards);
        }
    };

    private final MutableLiveData<List<String>> userInputs = new MutableLiveData<>();
    public final LiveData<List<CardEntity>> userAnswers = Transformations.switchMap(userInputs, (inputList) -> {
        //Convert input String to UserAnswer class here
        //Temporary: convert UserAnswer to Card here and set deck
        for (String s: inputList) {
            CardEntity input = new CardEntity(s, s + " in Japanese");
            cardRepository.upsert(input);
        }
//        deckIterator = deck.iterator();
//        if(deckIterator.hasNext()) {
//            currentCard = deckIterator.next();
//        }
//        setUpDeck(cardRepository.getAllCards());
        return cardRepository.getAllCards();
    });


    private void setUpDeck(List<CardEntity> allCards) {
        try {
            deckIterator2 = allCards.iterator();
            if (deckIterator2.hasNext()) {
                System.out.println("setting currentCard...");
                currentCard2 = deckIterator2.next();
            }
        } catch(NullPointerException e) {
            System.out.println("Current card is null...");
            System.err.println(e.getStackTrace());
        }
    }

    public SharedViewModel(CardRepository repository) {
        this.cardRepository = repository;
//        setUpDeck(userAnswers);

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        userAnswers.observeForever(observer);
    }

    public void setUserInputs(List<String> allUserInput) {
        userInputs.setValue(allUserInput);
    }
//    test deck for the timebeing - real deck will be injected in when link to db
//    private final List<Card> testDeck =
//        List.of(new Card("apple", "りんご (ringo)"),
//                new Card("orange", "オレンジ (orenji)"),
//                new Card("watermelon", "スイカ (suika)"));

//    private Queue<Card> repeatDeck = new LinkedList<>();

    //TODO - rename
    public void setDeck(List<Card> deck) {
        this.deck = deck;
        deckIterator = deck.iterator();
        if(deckIterator.hasNext()) {
            currentCard = deckIterator.next();
        }
    }

    public List<Card> getDeck() {
        return this.deck;
    }

    public Iterator<Card> getDeckIterator() {
        return this.deckIterator;
    }


//    public void setCurrentCard(Card card) {
//        currentCard = card;
//    }
//
    public CardEntity getCurrentCard() {
        return this.currentCard2;
    }
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
    public LiveData<List<CardEntity>> getAllCards() { return cardRepository.getAllCards(); }

    public void upsert(CardEntity card) {
        cardRepository.upsert(card);
    }

    public void delete(CardEntity card) {
        cardRepository.delete(card);
    }

    public void deleteAllCards() {
        cardRepository.deleteAllCards();
    }

    public int getAnswerDatabaseSize() {
        //TODO - implement. How does VM interact with LiveData to get the answers on the repo?
        return 0;
    }

    public CardEntity getNextCard() {
        //TODO - separation of concerns, this needs to be setCurrentCard...
        if(deckIterator2.hasNext()) {
            currentCard2 = deckIterator2.next();
        } else {
            currentCard2 = new CardEntity("Finished deck", "Finished deck");
        }
        return currentCard2;
    }

    @Override
    protected void onCleared() {
        userAnswers.removeObserver(observer);
        deleteAllCards();
        super.onCleared();
    }
}