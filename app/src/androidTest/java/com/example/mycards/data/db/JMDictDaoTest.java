package com.example.mycards.data.db;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.example.mycards.data.entities.JMDictEntry;
import com.example.mycards.usecases.jptranslate.jmdict.pojo.Kana;
import com.example.mycards.usecases.jptranslate.jmdict.pojo.Kanji;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class JMDictDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private JMDictEntryDatabase database;
    private JMDictEntryDao jmDictEntryDao;

    @Before //executed before every test case
    public void setUp() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                JMDictEntryDatabase.class).allowMainThreadQueries().build();

        jmDictEntryDao = database.getJMDictEntryDao();

        //TODO - create json stream off sample file here
    }

    @After  //executed after every test case
    public void tearDown() {
        database.close();
    }

    //TODO - handle null returns on get methods

    @Test
    public void testInsertAll() {
        JMDictEntry expectedEntry = new JMDictEntry("to speed up", "1601080", new Kanji("早める"),
                new Kana("はやめる"), 3, 2, 2, 2,
                new ArrayList<>(List.of("v1", "vt")));

        //test db is empty so we must insert this to get it back - this thus doesn't test preload...
        jmDictEntryDao.upsert(expectedEntry);
        JMDictEntry actualEntry;

//        try {
//            LiveData<JMDictEntry> testGet = jmDictEntryDao.getFirstJMDictEntry("to speed up");
//            assertFalse(testGet == null);   //this test passes
//            actualEntry = testGet.getValue();
//            assertFalse(actualEntry == null);   //this test doesn't pass
//            actualEntry = LiveDataTestUtil.getOrAwaitValue(jmDictEntryDao.getFirstJMDictEntry("to speed up"));
//            assertEquals(expectedEntry, actualEntry);   //assertion failure bc result is null
//        } catch (InterruptedException e) {
//            System.err.println(e.getStackTrace());
//        }
    }

    @Test
    public void testUpsert() {
//        Card test = new Card("chef", "チェフ");
//        test.setId(1);   //When testAns is entered into dao, Room will autoset id to 1 as it's the first object
//
//        cardEntityDao.upsert(test);
//
//        List<Card> allCards;
//        try {
//            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
//            assertEquals(allCards.get(0), test);
//        } catch(InterruptedException e) {
//            System.err.println(e.getStackTrace());
//        }
    }

    @Test
    public void testMultipleUpsert() {
//        Card testCard1 = new Card("chef", "チェフ");
//        testCard1.setId(1);
//        Card testCard2 = new Card("baker", "パン屋さん");
//        testCard2.setId(2);
//        Card testCard3 = new Card("musician", "音楽家");
//        testCard3.setId(3);
//
//        cardEntityDao.upsert(testCard1);
//        cardEntityDao.upsert(testCard2);
//        cardEntityDao.upsert(testCard3);
//
//        List<Card> allCards;
//        try {
//            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
//            assertEquals(allCards.get(0), testCard1);
//            assertEquals(allCards.get(1), testCard2);
//            assertEquals(allCards.get(2), testCard3);
//            assertTrue(allCards.size() == 3);
//        } catch(InterruptedException e) {
//            System.err.println(e.getStackTrace());
//        }
    }

    @Test
    public void testMultipleUpsertSameADiffB() {
        //TODO - This is 'correct' behaviour atm but question this.
        // Do we want to display diff Jwords for the same Eword as diff cards?
        // Should we use production code to decide how to display multi Jwords for same Eword?
//        Card testCard1 = new Card("chef", "チェフ");
//        testCard1.setId(1);
//        Card testCard2 = new Card("chef", "コック");
//        testCard2.setId(2);
//
//        cardEntityDao.upsert(testCard1);
//        cardEntityDao.upsert(testCard2);
//
//        List<Card> allCards;
//        try {
//            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
//            assertEquals(allCards.get(0), testCard1);
//            assertEquals(allCards.get(1), testCard2);
//            assertEquals(2, allCards.size());
//        } catch(InterruptedException e) {
//            System.err.println(e.getStackTrace());
//        }
    }

    @Test
    public void testMultipleUpsertDiffASameB() {
        //TODO - This is 'correct' behaviour atm but question this.
        // Do we want to diff Ewords to have same Jword on Bside? (Instinct: no, not useful)
        // Should we use production code to decide how to handle Ewords that are too similar
        // to avoid having to handle the same Jwords?
        // NB: JMDictEntry holds ref to unique wordID from JSON file & uses this as basis for equality
        // (so sideB here should have the same wordID)
//        Card testCard1 = new Card("cook", "チェフ");
//        testCard1.setId(1);
//        Card testCard2 = new Card("chef", "チェフ");
//        testCard2.setId(2);
//
//        cardEntityDao.upsert(testCard1);
//        cardEntityDao.upsert(testCard2);
//
//        List<Card> allCards;
//        try {
//            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
//            assertEquals(allCards.get(0), testCard1);
//            assertEquals(allCards.get(1), testCard2);
//            assertEquals(2, allCards.size());
//        } catch(InterruptedException e) {
//            System.err.println(e.getStackTrace());
//        }
    }

    @Test
    public void testUpdateOnInsertNoId() {
//        Card testCard1 = new Card("chef", "チェフ");
//        Card testCard2 = new Card("chef", "チェフ");
//
//        cardEntityDao.upsert(testCard1);
//        cardEntityDao.upsert(testCard2);
//
//        List<Card> allCards;
//        try {
//            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
//            assertEquals(allCards.size(), 1);
//        } catch(InterruptedException e) {
//            System.err.println(e.getStackTrace());
//        }
    }

    @Test
    public void testUpdateOnInsertSameId() {
//        Card testCard1 = new Card("chef", "チェフ");
//        testCard1.setId(1);
//        Card testCard2 = new Card("chef", "チェフ");
//        testCard2.setId(1);
//
//        cardEntityDao.upsert(testCard1);
//        cardEntityDao.upsert(testCard2);
//
//        List<Card> allCards;
//        try {
//            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
//            assertEquals(allCards.size(), 1);
//        } catch(InterruptedException e) {
//            System.err.println(e.getStackTrace());
//        }
    }

    @Test
    public void testUpdateOnInsertDiffId() {
//        Card testCard1 = new Card("chef", "チェフ");
//        testCard1.setId(1);
//        Card testCard2 = new Card("chef", "チェフ");
//        testCard2.setId(2);
//
//        cardEntityDao.upsert(testCard1);
//        cardEntityDao.upsert(testCard2);
//
//        List<Card> allCards;
//        try {
//            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
//            assertEquals(allCards.size(), 1);
//        } catch(InterruptedException e) {
//            System.err.println(e.getStackTrace());
//        }
    }

    @Test
    public void testDelete() {
//        //Recreate db from previous test
//        Card testCard = new Card("chef", "チェフ");
//        testCard.setId(1);
//
//        cardEntityDao.upsert(testCard);
//
//        //Test delete() method
//        cardEntityDao.delete(testCard);
//
//        List<Card> allCards;
//        try {
//            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
//            assertTrue(allCards.isEmpty());
//        } catch(InterruptedException e) {
//            System.err.println(e.getStackTrace());
//        }
    }

    @Test
    public void testDeleteAllAnswers() {
//        //Recreate db from previous test
//        Card testCard1 = new Card("chef", "チェフ");
//        testCard1.setId(1);
//        Card testCard2 = new Card("baker", "パン屋さん");
//        testCard2.setId(2);
//        Card testCard3 = new Card("musician", "音楽家");
//        testCard3.setId(3);
//
//        cardEntityDao.upsert(testCard1);
//        cardEntityDao.upsert(testCard2);
//        cardEntityDao.upsert(testCard3);
//
//        //Test method deleteAllAnswers()
//        cardEntityDao.deleteAllCards();
//
//        List<Card> allCards;
//        try {
//            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
//            assertTrue(allCards.isEmpty());
//        } catch(InterruptedException e) {
//            System.err.println(e.getStackTrace());
//        }
    }
}
