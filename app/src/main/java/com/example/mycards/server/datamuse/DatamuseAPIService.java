package com.example.mycards.server.datamuse;

import com.example.mycards.server.datamuse.pojo.DatamuseWord;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DatamuseAPIService {

    String BASE_URL = "https://api.datamuse.com/";

    @GET("words")
    Call<List<DatamuseWord>> getAllSingleSearchResults(@Query("ml") String searchTerm);

    @GET("words")
    Call<List<DatamuseWord>> getMaxSingleSearchResults(@Query("ml") String searchTerm,
                                                       @Query("max") int size);
}
