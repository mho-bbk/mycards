package com.example.mycards.datamuse;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Loosely based on this tutorial: https://www.section.io/engineering-education/making-api-requests-using-retrofit-android/
 */
public class DatamuseClient {

    private static DatamuseClient INSTANCE; //Singleton
    private DatamuseAPIService datamuseAPIService;

    private DatamuseClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DatamuseAPIService.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        //Use the retrofit to instantiate the DatamuseAPIService
        datamuseAPIService = retrofit.create(DatamuseAPIService.class);
    }

    public static synchronized DatamuseClient getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DatamuseClient();
        }
        return INSTANCE;
    }

    public DatamuseAPIService getDatamuseAPIService() {
        return datamuseAPIService;
    }

}
