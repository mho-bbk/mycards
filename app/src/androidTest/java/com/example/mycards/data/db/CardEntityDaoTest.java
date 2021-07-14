package com.example.mycards.data.db;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.example.mycards.LiveDataTestUtil;
import com.example.mycards.data.entities.CardEntity;
import com.example.mycards.data.entities.UserAnswer;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class CardEntityDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private CardEntityDatabase database;
    private CardEntityDao cardEntityDao;

    @Before //executed before every test case
    public void setUp() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                CardEntityDatabase.class).allowMainThreadQueries().build();

        cardEntityDao = database.getCardEntityDao();
    }

    @After  //executed after every test case
    public void tearDown() {
        database.close();
    }

    @Test
    public void testUpsert() {
        CardEntity test = new CardEntity("chef", "チェフ");
        test.setId(1);   //When testAns is entered into dao, Room will autoset id to 1 as it's the first object

        cardEntityDao.upsert(test);

        List<CardEntity> allCards;
        try {
            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
            assertEquals(allCards.get(0), test);
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testMultipleUpsert() {
        CardEntity testCard1 = new CardEntity("chef", "チェフ");
        testCard1.setId(1);
        CardEntity testCard2 = new CardEntity("baker", "パン屋さん");
        testCard2.setId(2);
        CardEntity testCard3 = new CardEntity("musician", "音楽家");
        testCard3.setId(3);

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);
        cardEntityDao.upsert(testCard3);

        List<CardEntity> allCards;
        try {
            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
            assertEquals(allCards.get(0), testCard1);
            assertEquals(allCards.get(1), testCard2);
            assertEquals(allCards.get(2), testCard3);
            assertTrue(allCards.size() == 3);
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testUpdateOnInsertNoId() {
        CardEntity testCard1 = new CardEntity("chef", "チェフ");
        CardEntity testCard2 = new CardEntity("chef", "チェフ");

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);

        List<CardEntity> allCards;
        try {
            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
            assertEquals(allCards.size(), 1);
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testUpdateOnInsertSameId() {
        CardEntity testCard1 = new CardEntity("chef", "チェフ");
        testCard1.setId(1);
        CardEntity testCard2 = new CardEntity("chef", "チェフ");
        testCard2.setId(1);

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);

        List<CardEntity> allCards;
        try {
            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
            assertEquals(allCards.size(), 1);
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testUpdateOnInsertDiffId() {
        CardEntity testCard1 = new CardEntity("chef", "チェフ");
        testCard1.setId(1);
        CardEntity testCard2 = new CardEntity("chef", "チェフ");
        testCard2.setId(2);

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);

        List<CardEntity> allCards;
        try {
            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
            assertEquals(allCards.size(), 1);
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testDelete() {
        //Recreate db from previous test
        CardEntity testCard = new CardEntity("chef", "チェフ");
        testCard.setId(1);

        cardEntityDao.upsert(testCard);

        //Test delete() method
        cardEntityDao.delete(testCard);

        List<CardEntity> allCards;
        try {
            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
            assertTrue(allCards.isEmpty());
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testDeleteAllAnswers() {
        //Recreate db from previous test
        CardEntity testCard1 = new CardEntity("chef", "チェフ");
        testCard1.setId(1);
        CardEntity testCard2 = new CardEntity("baker", "パン屋さん");
        testCard2.setId(2);
        CardEntity testCard3 = new CardEntity("musician", "音楽家");
        testCard3.setId(3);

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);
        cardEntityDao.upsert(testCard3);

        //Test method deleteAllAnswers()
        cardEntityDao.deleteAllCards();

        List<CardEntity> allCards;
        try {
            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
            assertTrue(allCards.isEmpty());
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }
}
