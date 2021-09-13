package com.example.mycards.usecases.createcards;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mycards.base.usecasetypes.BaseUseCaseWithParam;
import com.example.mycards.data.entities.Card;
import com.example.mycards.data.repositories.CardRepository;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

/**
 * Remit:
 *  + Takes a HashMap of inputString : relatedWordsMap(eng : jp)
 *  + Create Cards using the HashMap
 *  + Inserts Cards into the CardRepo (persists Cards)
 *  + Returns True if Cards were inserted; False if no Cards were inserted
 *
 *  + Public gateway to the methods of the card repository
 */
public class CreateAndGetCardUseCase implements BaseUseCaseWithParam<HashMap<String, HashMap<String, String>>,
        Boolean> {

    private static final String TAG = "CreateAndGetCardUseCase";
    private final CardRepository cardRepository;

    @Inject
    public CreateAndGetCardUseCase(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Boolean run(HashMap<String, HashMap<String, String>> stringHashMapString) {
        return createCards(stringHashMapString);
    }

    //This is the run method of this use case really
    private boolean createCards(HashMap<String, HashMap<String, String>> map) {
        AtomicBoolean returnValue = new AtomicBoolean(false);

        map.entrySet().forEach(entry -> {
            String inputWord = entry.getKey();
            HashMap<String, String> engToJpPerInputWord = entry.getValue();
            if(engToJpPerInputWord.isEmpty()) {
                //do nothing, return value already false by default
                Log.d(TAG, "No successful words found in jmdict for input " + entry.getKey()
                        + " or its related words");
            } else {
                engToJpPerInputWord.entrySet().forEach(innerEntry -> {
                    cardRepository.upsert(new Card(innerEntry.getKey(), innerEntry.getValue(), inputWord));
                });
                returnValue.set(true);
            }
        });

        return returnValue.get();
    }

    //**REPOSITORY/DAO METHODS**
    public LiveData<List<Card>> getAllCards() { return cardRepository.getAllCards(); }

    public LiveData<List<Card>> getCards(String relatedWord) {
        return cardRepository.getCards(relatedWord);
    }

    public LiveData<List<Card>> getCards(List<String> relatedWords) {
        return cardRepository.getCards(relatedWords);
    }

    public void upsert(Card card) {
        cardRepository.upsert(card);
    }

    public void delete(Card card) {
        cardRepository.delete(card);
    }

    public void deleteAllCards() {
        cardRepository.deleteAllCards();
    }

    public boolean containsCardsFor(String word) { return cardRepository.containsCardsFor(word); }

    public boolean containsCardsFor(List<String> words) { return cardRepository.containsCardsFor(words); }
}
