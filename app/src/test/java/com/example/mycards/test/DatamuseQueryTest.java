package com.example.mycards.test;

import com.example.mycards.datamuse.DatamuseQuery;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

//TODO - when we rewrite the DatamuseQuery class
public class DatamuseQueryTest {
    private String jsonString;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void findSimilar() {
        DatamuseQuery dq = new DatamuseQuery();
        String catWords = dq.findSimilar("cat");
        System.out.println(catWords);

    }
}