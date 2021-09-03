package com.example.mycards.data.entities;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DeckTest {

    @Test
    public void getDeckNameTest() {
        //Indirectly tests private method createDeckName()
        List<String> inputWords = List.of("chef", "tennis", "art");
        Deck testDeck = new Deck(inputWords);

        assertEquals("chef, tennis, art", testDeck.getDeckName());
    }

    @Test
    public void rebuildInputListTest() {
        List<String> inputWords = List.of("chef", "tennis", "art");
        Deck testDeck = new Deck(inputWords);

        List<String> rebuiltInputList = Deck.rebuildInputList(testDeck.getDeckName());

        assertEquals(inputWords, rebuiltInputList);
    }

    @Test
    public void rebuildInputList_EmptyList() {
        Deck testDeck = new Deck("");   //Deck with empty string name

        List<String> rebuiltInputList = Deck.rebuildInputList(testDeck.getDeckName());

        assertEquals(new ArrayList<>(), rebuiltInputList);
        assertNotNull(rebuiltInputList);
    }

    @Test
    public void ifInputStringEmptyThenDeckIsNameless() {
        List<String> inputWords = new ArrayList<>();
        Deck testDeck = new Deck(inputWords);

        assertEquals("", testDeck.getDeckName());
    }

    @Test
    public void equalsTest() {
        List<String> inputWords = List.of("chef", "tennis", "art");
        Deck testDeck = new Deck(inputWords);
        testDeck.setDeckId(1);

        List<String> inputWordsExactDuplicate = List.of("chef", "tennis", "art");
        Deck testDeckDuplicate = new Deck(inputWordsExactDuplicate);
        testDeckDuplicate.setDeckId(1);

        List<String> inputWordsDuplicateDiffId = List.of("chef", "tennis", "art");
        Deck testDeckSameButDiffId = new Deck(inputWordsDuplicateDiffId);
        testDeckSameButDiffId.setDeckId(2); //tests id does not matter for equality

        List<String> inputWordsOverlapNotDuplicate = List.of("teacher", "tennis", "art");
        Deck testDeckOverlap = new Deck(inputWordsOverlapNotDuplicate);
        testDeckOverlap.setDeckId(3);

        List<String> inputWordsAllDifferent = List.of("interpreter", "skiing", "history");
        Deck testDeckDifferent = new Deck(inputWordsAllDifferent);
        testDeckDifferent.setDeckId(4);

        assertEquals(testDeck, testDeckDuplicate);
        assertEquals(testDeck, testDeckSameButDiffId);
        assertNotEquals(testDeck, testDeckOverlap);
        assertNotEquals(testDeck, testDeckDifferent);
    }
}