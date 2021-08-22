package com.example.mycards.usecases.semanticsearch;

import android.util.Log;

import com.example.mycards.base.usecasetypes.BaseUseCaseWithParam;
import com.example.mycards.server.datamuse.DatamuseAPIService;
import com.example.mycards.server.datamuse.pojo.DatamuseWord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
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
public class GetSimilarWordsUseCase implements BaseUseCaseWithParam<List<String>, HashMap<String, List<String>>> {

    private static final String TAG = "GetSimilarWordsUseCase";

    private final DatamuseAPIService datamuseAPIService;
    private int searchLimit = 4;    //4 is default as plus original Word = 5 cards total

    @Inject
    public GetSimilarWordsUseCase(DatamuseAPIService datamuseAPIService) {
        this.datamuseAPIService = datamuseAPIService;
    }

    @Override
    public HashMap<String, List<String>> run(List<String> inputWords) {
        HashMap<String, List<String>> wordsAndRelatedWords = new HashMap<>();

        inputWords.forEach(word -> {
            //for each input word, call the service and search
            //get the words from the service and add to originalAndSimilarWords
            List<String> results = callService(datamuseAPIService, word, searchLimit);
            if(!results.isEmpty()) {
                wordsAndRelatedWords.put(word, results);
            }
        });

        return wordsAndRelatedWords; //null is captured in callService, but results can be empty
    }

    private List<String> callService(DatamuseAPIService service, String searchTerm, int searchLimit) {
        Call<List<DatamuseWord>> call = createMaxSingleSearchCall(
                service, searchTerm, searchLimit);

        List<String> apiSearchResult = new ArrayList<>();

        try {
            Response<List<DatamuseWord>> callWords = call.execute();
            List<DatamuseWord> datamuseWords = callWords.body();
            if(datamuseWords != null) {
                apiSearchResult = convertDatamuseWordsToStrings(datamuseWords);
                Log.d(TAG, "Got datamuseWords: " + datamuseWords);
            } else {
                Log.d(TAG, "API service executed successfully but no results retrieved");
            }
        } catch (Exception e) {
            Log.d(TAG, "Calling API service failed for " + searchTerm);
            e.printStackTrace();
        }

        return apiSearchResult;
    }

    //For future feature
    public void setSearchLimit(int n) { this.searchLimit = n; }

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
