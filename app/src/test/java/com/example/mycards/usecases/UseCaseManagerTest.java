package com.example.mycards.usecases;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.mycards.usecases.createdeck.CreateDeckUseCase;
import com.example.mycards.utility.CurrentThreadExecutor;
import com.example.mycards.usecases.createcards.CreateAndGetCardUseCase;
import com.example.mycards.usecases.jptranslate.GetJpWordsUseCase;
import com.example.mycards.usecases.semanticsearch.GetSimilarWordsUseCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.concurrent.ExecutorService;

public class UseCaseManagerTest {

    private UseCaseManager useCaseManager;

    private GetSimilarWordsUseCase getSimilarWordsUseCase;
    private GetJpWordsUseCase getJpWordsUseCase;
    private CreateAndGetCardUseCase createAndGetCardUseCase;
    private CreateDeckUseCase createDeckUseCase;
    private ExecutorService executorService = new CurrentThreadExecutor();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

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
    public void testRunAllUseCases() {
        //TODO
    }

    @Test
    public void testCheckCardRepo() {

    }
}