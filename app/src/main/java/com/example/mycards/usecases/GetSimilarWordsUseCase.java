package com.example.mycards.usecases;

import com.example.mycards.base.usecasetypes.BaseUseCaseWithParam;
import com.example.mycards.datamuse.DatamuseAPIService;
import com.example.mycards.datamuse.pojo.DatamuseWord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Remit:
 *  + Accept a String input from the VM - assumes String is correctly spelled, assumes one String input (no collection)
 *  + Get similar words via Remote Source (DatamuseAPI)
 *  + Convert DatamuseWord type into List of String for processing
 *  + Return those words to the client (VM)
 */
public class GetSimilarWordsUseCase implements BaseUseCaseWithParam<String, List<String>> {
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    private DatamuseAPIService datamuseAPIService;
    private List<DatamuseWord> datamuseWords = new ArrayList<>();

    @Inject
    public GetSimilarWordsUseCase(DatamuseAPIService datamuseAPIService) {
        this.datamuseAPIService = datamuseAPIService;
    }

    @Override
    public List<String> run(String param) {
        boolean foundWords = semanticSearch(param);
        if(foundWords) {
            return convertDatamuseWordsToStrings(datamuseWords);
        } else {
            List<String> dummyList = new ArrayList<>();
            return dummyList;
        }
    }

    private boolean semanticSearch(String s) {
        Call<List<DatamuseWord>> call = datamuseAPIService
                .getMaxSingleSearchResults(s, 3);   //later user should be able to set the max


        //Makes sure this doesn't happen on the main thread
//        call.enqueue(new Callback<List<DatamuseWord>>() {
//            @Override
//            public void onResponse(Call<List<DatamuseWord>> call, Response<List<DatamuseWord>> response) {
//                if(response.isSuccessful()) {
//                    //set the return results here
//                    datamuseWords = response.body();
//                } else {
//                    System.out.println(response.errorBody());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<DatamuseWord>> call, Throwable t) {
//                System.out.println("An error occurred with semanticSearch() method");
//                t.printStackTrace();
//            }
//        });

        //implement Retrofit on blocking thread as need results immediately
        executorService.execute(() -> {
            try {
                Response<List<DatamuseWord>> words = call.execute();
                datamuseWords = words.body();
                System.out.println(datamuseWords);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return true;
    }

    private List<String> convertDatamuseWordsToStrings(List<DatamuseWord> datamuseWords) {
        List<String> strings = new ArrayList<>();
        datamuseWords.forEach(dmWord -> strings.add(dmWord.getWord()));
        return strings;
    }
}
