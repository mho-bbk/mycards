package com.example.mycards.test;

import com.example.mycards.datamuse.DatamuseAPIService;
import com.example.mycards.datamuse.pojo.DatamuseWord;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.mock.Calls;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

//TODO - look further into testing here:
// https://stackoverflow.com/questions/45160055/unit-testing-in-retrofit-for-callback

public class DatamuseClientTest {
    private List<DatamuseWord> fakeResultForTeacher = List.of(new DatamuseWord("instructor", 91970),
            new DatamuseWord("schoolteacher", 72127),
            new DatamuseWord("fake", 12345));


    @Test
    public void testDatamuseAPIServiceMaxSearchMethod() throws IOException {

        DatamuseAPIService mockedAPIService = Mockito.mock(DatamuseAPIService.class);
        when(mockedAPIService.getMaxSingleSearchResults(eq("teacher"), eq(3)))
                .thenReturn(Calls.response(fakeResultForTeacher));

        Call<List<DatamuseWord>> mockedCall = mockedAPIService.getMaxSingleSearchResults("teacher", 3);
        List<DatamuseWord> dbWords = mockedCall.execute().body();   //use execute to perform synchronously

        assertEquals(3, dbWords.size());

        //Next steps according to link in TO/DO...
        // inject mocked ApiInterface to your presenter
        // and then mock view and verify calls (and eventually use ArgumentCaptor to access call parameters)



    }
}