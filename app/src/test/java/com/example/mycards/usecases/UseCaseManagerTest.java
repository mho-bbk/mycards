package com.example.mycards.usecases;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.mycards.base.callbacks.Result;
import com.example.mycards.base.callbacks.UseCaseCallback;
import com.example.mycards.usecases.createdeck.CreateDeckUseCase;
import com.example.mycards.utility.CurrentThreadExecutor;
import com.example.mycards.usecases.createcards.CreateAndGetCardUseCase;
import com.example.mycards.usecases.jptranslate.GetJpWordsUseCase;
import com.example.mycards.usecases.semanticsearch.GetSimilarWordsUseCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UseCaseManagerTest {

    private UseCaseManager useCaseManager;

    private GetSimilarWordsUseCase getSimilarWordsUseCase;
    private GetJpWordsUseCase getJpWordsUseCase;
    private CreateAndGetCardUseCase createAndGetCardUseCase;
    private CreateDeckUseCase createDeckUseCase;
    private ExecutorService executorService = new CurrentThreadExecutor();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule(); //stay on the main thread

    @Before
    public void setUp() {
        getSimilarWordsUseCase = Mockito.mock(GetSimilarWordsUseCase.class);
        getJpWordsUseCase = Mockito.mock(GetJpWordsUseCase.class);
        createAndGetCardUseCase = Mockito.mock(CreateAndGetCardUseCase.class);
        createDeckUseCase = Mockito.mock(CreateDeckUseCase.class);

        useCaseManager = UseCaseManager.getInstance(getSimilarWordsUseCase,
                getJpWordsUseCase, createAndGetCardUseCase, createDeckUseCase, executorService);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testCheckCardRepo_UnsearchedWordsEmpty() {
        //Indirectly tests private checkCardRepo() method via the public checkInputListThenRun() method
        //Indirectly calls private createDeck() method - but this method is actually tested in CreateDeckUseCaseTest

        //Simulate setting where cards are already in the db
        when(createAndGetCardUseCase.containsCardsFor(anyString())).thenReturn(true);

        //Create fake inputs
        List<String> fakeInputList = new ArrayList<>(List.of("chef", "cooking", "science"));

        //Simulates the callback in SharedViewModel
        UseCaseCallback<Boolean> testUseCaseCallbackSuccessful = new UseCaseCallback<Boolean>() {
            @Override
            public void onComplete(Result<Boolean> result) {
                assertTrue(result instanceof Result.Success);   //as opposed to Result.Error
                assertEquals(true, ((Result.Success<?>) result).getData());
            }
        };

        useCaseManager.checkInputListThenRun(fakeInputList, testUseCaseCallbackSuccessful);
    }

    @Test
    public void testCheckCardRepo_NonEmptyUnsearchedWords() {
        //Indirectly tests private checkCardRepo() method via the public checkInputListThenRun() method

        //Capture the List<String that goes into runAllUseCases()
        ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
        //Simulate the callback in SharedViewModel
        UseCaseCallback<Boolean> testUseCaseCallback = new UseCaseCallback<Boolean>() {
            @Override
            public void onComplete(Result<Boolean> result) {
                //Nothing happens because use case objects are mocked (not real)
            }
        };

        //Create fake inputs
        List<String> fakeInputList = new ArrayList<>(List.of("chef", "cooking", "science"));

        //Simulate setting where some cards are not in the db
        //Chef is in db, other words are not
        when(createAndGetCardUseCase.containsCardsFor(eq("chef"))).thenReturn(true);
        when(createAndGetCardUseCase.containsCardsFor(eq("cooking"))).thenReturn(false);
        when(createAndGetCardUseCase.containsCardsFor(eq("science"))).thenReturn(false);

        useCaseManager.checkInputListThenRun(fakeInputList, testUseCaseCallback);

        //TODO - improve this - not very legible
        //The private method runAllUseCases() within checkInputListThenRun calls similarWordsUseCase
        //So we capture the List<String> argument there to examine it
        verify(getSimilarWordsUseCase).run(listCaptor.capture());

        assertEquals(2, listCaptor.getValue().size());
        assertTrue(listCaptor.getValue().contains("cooking"));
        assertTrue(listCaptor.getValue().contains("science"));
        assertFalse(listCaptor.getValue().contains("chef"));

    }

    @Test
    public void testWhenException() {
        //TODO
    }
}