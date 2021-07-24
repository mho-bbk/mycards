package com.example.mycards;

import com.example.mycards.data.repositories.FakeCardRepository;
import com.example.mycards.jmdict.JMDictEntryBuilder;
import com.example.mycards.main.SharedViewModel;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

//Not testing getters and setters
public class SharedViewModelTest {
    private InputStream input;
    private JMDictEntryBuilder entryBuilder;

    private SharedViewModel sharedViewModel;

    @Before
    private void setUp() throws IOException {
        sharedViewModel = new SharedViewModel(new FakeCardRepository());
        input = getClass()
                .getResourceAsStream("jmdict_eng_common_3_1_0_sample.json");
        entryBuilder = JMDictEntryBuilder.getInstance(input);
    }

//    private final List<Card> testDeck =
//        List.of(new Card("apple", "りんご (ringo)"),
//                new Card("orange", "オレンジ (orenji)"),
//                new Card("watermelon", "スイカ (suika)"));

    //Rules we could test for:
    //- Not accepting empty input/insertions to the db
    //- Not accepting String that are too long in length
    //- Not accepting words that are misspelled (don't appear in Datamuse API)...

}