package com.example.mycards.usecases;

import com.example.mycards.data.entities.Card;
import com.example.mycards.data.repositories.CardRepository;
import com.example.mycards.data.repositories.DefaultCardRepository;
import com.example.mycards.usecases.createcards.CreateAndGetCardUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CreateAndGetCardUseCaseTest {

    private CardRepository mockedCardRepository;
    private CreateAndGetCardUseCase createAndGetCardUseCase;

    @Before
    public void setUp() {
        //Mock the repository
        mockedCardRepository = Mockito.mock(DefaultCardRepository.class);
        //Instantiate the use case
        createAndGetCardUseCase = new CreateAndGetCardUseCase(mockedCardRepository);
    }

    @Test
    public void testRunPasses_SingleUpsert() {
        //Capture the argument to test the upsert using mocked repository class
        ArgumentCaptor<Card> cardCaptor = ArgumentCaptor.forClass(Card.class);
        doNothing().when(mockedCardRepository).upsert(cardCaptor.capture());

        //Create fake input, single entry in innerHashMap
        HashMap<String, HashMap<String, String>> fakeInput = new HashMap<>();
        HashMap<String, String> innerHashMap = new HashMap<>();
        innerHashMap.put("teacher", "先生 (せんせい, sensei)");
        fakeInput.put("teacher", innerHashMap);

        //Create expected Card that would be upserted
        Card expectedCard = new Card("teacher", "先生 (せんせい, sensei)", "teacher");

        Boolean resultOfRun = createAndGetCardUseCase.run(fakeInput);
        assertEquals(true, resultOfRun);
        assertEquals(expectedCard, cardCaptor.getValue());
    }

    @Test
    public void testRunPasses_MultiUpsert() {
        //Create the ArgumentCaptor which will help us to test the upsert using mocked repository class
        ArgumentCaptor<Card> cardCaptor = ArgumentCaptor.forClass(Card.class);

        //Create fake input, multiple entries in innerHashMap
        HashMap<String, HashMap<String, String>> fakeInput = new HashMap<>();
        HashMap<String, String> innerHashMap = new HashMap<>();
        innerHashMap.put("teacher", "先生 (せんせい, sensei)");
        innerHashMap.put("instructor", "インストラクター (insutorakuta-)");
        fakeInput.put("teacher", innerHashMap);

        //Create Card outcomes expected in the ArgumentCaptor
        Card expectedCard1 = new Card("teacher", "先生 (せんせい, sensei)", "teacher");
        Card expectedCard2 = new Card("instructor", "インストラクター (insutorakuta-)", "teacher");

        Boolean resultOfRun = createAndGetCardUseCase.run(fakeInput);
        assertEquals(true, resultOfRun);

        verify(mockedCardRepository, times(2)).upsert(cardCaptor.capture());
        List<Card> capturedCards = cardCaptor.getAllValues();
        assertEquals(expectedCard1, capturedCards.get(0));
        assertEquals(expectedCard2, capturedCards.get(1));
    }

    @Test
    public void testRun_NoInnerHashMap_returnsFalse() {
        //Eg when there are no translations found in the jmdict local db
        HashMap<String, HashMap<String, String>> jmdictDbResults = new HashMap<>();
        jmdictDbResults.put("nonsensical string", new HashMap<>());

        Boolean resultOfRun = createAndGetCardUseCase.run(jmdictDbResults);
        assertEquals(false, resultOfRun);
    }

}