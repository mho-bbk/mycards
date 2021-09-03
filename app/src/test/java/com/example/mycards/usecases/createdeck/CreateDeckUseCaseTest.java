package com.example.mycards.usecases.createdeck;

import com.example.mycards.data.entities.Deck;
import com.example.mycards.data.repositories.DeckRepository;
import com.example.mycards.data.repositories.DefaultDeckRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;

public class CreateDeckUseCaseTest {

    private DeckRepository mockedDeckRepository;
    private CreateDeckUseCase createDeckUseCase;

    @Before
    public void setUp() {
        //Mock the repository
        mockedDeckRepository = Mockito.mock(DefaultDeckRepository.class);
        //Instantiate the use case
        createDeckUseCase = new CreateDeckUseCase(mockedDeckRepository);
    }

    @Test
    public void testUpsertPassesValidDeckName() {
        //Capture the argument to test the upsert using mocked repository class
        ArgumentCaptor<Deck> deckCaptor = ArgumentCaptor.forClass(Deck.class);
        doNothing().when(mockedDeckRepository).upsert(deckCaptor.capture());

        //Create fake input
        List<String> fakeInput = new ArrayList<>(List.of("chef", "sport", "art"));

        //Create expected Deck that would be upserted
        Deck expectedDeck = new Deck("chef, sport, art");

        Boolean resultOfRun = createDeckUseCase.run(fakeInput);
        assertEquals(true, resultOfRun);
        assertEquals(expectedDeck, deckCaptor.getValue());
    }

    @Test
    public void testUpsertFailsInvalidDeckName() {
        //Create fake input
        List<String> fakeInput = new ArrayList<>();

        //We expect private method isValid() to prevent upsert so run returns false
        Boolean resultOfRun = createDeckUseCase.run(fakeInput);
        assertEquals(false, resultOfRun);
    }

}