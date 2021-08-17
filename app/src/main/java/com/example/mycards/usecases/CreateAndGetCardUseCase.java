package com.example.mycards.usecases;

import androidx.lifecycle.LiveData;

import com.example.mycards.base.usecasetypes.BaseUseCaseWithParam;
import com.example.mycards.data.entities.Card;
import com.example.mycards.data.repositories.CardRepository;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private CardRepository cardRepository;
    private String deckSeed = ""; //this should be the user's original input (string(s))

    @Inject
    public CreateAndGetCardUseCase(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Boolean run(HashMap<String, String> param) {
        return createCards(param);
    }

    private Boolean createCards(HashMap<String, String> param) {
        param.entrySet().forEach(entry -> {
            cardRepository.upsert(new Card(entry.getKey(), entry.getValue(), deckSeed));
        });
        //Assume the above will throw exception and stop, or return true
        return true;
    }

    public String getDeckSeed() {
        return deckSeed;
    }

    //VM needs to use this to either directly add user's original input String
    //or VM can get the input string from GetSimilarWordsUseCase
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
