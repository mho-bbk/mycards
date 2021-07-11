package com.example.mycards;

import com.example.mycards.data.repositories.FakeAnswerRepository;

import org.junit.Before;

import static org.junit.Assert.*;

public class SharedViewModelTest {

    private SharedViewModel sharedViewModel;

    @Before
    private void setUp() {
        sharedViewModel = new SharedViewModel(new FakeAnswerRepository());
    }

    //Rules we could test for:
    //- Not accepting empty input/insertions to the db
    //- Not accepting String that are too long in length
    //- Not accepting words that are misspelled (don't appear in Datamuse API)

}