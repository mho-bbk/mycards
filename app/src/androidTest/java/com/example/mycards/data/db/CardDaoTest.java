package com.example.mycards.data.db;

import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.example.mycards.LiveDataTestUtil;
import com.example.mycards.data.entities.Card;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class CardDaoTest {

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
        Card test = new Card("chef", "チェフ");
        test.setId(1);   //When testAns is entered into dao, Room will autoset id to 1 as it's the first object

        cardEntityDao.upsert(test);

        List<Card> allCards;
        try {
            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
            assertEquals(allCards.get(0), test);
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testMultipleUpsert() {
        Card testCard1 = new Card("chef", "チェフ");
        testCard1.setId(1);
        Card testCard2 = new Card("baker", "パン屋さん");
        testCard2.setId(2);
        Card testCard3 = new Card("musician", "音楽家");
        testCard3.setId(3);

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);
        cardEntityDao.upsert(testCard3);

        List<Card> allCards;
        try {
            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
            assertEquals(allCards.get(0), testCard1);
            assertEquals(allCards.get(1), testCard2);
            assertEquals(allCards.get(2), testCard3);
            assertEquals(3, allCards.size());
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testMultipleUpsertSameADiffB() {
        //TODO - This is 'correct' behaviour atm but question this.
        // Do we want to display diff Jwords for the same Eword as diff cards?
        // Should we use production code to decide how to display multi Jwords for same Eword?
        Card testCard1 = new Card("chef", "チェフ");
        testCard1.setId(1);
        Card testCard2 = new Card("chef", "コック");
        testCard2.setId(2);

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);

        List<Card> allCards;
        try {
            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
            assertEquals(allCards.get(0), testCard1);
            assertEquals(allCards.get(1), testCard2);
            assertEquals(2, allCards.size());
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testMultipleUpsertDiffASameBSuccessNoDeckSeed() {
        //TODO - This is 'correct' behaviour atm but question this.
        // Do we want to diff Ewords to have same Jword on Bside? (Instinct: no, not useful)
        Card testCard1 = new Card("cook", "チェフ");
        testCard1.setId(1);
        Card testCard2 = new Card("chef", "チェフ");
        testCard2.setId(2);

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);

        List<Card> allCards;
        try {
            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
            assertEquals(allCards.get(0), testCard1);
            assertEquals(allCards.get(1), testCard2);
            assertEquals(2, allCards.size());
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testUpdateOnInsertNoId() {
        Card testCard1 = new Card("chef", "チェフ");
        Card testCard2 = new Card("chef", "チェフ");

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);

        List<Card> allCards;
        try {
            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
            assertEquals(allCards.size(), 1);
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testUpdateOnInsertSameId() {
        Card testCard1 = new Card("chef", "チェフ");
        testCard1.setId(1);
        Card testCard2 = new Card("chef", "チェフ");
        testCard2.setId(1);

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);

        List<Card> allCards;
        try {
            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
            assertEquals(allCards.size(), 1);
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testUpdateOnInsertDiffId() {
        Card testCard1 = new Card("chef", "チェフ");
        testCard1.setId(1);
        Card testCard2 = new Card("chef", "チェフ");
        testCard2.setId(2);

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);

        List<Card> allCards;
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
        Card testCard = new Card("chef", "チェフ");
        testCard.setId(1);

        cardEntityDao.upsert(testCard);

        //Test delete() method
        cardEntityDao.delete(testCard);

        List<Card> allCards;
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
        Card testCard1 = new Card("chef", "チェフ");
        testCard1.setId(1);
        Card testCard2 = new Card("baker", "パン屋さん");
        testCard2.setId(2);
        Card testCard3 = new Card("musician", "音楽家");
        testCard3.setId(3);

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);
        cardEntityDao.upsert(testCard3);

        //Test method deleteAllAnswers()
        cardEntityDao.deleteAllCards();

        List<Card> allCards;
        try {
            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
            assertTrue(allCards.isEmpty());
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void getDeckOfCardsSameDeck() {
        //Recreate db from previous test - with deckSeeds
        Card testCard1 = new Card("chef", "チェフ", "{chef, baker, musician}");
        testCard1.setId(1);
        Card testCard2 = new Card("baker", "パン屋さん", "{chef, baker, musician}");
        testCard2.setId(2);
        Card testCard3 = new Card("musician", "音楽家", "{chef, baker, musician}");
        testCard3.setId(3);

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);
        cardEntityDao.upsert(testCard3);

        String deckName = "{chef, baker, musician}";

        List<Card> allCardsSameDeck;
        try {
            allCardsSameDeck = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getCards(deckName));
            assertEquals(3, allCardsSameDeck.size());
            assertTrue(allCardsSameDeck.contains(testCard1));
            assertTrue(allCardsSameDeck.contains(testCard2));
            assertTrue(allCardsSameDeck.contains(testCard3));
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void getDeckOfCardsDiffDeck() {
        //Recreate db from previous test - with deckSeeds
        Card testCard1 = new Card("chef", "チェフ", "{chef, baker}");
        testCard1.setId(1);
        Card testCard2 = new Card("baker", "パン屋さん", "{chef, baker}");
        testCard2.setId(2);
        Card testCard3 = new Card("musician", "音楽家", "{musician moo}");
        testCard3.setId(3);

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);
        cardEntityDao.upsert(testCard3);

        String deckName1 = "{chef, baker}";
        String deckName2 = "{musician moo}";

        List<Card> deck1;
        List<Card> deck2;
        try {
            deck1 = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getCards(deckName1));
            assertEquals(2, deck1.size());
            assertTrue(deck1.contains(testCard1));
            assertTrue(deck1.contains(testCard2));
            assertFalse(deck1.contains(testCard3));

            deck2 = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getCards(deckName2));
            assertEquals(1, deck2.size());
            assertTrue(deck2.contains(testCard3));
            assertFalse(deck2.contains(testCard2));
            assertFalse(deck2.contains(testCard1));
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    //TODO handle empty card db (getAllCards or getCards returns null)
    // Currently: if returns nothing, deck goes straight to 'Finished deck' - is this the desired behaviour?
}
