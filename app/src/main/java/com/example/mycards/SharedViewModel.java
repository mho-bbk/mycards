package com.example.mycards;

import android.app.Application;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycards.Card;
import com.example.mycards.data.entities.UserAnswer;
import com.example.mycards.data.repositories.AnswerRepository;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

@RequiresApi(api = Build.VERSION_CODES.R)
public class SharedViewModel extends ViewModel {

    private AnswerRepository answerRepository;
    private LiveData<List<UserAnswer>> userAnswers;
    private List<Card> deck;

    public SharedViewModel(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
        this.userAnswers = answerRepository.getAllAnswers();
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
    public LiveData<List<UserAnswer>> getAllAnswers() { return userAnswers; }

    public void upsert(UserAnswer answer) {
        answerRepository.upsert(answer);
    }

    public void delete(UserAnswer answer) {
        answerRepository.delete(answer);
    }

    public void deleteAllAnswers() {
        answerRepository.deleteAllAnswers();
    }
}