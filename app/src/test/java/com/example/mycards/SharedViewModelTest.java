package com.example.mycards;

import com.example.mycards.main.SharedViewModel;
import com.example.mycards.main.SharedViewModelFactory;
import com.example.mycards.usecases.createcards.CreateAndGetCardUseCase;
import com.example.mycards.usecases.helper.CurrentThreadExecutor;
import com.example.mycards.usecases.jptranslate.GetJpWordsUseCase;
import com.example.mycards.usecases.semanticsearch.GetSimilarWordsUseCase;

import org.junit.Before;
import org.mockito.Mock;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

//Not testing getters and setters
public class SharedViewModelTest {

    @Mock
    private SharedViewModelFactory sharedViewModelFactory;     //not used
    private SharedViewModel testSharedViewModel;

    @Mock
    private GetSimilarWordsUseCase similarWordsUseCase;
    @Mock
    private GetJpWordsUseCase jpWordsUseCase;
    @Mock
    private CreateAndGetCardUseCase cardUseCase;

    private final ExecutorService executorService = new CurrentThreadExecutor();

    @Before
    public void setUp() throws IOException {
        //instantiate VM using usecase mocks
        testSharedViewModel = new SharedViewModel(similarWordsUseCase, jpWordsUseCase, cardUseCase, executorService);
    }

    //TODO - test LiveData values are setting accordingly
    // Ref: https://medium.com/androiddevelopers/unit-testing-livedata-and-other-common-observability-problems-bb477262eb04
    //  (Associated github) https://gist.github.com/JoseAlcerreca/1e9ee05dcdd6a6a6fa1cbfc125559bba
    //  Need to account for how JUnit has no idea about main thread/doesn't allow for async operations

    //Also this: https://medium.com/mindorks/unit-testing-for-viewmodel-19f4d76b20d4


//    private final List<Card> testDeck =
//        List.of(new Card("apple", "りんご (ringo)"),
//                new Card("orange", "オレンジ (orenji)"),
//                new Card("watermelon", "スイカ (suika)"));

    //Rules we could test for:
    //- Not accepting empty input/insertions to the db
    //- Not accepting String that are too long in length
    //- Not accepting words that are misspelled (don't appear in Datamuse API)...

}