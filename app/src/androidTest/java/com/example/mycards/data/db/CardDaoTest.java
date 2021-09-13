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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
    public void testUpsertBlankCardsNoDuplicate() {
        //Bc equality on a card is defined by side a & side b being the same,
        // blank side a and side b should not be inserted twice, even when relatedWord is present/different
        Card testCard1NoRelatedWord = new Card("", "");
        Card testCard2NoRelatedWord = new Card("", "");
        Card testCard1RelatedWord = new Card("", "", "teacher");
        Card testCard2RelatedWord = new Card("", "", "art");

        cardEntityDao.upsert(testCard1NoRelatedWord);
        cardEntityDao.upsert(testCard2NoRelatedWord);
        cardEntityDao.upsert(testCard1RelatedWord);
        cardEntityDao.upsert(testCard2RelatedWord);

        List<Card> allCards;
        try {
            allCards = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
            assertEquals(1, allCards.size());
            assertTrue(allCards.contains(testCard1NoRelatedWord));
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
    public void testDeleteAllCards() {
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
        String inputString = "chef";

        Card testCard1 = new Card("chef", "チェフ", "chef");
        testCard1.setId(1);
        Card testCard2 = new Card("baker", "パン屋さん", "chef");
        testCard2.setId(2);
        Card testCard3 = new Card("musician", "音楽家", "chef");
        testCard3.setId(3);

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);
        cardEntityDao.upsert(testCard3);

        List<Card> allCardsSameDeck;
        try {
            allCardsSameDeck = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getCards(inputString));
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
        String inputString1 = "chef";
        String inputString2 = "musician moo";

        //Recreate db from previous test - with relatedWord
        Card testCard1 = new Card("chef", "チェフ", "chef");
        testCard1.setId(1);
        Card testCard2 = new Card("baker", "パン屋さん", "chef");
        testCard2.setId(2);
        Card testCard3 = new Card("musician", "音楽家", "musician moo");
        testCard3.setId(3);

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);
        cardEntityDao.upsert(testCard3);


        List<Card> deck1;
        List<Card> deck2;
        try {
            deck1 = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getCards(inputString1));
            assertEquals(2, deck1.size());
            assertTrue(deck1.contains(testCard1));
            assertTrue(deck1.contains(testCard2));
            assertFalse(deck1.contains(testCard3));

            deck2 = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getCards(inputString2));
            assertEquals(1, deck2.size());
            assertTrue(deck2.contains(testCard3));
            assertFalse(deck2.contains(testCard2));
            assertFalse(deck2.contains(testCard1));
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void getAllCardsFromListString() {
        //Recreate db from previous test - with deckSeeds
        List<String> inputStrings = List.of("chef seed", "baker seed", "musician seed");

        Card testCard1 = new Card("chef", "チェフ", "chef seed");
        testCard1.setId(1);
        Card testCard2 = new Card("baker", "パン屋さん", "baker seed");
        testCard2.setId(2);
        Card testCard3 = new Card("musician", "音楽家", "musician seed");
        testCard3.setId(3);

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);
        cardEntityDao.upsert(testCard3);

        List<Card> chefBakerMusicianDeck;
        try {
            chefBakerMusicianDeck = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getCards(inputStrings));
            assertEquals(3, chefBakerMusicianDeck.size());
            assertTrue(chefBakerMusicianDeck.contains(testCard1));
            assertTrue(chefBakerMusicianDeck.contains(testCard2));
            assertTrue(chefBakerMusicianDeck.contains(testCard3));
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void onNoResultGetEmptyListNotNull_withDeckSeed() {
        String keyboardSmash = "agddjahd";

        try {
            List<Card> keyboardSmashDeck = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getCards(keyboardSmash));
            assertTrue(keyboardSmashDeck.isEmpty());
            assertNotNull(keyboardSmashDeck);

            List<Card> allCardsEmpty = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
            assertTrue(allCardsEmpty.isEmpty());
            assertNotNull(allCardsEmpty);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void onNoResultGetEmptyListNotNull_allCards() {
        try {
            List<Card> allCardsEmpty = LiveDataTestUtil.getOrAwaitValue(cardEntityDao.getAllCards());
            assertTrue(allCardsEmpty.isEmpty());
            assertNotNull(allCardsEmpty);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testContainsCardForString() {
        //Recreate db from previous test - with deckSeeds
        Card testCard1 = new Card("chef", "チェフ", "chef");
        testCard1.setId(1);
        Card testCard2 = new Card("baker", "パン屋さん", "baker");
        testCard2.setId(2);
        Card testCard3 = new Card("musician", "音楽家", "musician");
        testCard3.setId(3);

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);
        cardEntityDao.upsert(testCard3);

        assertTrue(cardEntityDao.containsCardsFor("chef"));
        assertTrue(cardEntityDao.containsCardsFor("baker"));
        assertTrue(cardEntityDao.containsCardsFor("musician"));
        assertFalse(cardEntityDao.containsCardsFor("astronaut"));

        //tests for case (in)sensitivity
        assertTrue(cardEntityDao.containsCardsFor("Chef")); //TODO - this fails, can switch = to LIKE but we want to be sure LIKE doesn't have any side effects (eg retrieving more than ideal)
    }

    @Test
    public void testContainsCardForListString() {
        //Recreate db from previous test - with deckSeeds
        Card testCard1 = new Card("chef", "チェフ", "chef");
        testCard1.setId(1);
        Card testCard2 = new Card("baker", "パン屋さん", "baker");
        testCard2.setId(2);
        Card testCard3 = new Card("musician", "音楽家", "musician");
        testCard3.setId(3);

        cardEntityDao.upsert(testCard1);
        cardEntityDao.upsert(testCard2);
        cardEntityDao.upsert(testCard3);

        List<String> dummyInputPasses = new ArrayList<>(List.of("chef", "baker", "musician"));
        List<String> dummyInputSomePasses = new ArrayList<>(List.of("astronaut", "baker", "adhdsha"));
        List<String> dummyInputFails = new ArrayList<>(List.of("astronaut", "parent", "afdgdsj"));

        assertTrue(cardEntityDao.containsCardsFor(dummyInputPasses));
        assertTrue(cardEntityDao.containsCardsFor(dummyInputSomePasses));
        assertFalse(cardEntityDao.containsCardsFor(dummyInputFails));

        //tests for case (in)sensitivity
        List<String> dummyInputPassesCapitalised = new ArrayList<>(List.of("Chef", "Baker", "Musician"));
        assertTrue(cardEntityDao.containsCardsFor(dummyInputPassesCapitalised));    //TODO - test fails atm but we expect this
    }
}
