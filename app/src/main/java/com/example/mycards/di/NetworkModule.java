package com.example.mycards.di;

import com.example.mycards.server.datamuse.DatamuseAPIService;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

//Scope: Return dependencies related to Network calls. Specifically:
// + Retrofit
// + DatamuseAPIService
//Loosely based on: https://betterprogramming.pub/dependency-injection-in-android-with-dagger2-d260b8a72bb0
@Module
public class NetworkModule {

    @Provides
    @Singleton
    public Retrofit provideRetrofitForDatamuse() {
        return new Retrofit.Builder()
                .baseUrl(DatamuseAPIService.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Inject
    public DatamuseAPIService provideDatamuseApiService(Retrofit retrofit) {
        return retrofit.create(DatamuseAPIService.class);
    }

}
