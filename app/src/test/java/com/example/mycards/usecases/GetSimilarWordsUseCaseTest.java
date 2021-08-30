package com.example.mycards.usecases;

import com.example.mycards.server.datamuse.DatamuseAPIService;
import com.example.mycards.server.datamuse.pojo.DatamuseWord;
import com.example.mycards.usecases.semanticsearch.GetSimilarWordsUseCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.mock.Calls;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Tests for Retrofit and Network calls
 * Helpful stack overflow answers:
 * + https://stackoverflow.com/questions/53707876/how-to-unit-test-retrofit-call
 * + (For testIOException) https://stackoverflow.com/questions/45160055/unit-testing-in-retrofit-for-callback
 * + (For testIOException) https://stackoverflow.com/questions/3762047/throw-checked-exceptions-from-mocks-with-mockito
 */

public class GetSimilarWordsUseCaseTest {

    private DatamuseAPIService mockedAPIService;
    private GetSimilarWordsUseCase similarWordsUseCase;
    private final int searchLimit = 4;    //current default. Future feature to make this adjustable. If impl in future, need to test api if num is higher than poss (TODO)

    //create Lists of DatamuseWords to return from the service call. These are real results (accessed 22/8/21)
    List<DatamuseWord> fakeResultForTeacher = List.of(new DatamuseWord("instructor", 91970, List.of("syn", "n")),
            new DatamuseWord("schoolteacher", 72127, List.of("n")),
            new DatamuseWord("school", 71586, List.of("n")),
            new DatamuseWord("educator", 70839, List.of("n")));

    List<DatamuseWord> fakeResultForTennis = List.of(new DatamuseWord("lawn tennis", 86876, List.of("syn", "n")),
            new DatamuseWord("volleyball", 73356, List.of("n", "adv")),
            new DatamuseWord("tournament", 67657, List.of("n", "adj")),
            new DatamuseWord("championships", 63025, List.of("n")));

    List<DatamuseWord> fakeResultForHistory = List.of(new DatamuseWord("chronicle", 80836, List.of("syn", "n")),
            new DatamuseWord("story", 77519, List.of("syn", "n")),
            new DatamuseWord("account", 73512, List.of("syn", "n")),
            new DatamuseWord("annals", 70502, List.of("n")));

    @Before
    public void setUp() {
        //Mock the service call
        mockedAPIService = Mockito.mock(DatamuseAPIService.class);
        //Create global use case instance using the mock
        similarWordsUseCase = new GetSimilarWordsUseCase(mockedAPIService);
    }

    @After
    public void tearDown() throws Exception {
    }

    //TODO - use MockWebServer for further testing and mocking
    // eg what happens when the API service itself throws an exception (IOException)
    // (in my code this is the catch in the try/catch in run()

    @Test
    public void testRunPasses() {
        //Set up fake call and results
        when(mockedAPIService.getMaxSingleSearchResults(eq("teacher"), eq(searchLimit)))
                .thenReturn(Calls.response(fakeResultForTeacher));
        when(mockedAPIService.getMaxSingleSearchResults(eq("tennis"), eq(searchLimit)))
                .thenReturn(Calls.response(fakeResultForTennis));
        when(mockedAPIService.getMaxSingleSearchResults(eq("history"), eq(searchLimit)))
                .thenReturn(Calls.response(fakeResultForHistory));

        //Indirectly test private methods
        List<String> testInput = new ArrayList<>(List.of("teacher", "tennis", "history"));

        HashMap<String, List<String>> expectedOutput = new HashMap<>();
        expectedOutput.put("teacher", List.of("instructor", "schoolteacher", "school", "educator"));
        expectedOutput.put("tennis", List.of("lawn tennis", "volleyball", "tournament", "championships"));
        expectedOutput.put("history", List.of("chronicle", "story", "account", "annals"));

        HashMap<String, List<String>> similarWordsMap = similarWordsUseCase.run(testInput);

        assertEquals(expectedOutput, similarWordsMap);
    }

    @Test
    public void testRunEmptyReturn() {
        //Empty list return
        when(mockedAPIService
                .getMaxSingleSearchResults(eq("a string that returns no results"), eq(searchLimit)))
                .thenReturn(Calls.response(new ArrayList<>()));

        //Tests if(datamuseWords != null) but is empty
        List<String> testInput = new ArrayList<>(List.of("a string that returns no results"));

        HashMap<String, List<String>> expectedOutput = new HashMap<>(); //empty HashMap

        HashMap<String, List<String>> mapOnEmptyServiceResponse = similarWordsUseCase.run(testInput);

        assertEquals(expectedOutput, mapOnEmptyServiceResponse);
    }

    @Test
    public void testRunNullReturn() {
        //Null return (eg when HTTP call returns error page?
        // Unsure Retrofit ever returns null as may only ever be empty, but null can be returned eg if JSON parser is not correct...)
        when(mockedAPIService
                .getMaxSingleSearchResults(eq("a string that returns null"), eq(searchLimit)))
                .thenReturn(Calls.response((List<DatamuseWord>) null)); //cast to ensure Calls uses the right overloaded method

        //Tests if(datamuseWords == null)
        List<String> testInput = new ArrayList<>(List.of("a string that returns null"));

        HashMap<String, List<String>> expectedOutput = new HashMap<>(); //empty HashMap

        HashMap<String, List<String>> mapOnNullServiceResponse = similarWordsUseCase.run(testInput);

        assertEquals(expectedOutput, mapOnNullServiceResponse);
    }

    @Test(expected = RuntimeException.class)
    public void testRunException() {
        when(mockedAPIService.getMaxSingleSearchResults(anyString(), anyInt()))
                .thenThrow(new RuntimeException("RuntimeException occurred"));

        HashMap<String, List<String>> expectedOutput = new HashMap<>(); //empty HashMap
        HashMap<String, List<String>> mapOnException = similarWordsUseCase.run(new ArrayList<>(List.of("any string")));

        //Test that exception is handled within callService private method
        assertEquals(expectedOutput, mapOnException);
    }

    @Test
    public void testIOException() throws IOException {
        Call<List<DatamuseWord>> mockCall = Mockito.mock(Call.class);

        Mockito.when(mockedAPIService.getMaxSingleSearchResults("any string", searchLimit))
                .thenReturn(mockCall);

        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                //mocking IOException in Retrofit Call
                throw new IOException("Mock IOException");
            }
        }).when(mockCall).execute();

        HashMap<String, List<String>> expectedOutput = new HashMap<>(); //empty HashMap
        HashMap<String, List<String>> mapOnException = similarWordsUseCase.run(new ArrayList<>(List.of("any string")));

        //Test that exception is handled within callService private method
        assertEquals(expectedOutput, mapOnException);
    }

}