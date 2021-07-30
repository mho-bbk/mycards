package com.example.mycards.test;

import com.example.mycards.datamuse.DatamuseWordBuilder;
import com.example.mycards.datamuse.pojo.DatamuseWord;
import com.example.mycards.jmdict.JMDictEntryBuilder;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DatamuseWordBuilderTest {

    private String fakeJsonString = "[{\"word\":\"cuisine\",\"score\":71291,\"tags\":[\"n\"]},\n" +
            "{\"word\":\"cook\",\"score\":69758,\"tags\":[\"n\",\"v\"]},\n" +
            "{\"word\":\"kitchen\",\"score\":67750,\"tags\":[\"n\"]},\n" +
            "{\"word\":\"baker\",\"score\":69008,\"tags\":[\"n\",\"prop\"]},\n" +
            "{\"word\":\"cooking\",\"score\":67352,\"tags\":[\"n\",\"v\"]}]";
    private DatamuseWordBuilder datamuseWordBuilder;

    @Before
    public void setUp() throws Exception {
        datamuseWordBuilder = DatamuseWordBuilder.getInstance(fakeJsonString);
    }

    //May not usually test getters but this is to test private sort method
    @Test
    public void getDatamuseWords() {
        List<DatamuseWord> words = datamuseWordBuilder.getDatamuseWords();

        assertEquals(words.get(0), new DatamuseWord("cuisine", 71291));
        assertEquals(words.get(1), new DatamuseWord("cook", 69758));
        assertEquals(words.get(2), new DatamuseWord("baker", 69008));
        assertEquals(words.get(3), new DatamuseWord("kitchen", 67750));
        assertEquals(words.get(4), new DatamuseWord("cooking",67352));

    }
}