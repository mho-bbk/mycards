package com.example.mycards.usecases;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.mycards.base.callbacks.Result;
import com.example.mycards.data.entities.Card;
import com.example.mycards.usecases.helper.CurrentThreadExecutor;
import com.example.mycards.usecases.createcards.CreateAndGetCardUseCase;
import com.example.mycards.usecases.jptranslate.GetJpWordsUseCase;
import com.example.mycards.usecases.semanticsearch.GetSimilarWordsUseCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.doNothing;

public class UseCaseManagerTest {

    private UseCaseManager useCaseManager;

    private GetSimilarWordsUseCase getSimilarWordsUseCase;
    private GetJpWordsUseCase getJpWordsUseCase;
    private CreateAndGetCardUseCase createAndGetCardUseCase;
    private ExecutorService executorService = new CurrentThreadExecutor();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        getSimilarWordsUseCase = Mockito.mock(GetSimilarWordsUseCase.class);
        getJpWordsUseCase = Mockito.mock(GetJpWordsUseCase.class);
        createAndGetCardUseCase = Mockito.mock(CreateAndGetCardUseCase.class);

        useCaseManager = UseCaseManager.getInstance(getSimilarWordsUseCase, getJpWordsUseCase, createAndGetCardUseCase, executorService);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void runAllUseCases() {
        //TODO
    }
}