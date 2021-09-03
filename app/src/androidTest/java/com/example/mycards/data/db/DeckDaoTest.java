package com.example.mycards.data.db;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.example.mycards.LiveDataTestUtil;
import com.example.mycards.data.entities.Deck;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class DeckDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private DeckEntityDatabase database;
    private DeckEntityDao deckEntityDao;

    @Before //executed before every test case
    public void setUp() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                DeckEntityDatabase.class).allowMainThreadQueries().build();

        deckEntityDao = database.getDeckEntityDao();
    }

    @After  //executed after every test case
    public void tearDown() {
        database.close();
    }

    @Test
    public void testUpsertValidStringParam_Passes() {
        Deck test = new Deck("new deck name");

        deckEntityDao.upsert(test);

        List<Deck> allDecks;
        try {
            allDecks = LiveDataTestUtil.getOrAwaitValue(deckEntityDao.getAllDecks());
            assertEquals(test, allDecks.get(0));
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testUpsertEmptyStringParam_Passes() {
        //NB: We don't want it to upsert but as the implementation of the Dao is delegated
        //to room, we will have to handle this behaviour elsewhere in our code

        Deck test = new Deck("");

        deckEntityDao.upsert(test);

        List<Deck> allDecks;
        try {
            allDecks = LiveDataTestUtil.getOrAwaitValue(deckEntityDao.getAllDecks());
            assertEquals(test, allDecks.get(0));
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testUpsertListParam() {
        //Testing the constructor specifically with Room's Dao implementation and the @Ignore annotation
        List<String> listParam = new ArrayList<>(List.of("chef", "art", "music"));
        Deck test = new Deck(listParam);

        deckEntityDao.upsert(test);

        List<Deck> allDecks;
        try {
            allDecks = LiveDataTestUtil.getOrAwaitValue(deckEntityDao.getAllDecks());
            assertEquals(test, allDecks.get(0));

        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testUpsertListParam_NoDuplicates() {
        List<String> listParam = new ArrayList<>(List.of("chef", "art", "music"));
        List<String> listParamDuplicate = new ArrayList<>(List.of("chef", "art", "music"));
        Deck test1 = new Deck(listParam);
        Deck test2 = new Deck(listParamDuplicate);

        deckEntityDao.upsert(test1);
        deckEntityDao.upsert(test2);

        List<Deck> allDecks;
        try {
            allDecks = LiveDataTestUtil.getOrAwaitValue(deckEntityDao.getAllDecks());
            assertEquals(1, allDecks.size());
            assertEquals(test1, allDecks.get(0));
            assertEquals(test2, allDecks.get(0));

        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testMultipleUpsert_OverlappingDecks_Pass() {
        //Testing upserts work with overlaps still
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

        deckEntityDao.upsert(testDeck);
        deckEntityDao.upsert(testDeckDuplicate);
        deckEntityDao.upsert(testDeckSameButDiffId);
        deckEntityDao.upsert(testDeckOverlap);
        deckEntityDao.upsert(testDeckDifferent);

        List<Deck> allDecks;
        try {
            allDecks = LiveDataTestUtil.getOrAwaitValue(deckEntityDao.getAllDecks());
            assertEquals(3, allDecks.size());
            assertEquals(testDeck, allDecks.get(0));
            assertEquals(testDeckOverlap, allDecks.get(1));
            assertEquals(testDeckDifferent, allDecks.get(2));
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testDelete() {
        //Recreate db from previous test
        Deck test = new Deck("new deck name");
        test.setDeckId(1);  //NOTE: Must set Id (autogenerated IRL) in order for delete to work

        deckEntityDao.upsert(test);

        List<Deck> allDecks;
        try {
            //Check the db is not empty
            allDecks = LiveDataTestUtil.getOrAwaitValue(deckEntityDao.getAllDecks());
            assertEquals(1, allDecks.size());

            //Test delete() method
            deckEntityDao.delete(test);
            //Re-get the decks
            allDecks = LiveDataTestUtil.getOrAwaitValue(deckEntityDao.getAllDecks());
            assertTrue(allDecks.isEmpty());

        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testDeleteAllDecks() {
        //Recreate db from previous test
        List<String> inputWords = List.of("chef", "tennis", "art");
        Deck testDeck = new Deck(inputWords);
        testDeck.setDeckId(1);

        List<String> inputWordsOverlapNotDuplicate = List.of("teacher", "tennis", "art");
        Deck testDeckOverlap = new Deck(inputWordsOverlapNotDuplicate);
        testDeckOverlap.setDeckId(2);

        List<String> inputWordsAllDifferent = List.of("interpreter", "skiing", "history");
        Deck testDeckDifferent = new Deck(inputWordsAllDifferent);
        testDeckDifferent.setDeckId(3);

        deckEntityDao.upsert(testDeck);
        deckEntityDao.upsert(testDeckOverlap);
        deckEntityDao.upsert(testDeckDifferent);

        List<Deck> allDecks;
        try {
            //Repeat test to check it's filled
            allDecks = LiveDataTestUtil.getOrAwaitValue(deckEntityDao.getAllDecks());
            assertEquals(3, allDecks.size());

            //Test deleteAll() method
            deckEntityDao.deleteAllDecks();
            //re-get the data
            allDecks = LiveDataTestUtil.getOrAwaitValue(deckEntityDao.getAllDecks());
            assertTrue(allDecks.isEmpty());
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void onNoResultGetEmptyListNotNull_allDecks() {
        try {
            List<Deck> allDecksEmpty = LiveDataTestUtil.getOrAwaitValue(deckEntityDao.getAllDecks());
            assertTrue(allDecksEmpty.isEmpty());
            assertNotNull(allDecksEmpty);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
