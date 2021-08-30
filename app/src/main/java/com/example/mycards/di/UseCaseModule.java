package com.example.mycards.di;

import com.example.mycards.data.repositories.CardRepository;
import com.example.mycards.data.repositories.DeckRepository;
import com.example.mycards.data.repositories.JMDictRepository;
import com.example.mycards.server.datamuse.DatamuseAPIService;
import com.example.mycards.usecases.createcards.CreateAndGetCardUseCase;
import com.example.mycards.usecases.createdeck.CreateDeckUseCase;
import com.example.mycards.usecases.jptranslate.GetJpWordsUseCase;
import com.example.mycards.usecases.semanticsearch.GetSimilarWordsUseCase;

import java.util.Observable;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public class UseCaseModule {

    @Provides
    @Singleton
    public GetSimilarWordsUseCase provideSimilarWordsUseCase(DatamuseAPIService datamuseAPIService) {
        return new GetSimilarWordsUseCase(datamuseAPIService);
    }

    @Provides
    @Singleton
    public GetJpWordsUseCase provideJPWordsUseCase(JMDictRepository jmDictRepository) {
        return new GetJpWordsUseCase(jmDictRepository);
    }

    @Provides
    @Singleton
    public CreateAndGetCardUseCase provideCreateCardsUseCase(CardRepository cardRepository) {
        return new CreateAndGetCardUseCase(cardRepository);
    }

    @Provides
    @Singleton
    public CreateDeckUseCase createDeckUseCase(DeckRepository deckRepository) {
        return new CreateDeckUseCase(deckRepository);
    }
}
