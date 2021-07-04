package com.example.mycards.ui.carddisplay;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.example.mycards.Card;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@RequiresApi(api = Build.VERSION_CODES.R)
public class CardDisplayViewModel extends ViewModel {

    //test deck for the timebeing - real deck will be injected in when link to db
    private final List<Card> testDeck =
            List.of(new Card("apple", "りんご (ringo)"),
                    new Card("orange", "オレンジ (orenji)"),
                    new Card("watermelon", "スイカ (suika)"));

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