package com.example.mycards;

import com.example.mycards.data.repositories.FakeCardRepository;

import org.junit.Before;

//Not testing getters and setters
public class SharedViewModelTest {

    private SharedViewModel sharedViewModel;

    @Before
    private void setUp() {
        sharedViewModel = new SharedViewModel(new FakeCardRepository());
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