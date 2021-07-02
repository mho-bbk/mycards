package com.example.mycards.ui.main;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.example.mycards.Card;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

@RequiresApi(api = Build.VERSION_CODES.R)
public class MainViewModel extends ViewModel {

    //test deck for the timebeing - real deck will be injected in when link to db
    private final List<Card> testDeck =
            List.of(new Card("apple", "りんご"),
                    new Card("orange", "オレンジ"),
                    new Card("watermelon", "スイカ"));

    private Iterator<Card> cardIterator = testDeck.iterator();
    private Card currentCard;
    private Queue<Card> repeatDeck = new LinkedList<>();

    public List<Card> getTestDeck() {
        return testDeck;
    }

    public Iterator<Card> getCardIterator() {
        return cardIterator;
    }

    public void setCurrentCard(Card card) {
        currentCard = card;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public Queue<Card> getRepeatDeck() {
        return repeatDeck;
    }

    public void addToRepeatDeck(Card card) {
        this.repeatDeck.add(card);
    }

    public void setCardIteratorToRepeatDeck() {
        this.cardIterator = repeatDeck.iterator();
    }
}