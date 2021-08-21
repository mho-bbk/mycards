package com.example.mycards.usecases.createcards;

import androidx.lifecycle.LiveData;

import com.example.mycards.base.usecasetypes.BaseCaseWithoutParamWithoutReturn;
import com.example.mycards.base.usecasetypes.BaseUseCaseWithOutParams;
import com.example.mycards.base.usecasetypes.BaseUseCaseWithParam;
import com.example.mycards.base.usecasetypes.BaseUseCaseWithParamWithOutReturn;
import com.example.mycards.data.entities.Card;
import com.example.mycards.data.repositories.CardRepository;
import com.example.mycards.usecases.jptranslate.GetJpWordsUseCase;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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
public class CreateAndGetCardUseCase implements BaseUseCaseWithParam<HashMap<String, String>, Boolean> {

    private final CardRepository cardRepository;

    private String deckSeed = ""; //this should be the user's original input (string(s))

    @Inject
    public CreateAndGetCardUseCase(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Boolean run(HashMap<String, String> stringStringHashMap) {
        return createCards(stringStringHashMap);
    }

    //This is the run method of this use case really
    private boolean createCards(HashMap<String, String> param) {
        param.entrySet().forEach(entry -> {
            cardRepository.upsert(new Card(entry.getKey(), entry.getValue(), deckSeed));
        });

        resetDeckSeed(); //TODO - needed?
        return true;
    }

    //Manager uses this to add user's original input String as deck identified
    public void setDeckSeed(String deckSeed) {
        this.deckSeed = deckSeed;
    }

    public void resetDeckSeed() {
        this.deckSeed = "";
    }


    //**REPOSITORY/DAO METHODS**
    public LiveData<List<Card>> getAllCards() { return cardRepository.getAllCards(); }

    public LiveData<List<Card>> getCards(String deckSeed) {
        return cardRepository.getCards(deckSeed);
    }

    public List<Card> getCardsNotLive(String deckSeed) {
        return cardRepository.getCardsNotLive(deckSeed);
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
