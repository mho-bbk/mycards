package com.example.mycards;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycards.data.entities.UserAnswer;
import com.example.mycards.data.repositories.AnswerRepository;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.R)
public class SharedViewModel extends ViewModel {

    private AnswerRepository answerRepository;
//    public final LiveData<List<UserAnswer>> userAnswers;
    private List<Card> deck;

    public SharedViewModel(AnswerRepository respository) {
        this.answerRepository = respository;
//        this.userAnswers = repository.getAllAnswers();
    }

//    test deck for the timebeing - real deck will be injected in when link to db
//    private final List<Card> testDeck =
//        List.of(new Card("apple", "りんご (ringo)"),
//                new Card("orange", "オレンジ (orenji)"),
//                new Card("watermelon", "スイカ (suika)"));
//
//    private Iterator<Card> cardIterator = testDeck.iterator();
//    private Card currentCard;
//    private Queue<Card> repeatDeck = new LinkedList<>();


    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public List<Card> getDeck() {
        return deck;
    }

//    public Iterator<Card> getCardIterator() {
//        return cardIterator;
//    }
//
//    public void setCurrentCard(Card card) {
//        currentCard = card;
//    }
//
//    public Card getCurrentCard() {
//        return currentCard;
//    }
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
}