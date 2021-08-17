package com.example.mycards.usecases;

import android.util.Log;

import com.example.mycards.base.usecasetypes.BaseUseCaseWithParam;
import com.example.mycards.datamuse.DatamuseAPIService;
import com.example.mycards.datamuse.pojo.DatamuseWord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Remit:
 *  + Accept a String input from the VM - assumes String is correctly spelled, assumes one String input (no collection)
 *  + Get similar words via Remote Source (DatamuseAPI)
 *  + Convert DatamuseWord type into List of String for processing
 *  + Return those words to the client (VM)
 */
public class GetSimilarWordsUseCase implements BaseUseCaseWithParam<String, List<String>> {

    ExecutorService simWordUseCaseExecutor = Executors.newSingleThreadExecutor();
    ExecutorService threadPool = Executors.newFixedThreadPool(3);   //trying to use more threads to see if there's a diff...

    private static final String TAG = "GetSimilarWordsUseCase";

    private final DatamuseAPIService datamuseAPIService;
    private int searchLimit = 4;    //4 is default
    private List<DatamuseWord> datamuseWords = new ArrayList<>();
    private List<String> originalAndSimilarWords = new ArrayList<>();

    @Inject
    public GetSimilarWordsUseCase(DatamuseAPIService datamuseAPIService) {
        this.datamuseAPIService = datamuseAPIService;
    }

    @Override
    public List<String> run(String param) {
        //Original String should be included in the deck, so add to List
        originalAndSimilarWords.add(param);

        //Create the call
        Call<List<DatamuseWord>> maxSingleSearchCall = createMaxSingleSearchCall(
                datamuseAPIService, param, searchLimit);

        //Run the semantic search (execute the call on a bg thread)
        if(semanticSearch(maxSingleSearchCall)) {
            originalAndSimilarWords.addAll(convertDatamuseWordsToStrings(datamuseWords));
            return originalAndSimilarWords;
        } else {
            return new ArrayList<>();
        }
    }

    public boolean semanticSearch(Call<List<DatamuseWord>> call) {
        //implement Retrofit on blocking thread as need results immediately
        //but remote API calls should not be made on main thread, so use Executor
        long startTime = System.nanoTime(); //measure starttime

        Future<?> searchSimilarWords = threadPool.submit(() -> {
            try {
                Response<List<DatamuseWord>> words = call.execute();

                if(words.isSuccessful()) {
                    datamuseWords = words.body();
                    Log.d(TAG, "Got datamuseWords: " + datamuseWords);
                } else {
                    Log.d(TAG, "Datamuse call unsuccessful, datamuseWords will be empty");
                }

            } catch (IOException e) {
                Log.d(TAG, "IOException with semanticSearch() method: ");
                e.printStackTrace();
            }
        });

        try {
            searchSimilarWords.get();

            long endTime = System.nanoTime();   //measure endtime
            long duration = (endTime - startTime) / 1000000;  //calculate duration in ms
            Log.d(TAG, Thread.currentThread().getName() + " has finished. DatamuseWords populated in " + duration);

            return true;
        } catch (ExecutionException e) {
            Log.d(TAG, "ExecutionException in semanticSearch() method: ");
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.d(TAG, "InterruptedException in semanticSearch() method: ");
            e.printStackTrace();
        }

        return false;
    }

    public void setSearchLimit(int n) { this.searchLimit = n; }

    public List<String> getDatamuseWords() {
        if(!datamuseWords.isEmpty()) {
            return convertDatamuseWordsToStrings(datamuseWords);
        } else {
            List<String> dummyList = new ArrayList<>();
            return dummyList;
        }
    }

    //Helper method to disaggregate Call from semanticSearch to make the latter testable
    private Call<List<DatamuseWord>> createMaxSingleSearchCall(DatamuseAPIService service,
                                                               String searchTerm, int upToThisInt) {
        return service.getMaxSingleSearchResults(searchTerm, upToThisInt);
    }

    private List<String> convertDatamuseWordsToStrings(List<DatamuseWord> datamuseWords) {
        List<String> strings = new ArrayList<>();
        datamuseWords.forEach(dmWord -> strings.add(dmWord.getWord()));
        return strings;
    }
}
