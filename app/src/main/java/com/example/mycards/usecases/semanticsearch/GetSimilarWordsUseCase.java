package com.example.mycards.usecases.semanticsearch;

import android.util.Log;

import com.example.mycards.base.usecasetypes.BaseUseCaseWithParam;
import com.example.mycards.server.datamuse.DatamuseAPIService;
import com.example.mycards.server.datamuse.pojo.DatamuseWord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Remit:
 *  + Accept List<String> of user input from the VM. Assumes Strings are correctly spelled.
 *  + Get similar words via Remote Source (DatamuseAPI)
 *  + Converts JSON -> DatamuseWord -> String
 *  + Returns to client HashMap where Key is the input String and Value is a List of related Strings
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
            //for each input word, create the call, call the service
            Call<List<DatamuseWord>> call = createMaxSingleSearchCall(datamuseAPIService, word, searchLimit);
            List<String> results = callService(call, word);
            //get the words from the service and add to originalAndSimilarWords
            if(!results.isEmpty()) {
                wordsAndRelatedWords.put(word, results);
            }
        });

        return wordsAndRelatedWords; //null is captured in callService, but results can be empty
    }

    //Helper method. Calls the API service with individual Strings.
    private List<String> callService(Call<List<DatamuseWord>> call, String searchTerm) {

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

    //Helper method. Decouple call creation from call execute for testability.
    private Call<List<DatamuseWord>> createMaxSingleSearchCall(DatamuseAPIService service,
                                                               String searchTerm, int upToThisInt) {
        return service.getMaxSingleSearchResults(searchTerm, upToThisInt);
    }

    //Helper method. Gets embedded Strings from DatamuseWord class.
    private List<String> convertDatamuseWordsToStrings(List<DatamuseWord> datamuseWords) {
        List<String> strings = new ArrayList<>();
        datamuseWords.forEach(dmWord -> strings.add(dmWord.getWord()));
        return strings;
    }

    //TODO - Future feature
    //Issue? This is a global field atm, changing this would make it change for all the words input...?
    //Would need to inject this via the run() constructor?
    public void setSearchLimit(int n) { this.searchLimit = n; }
}
