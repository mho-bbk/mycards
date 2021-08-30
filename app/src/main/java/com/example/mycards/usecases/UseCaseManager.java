package com.example.mycards.usecases;

import android.os.Build;
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

    private final String TAG = "UseCaseManager";
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
    public void runAllUseCases(List<String> inputList, UseCaseCallback<Boolean> callback) {
        executorService.execute(() -> {
            try {
                Result<Boolean> result = runAllUseCasesSynchronously(inputList);
                callback.onComplete(result);
            } catch (Exception e) {
                Result<Boolean> errorResult = new Result.Error<>(e);
                callback.onComplete(errorResult);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private Result<Boolean> runAllUseCasesSynchronously(List<String> inputList) {
        return runSimilarWordsUseCaseSynchronously(inputList); //could be null

        //Run the below if testing on emulator to avoid having to do network call constantly
//        HashMap<String, List<String>> words = new HashMap<>();
//        words.put("to speed up", List.of("to fail to notice", "inspection"));
//        words.put("to fail to notice", List.of("to speed up", "inspection"));
//        words.put("inspection", List.of("to speed up", "to fail to notice"));
//
//        return runGetJPWordsUseCaseSynchronously(words);    //could be null
    }

    private Result<Boolean> runSimilarWordsUseCaseSynchronously(List<String> inputList) {
        try {
            //semantic search using DatamuseAPI
            Future<HashMap<String, List<String>>> semanticSearch =
                    executorService.submit(() -> getSimilarWordsUseCase.run(inputList));
            try {
                HashMap<String, List<String>> wordsAndRelatedWords = semanticSearch.get();

                return runGetJPWordsUseCaseSynchronously(wordsAndRelatedWords);

            } catch (ExecutionException | InterruptedException e) {
                Log.d(TAG, "Exception during semanticSearch.get()");
                return new Result.Error<>(e);
            }
        } catch (Exception e) {
            //catch any exception, probably IO from failed network call
            return new Result.Error<>(e);
        }
    }

    private Result<Boolean> runGetJPWordsUseCaseSynchronously(HashMap<String, List<String>> wordsAndRelatedWords) {

        Future<HashMap<String, HashMap<String, String>>> engToJpReady =
                executorService.submit(() -> getJpWordsUseCase.run(wordsAndRelatedWords));
        try {
            HashMap<String, HashMap<String, String>> engToJpMap = engToJpReady.get();
            return runCreateAndGetCardUseCaseSynchronously(engToJpMap);

        } catch (ExecutionException | InterruptedException e) {
            Log.d(TAG, "Exception during engToJpReady.get()");
            return new Result.Error<>(e);
        }
    }

    private Result<Boolean> runCreateAndGetCardUseCaseSynchronously(HashMap<String, HashMap<String, String>> engToJpMap) {
        Future<Boolean> cardsReady =
                executorService.submit(() -> createAndGetCardUseCase.run(engToJpMap));
        try {
            Boolean ready = cardsReady.get();
            return new Result.Success<>(ready); //True/False but never null
        } catch (Exception e) {
            Log.d(TAG, "Exception during cardsReady.get()");
            return new Result.Error<>(e);
        }
    }

    //Access point: VM -> this -> cardUseCase -> Repository -> Dao -> DB
    public LiveData<List<Card>> getCards(List<String> inputList) {
        return createAndGetCardUseCase.getCards(inputList);
    }

    public void deleteAllCards() {
        createAndGetCardUseCase.deleteAllCards();
    }
}
