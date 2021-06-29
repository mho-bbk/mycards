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

    //TODO - Rename testDictionary and keyIterator to something more appropriate now Card class exists
    private final List<Card> testDictionary =
            List.of(new Card("apple", "りんご"),
                    new Card("orange", "オレンジ"),
                    new Card("watermelon", "スイカ"));
    private Map<Card, Boolean> shownWords = new HashMap<>();
    private Iterator<Card> keyIterator = testDictionary.iterator();
    private Card lastDisplayed;

    {
        //shownWords is a map of flags indicating whether a key value has been displayed or not
        //ASSUMPTION: shownWords contains exactly the same keys as testDictionary and its values are false initially
        testDictionary.forEach(card -> shownWords.put(card, false));
    }

    public List<Card> getTestDictionary() {
        return testDictionary;
    }

    public Map<Card, Boolean> getShownWords() {
        return shownWords;
    }

    public Iterator<Card> getKeyIterator() {
        return keyIterator;
    }

    public void setLastDisplayed(Card card) {
        lastDisplayed = card;
    }

    public Card getLastDisplayed() {
        return lastDisplayed;
    }
}