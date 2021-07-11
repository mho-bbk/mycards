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

}