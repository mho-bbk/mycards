package com.example.mycards.usecases.createdeck;

import androidx.lifecycle.LiveData;

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
public class CreateDeckUseCase implements BaseUseCaseWithParamWithOutReturn<List<String>> {

    private final DeckRepository deckRepository;

    @Inject
    public CreateDeckUseCase(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    @Override
    public void run(List<String> strings) {
        Deck deck = new Deck(strings);
        upsert(deck);
    }

    //**REPOSITORY/DAO METHODS**
    public void upsert(Deck deck) {
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
