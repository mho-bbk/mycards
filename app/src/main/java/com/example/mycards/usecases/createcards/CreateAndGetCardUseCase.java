package com.example.mycards.usecases.createcards;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mycards.base.usecasetypes.BaseUseCaseWithParam;
import com.example.mycards.data.entities.Card;
import com.example.mycards.data.repositories.CardRepository;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Remit:
 *  + Takes a HashMap of eng:jp words
 *  + Create Cards using the HashMap
 *  + Inserts cards into the CardRepo for persistence
 *  + Assigns all cards represented by the hashmap with one 'deck' seed word
 *
 *  + Gives public gateway to the methods of the card repository, so VM can use it for UI
 *
 *  //Possibly another usecase...
 *  + Assumption that VM can get the deckSeed from here.
 *      If not, VM must already knows deckSeed (as passed down as userinput)
 */
public class CreateAndGetCardUseCase implements BaseUseCaseWithParam<HashMap<String, HashMap<String, String>>,
        Boolean> {

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
        map.entrySet().forEach( entry -> {
            String inputWord = entry.getKey();
            HashMap<String, String> engToJpPerInputWord = entry.getValue();
            engToJpPerInputWord.entrySet().forEach( innerEntry -> {
                cardRepository.upsert(new Card(innerEntry.getKey(), innerEntry.getValue(), inputWord));
            });
        });

        return true;
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
}
