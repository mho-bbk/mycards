package com.example.mycards.usecases.createdeck;

import androidx.lifecycle.LiveData;

import com.example.mycards.base.usecasetypes.BaseUseCaseWithParam;
import com.example.mycards.base.usecasetypes.BaseUseCaseWithParamWithOutReturn;
import com.example.mycards.data.entities.Deck;
import com.example.mycards.data.repositories.DeckRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Remit:
 * + Takes userInput as List<String> and uses this to create the Deck, inserting Deck into the db
 *
 * + Provides public access to deck repository methods
 */
public class CreateDeckUseCase implements BaseUseCaseWithParam<List<String>, Boolean> {

    private final DeckRepository deckRepository;

    @Inject
    public CreateDeckUseCase(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    @Override
    public Boolean run(List<String> strings) {
        Deck deck = new Deck(strings);
        if(isValid(deck)) {
            upsert(deck);
            return true;
        } else {
            return false;
        }
    }

    //Helper method to implement definition of a valid deck name
    private boolean isValid(Deck deck) {
        if("".equals(deck.getDeckName())) {
            return false;
        } else {
            return true;
        }
    }

    //**REPOSITORY/DAO METHODS**

    //Private so you can only upsert after the use case validates the Deck
    private void upsert(Deck deck) {
        deckRepository.upsert(deck);
    }

    public void delete(Deck deck) {
        deckRepository.delete(deck);
    }

    public LiveData<List<Deck>> getAllDecks() {
        return deckRepository.getAllDecks();
    }

    public void deleteAllDecks() {
        deckRepository.deleteAllDecks();
    }
}
