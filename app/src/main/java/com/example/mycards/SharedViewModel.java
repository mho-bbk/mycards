package com.example.mycards;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mycards.data.entities.UserAnswer;
import com.example.mycards.data.repositories.AnswerRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.R)
public class SharedViewModel extends ViewModel {

    private AnswerRepository answerRepository;

    private List<Card> deck = new ArrayList<>();
    private Iterator<Card> deckIterator;
    private Card currentCard = new Card();

    private final MutableLiveData<List<String>> userInputs = new MutableLiveData<>();
    public final LiveData<List<UserAnswer>> userAnswers = Transformations.switchMap(userInputs, (inputList) -> {
        //Convert input String to UserAnswer class here
        //Temporary: convert UserAnswer to Card here and set deck
        for (String s: inputList) {
            UserAnswer input = new UserAnswer(s);
            deck.add(new Card(input));  //TODO - this bypasses the checks put on the repository and creates duplicate cards...
            answerRepository.upsert(input);
        }
        deckIterator = deck.iterator();
        if(deckIterator.hasNext()) {
            currentCard = deckIterator.next();
        }
        return answerRepository.getAllAnswers();
    });

    public SharedViewModel(AnswerRepository repository) {
        this.answerRepository = repository;
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
    public Card getCurrentCard() {
        return this.currentCard;
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
    public LiveData<List<UserAnswer>> getAllAnswers() { return answerRepository.getAllAnswers(); }

    public void upsert(UserAnswer answer) {
        answerRepository.upsert(answer);
    }

    public void delete(UserAnswer answer) {
        answerRepository.delete(answer);
    }

    public void deleteAllAnswers() {
        answerRepository.deleteAllAnswers();
    }

    public int getAnswerDatabaseSize() {
        //TODO - implement. How does VM interact with LiveData to get the answers on the repo?
        return 0;
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
}