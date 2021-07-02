package com.example.mycards.ui.main;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.example.mycards.Card;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.R)
public class MainViewModel extends ViewModel {

    //test deck for the timebeing - real deck will be injected in when link to db
    private final List<Card> testDeck =
            List.of(new Card("apple", "りんご"),
                    new Card("orange", "オレンジ"),
                    new Card("watermelon", "スイカ"));

    private Iterator<Card> cardIterator = testDeck.iterator();

    private Card currentCard;

    public Iterator<Card> getCardIterator() {
        return cardIterator;
    }

    public void setCurrentCard(Card card) {
        currentCard = card;
    }

    public Card getCurrentCard() {
        return currentCard;
    }
}