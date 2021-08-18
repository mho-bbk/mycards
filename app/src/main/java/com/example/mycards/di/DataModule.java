package com.example.mycards.di;

import com.example.mycards.data.repositories.CardRepository;
import com.example.mycards.data.repositories.DefaultCardRepository;
import com.example.mycards.data.repositories.DefaultJMDictRepository;
import com.example.mycards.data.repositories.JMDictRepository;

import java.util.concurrent.ExecutorService;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

//Returns dependencies based on data. Specifically:
// + CardRepository/CardRepositoryImpl [DefaultCardRepository]
// + JMDictRepository/JMDictRepositoryImpl [DefaultJMDictRepository]
@Module(includes = {NetworkModule.class})
abstract class DataModule {

    @Binds
    @Singleton
    public abstract CardRepository provideCardRepository(DefaultCardRepository defaultCardRepository);

    @Binds
    @Singleton
    public abstract JMDictRepository provideJMDictRepository(DefaultJMDictRepository defaultJMDictRepository);
}
