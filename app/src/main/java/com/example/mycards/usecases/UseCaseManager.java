package com.example.mycards.usecases;

import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.mycards.base.callbacks.Result;
import com.example.mycards.base.callbacks.UseCaseCallback;
import com.example.mycards.data.entities.Card;
import com.example.mycards.usecases.createcards.CreateAndGetCardUseCase;
import com.example.mycards.usecases.jptranslate.GetJpWordsUseCase;
import com.example.mycards.usecases.semanticsearch.GetSimilarWordsUseCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


public class UseCaseManager {

    private String TAG = "UseCaseManager";
    private static UseCaseManager INSTANCE;

    private final GetSimilarWordsUseCase getSimilarWordsUseCase;
    private final GetJpWordsUseCase getJpWordsUseCase;
    private final CreateAndGetCardUseCase createAndGetCardUseCase;
    private final ExecutorService executorService;

    private UseCaseManager(GetSimilarWordsUseCase getSimilarWordsUseCase,
                           GetJpWordsUseCase getJpWordsUseCase,
                           CreateAndGetCardUseCase createAndGetCardUseCase,
                           ExecutorService executorService) {
        this.getSimilarWordsUseCase = getSimilarWordsUseCase;
        this.getJpWordsUseCase = getJpWordsUseCase;
        this.createAndGetCardUseCase = createAndGetCardUseCase;
        this.executorService = executorService;

    }

    //implement as a Singleton
    public static UseCaseManager getInstance(GetSimilarWordsUseCase getSimilarWordsUseCase,
                                             GetJpWordsUseCase getJpWordsUseCase,
                                             CreateAndGetCardUseCase createAndGetCardUseCase,
                                             ExecutorService executorService) {
        //lazy instantiation
        if(INSTANCE == null) {
            INSTANCE = new UseCaseManager(getSimilarWordsUseCase,
                    getJpWordsUseCase,
                    createAndGetCardUseCase,
                    executorService);
        }
        return INSTANCE;
    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    public void runAllUseCases(List<String> inputList, String deckSeed, UseCaseCallback<Boolean> callback) {
        executorService.execute(() -> {
            try {
                Result<Boolean> result = runAllUseCasesSynchronously(inputList, deckSeed);
                callback.onComplete(result);
            } catch (Exception e) {
                Result<Boolean> errorResult = new Result.Error<>(e);
                callback.onComplete(errorResult);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private Result<Boolean> runAllUseCasesSynchronously(List<String> inputList, String deckSeed) {
        Boolean ready;
        //TODO - split these use cases up into separate methods
        try {
            createAndGetCardUseCase.setDeckSeed(deckSeed); //TODO - bit of an anomaly here
            //semantic search using DatamuseAPI
            Future<List<String>> semanticSearch = executorService.submit(
                    () -> getSimilarWordsUseCase.run(inputList));
            try {
                List<String> words = semanticSearch.get();
                //Test so we can stop needlessly calling API...
//                List<String> words = new ArrayList<>(List.of("to speed up", "to fail to notice", "inspection"));

                Future<HashMap<String, String>> engToJpReady = executorService.submit(
                        () -> getJpWordsUseCase.run(words));
                try {
                    HashMap<String, String> engToJpMap = engToJpReady.get();

                    Future<Boolean> cardsReady = executorService.submit(
                            () -> createAndGetCardUseCase.run(engToJpMap));
                    try {
                        ready = cardsReady.get();
                    } catch (Exception e) {
                        Log.d(TAG, "Exception during cardsReady.get()");
                        return new Result.Error<>(e);
                    }
                } catch (ExecutionException | InterruptedException e) {
                    Log.d(TAG, "Exception during engToJpReady.get()");
                    return new Result.Error<>(e);
                }
            } catch (ExecutionException | InterruptedException e) {
                Log.d(TAG, "Exception during semanticSearch.get()");
                return new Result.Error<>(e);
            }
        } catch (Exception e) {
            //catch any exception
            return new Result.Error<>(e);
        }
        return new Result.Success<>(ready);
    }

    //Access point: VM -> this -> cardUseCase -> Repository -> Dao -> DB
    public LiveData<List<Card>> getCards(String deckSeed) {
        return createAndGetCardUseCase.getCards(deckSeed);
    }

    public List<Card> getCardsNotLive(String deckSeed) {
        return createAndGetCardUseCase.getCardsNotLive(deckSeed);
    }

    public void deleteAllCards() {
        createAndGetCardUseCase.deleteAllCards();
    }
}
