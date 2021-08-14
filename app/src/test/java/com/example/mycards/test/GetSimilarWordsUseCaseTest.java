package com.example.mycards.test;

import com.example.mycards.datamuse.DatamuseAPIService;
import com.example.mycards.datamuse.pojo.DatamuseWord;
import com.example.mycards.usecases.GetSimilarWordsUseCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.mock.Calls;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

//TODO - look further into testing here:
// https://stackoverflow.com/questions/45160055/unit-testing-in-retrofit-for-callback
public class GetSimilarWordsUseCaseTest {

    private DatamuseAPIService mockedAPIService;

    private List<DatamuseWord> fakeResultForTeacher = List.of(new DatamuseWord("instructor", 91970),
            new DatamuseWord("schoolteacher", 72127),
            new DatamuseWord("fake", 12345));

    private List<DatamuseWord> resultDatamuseWords = new ArrayList<>();

    @Before
    public void setUp() {
        mockedAPIService = Mockito.mock(DatamuseAPIService.class);
        GetSimilarWordsUseCase similarWordsUseCase = new GetSimilarWordsUseCase(mockedAPIService);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void semanticSearchTest() {
        when(mockedAPIService.getMaxSingleSearchResults(eq("teacher"), eq(3)))
                .thenReturn(Calls.response(fakeResultForTeacher));

        Call<List<DatamuseWord>> mockedCall = mockedAPIService
                .getMaxSingleSearchResults("teacher", 3);

        List<DatamuseWord> dbWords = null;

        try {
            dbWords = mockedCall.execute().body();  //use execute to perform synchronously
            assertEquals(3, dbWords.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //TODO - test what happens if apiService returns nothing (both null and empty List)
    //Next steps according to link in TO/DO...
    // inject mocked ApiInterface to your presenter
    // and then mock view and verify calls (and eventually use ArgumentCaptor to access call parameters)

}