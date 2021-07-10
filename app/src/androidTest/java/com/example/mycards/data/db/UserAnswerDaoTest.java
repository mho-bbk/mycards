package com.example.mycards.data.db;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.example.mycards.LiveDataTestUtil;
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
public class UserAnswerDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AnswersDatabase database;
    private UserAnswerDao answerDao;

    @Before //executed before every test case
    public void setUp() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                AnswersDatabase.class).allowMainThreadQueries().build();

        answerDao = database.getUserAnswerDao();
    }

    @After  //executed after every test case
    public void tearDown() {
        database.close();
    }

    @Test
    public void testUpsert() {
        UserAnswer testAns = new UserAnswer("chef");
        testAns.setId(1);   //When testAns is entered into dao, Room will autoset id to 1 as it's the first object

        answerDao.upsert(testAns);

        List<UserAnswer> allAnswers;
        try {
            allAnswers = LiveDataTestUtil.getOrAwaitValue(answerDao.getAllAnswers());
            assertEquals(allAnswers.get(0), testAns);
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testMultipleUpsert() {
        UserAnswer testAns1 = new UserAnswer("chef");
        testAns1.setId(1);
        UserAnswer testAns2 = new UserAnswer("baker");
        testAns2.setId(2);
        UserAnswer testAns3 = new UserAnswer("musician");
        testAns3.setId(3);

        answerDao.upsert(testAns1);
        answerDao.upsert(testAns2);
        answerDao.upsert(testAns3);

        List<UserAnswer> allAnswers;
        try {
            allAnswers = LiveDataTestUtil.getOrAwaitValue(answerDao.getAllAnswers());
            assertEquals(allAnswers.get(0), testAns1);
            assertEquals(allAnswers.get(1), testAns2);
            assertEquals(allAnswers.get(2), testAns3);
            assertTrue(allAnswers.size() == 3);
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testUpdateOnInsertNoId() {
        UserAnswer testAns1 = new UserAnswer("chef");
        UserAnswer testAns2 = new UserAnswer("chef");

        answerDao.upsert(testAns1);
        answerDao.upsert(testAns2);

        List<UserAnswer> allAnswers;
        try {
            allAnswers = LiveDataTestUtil.getOrAwaitValue(answerDao.getAllAnswers());
            assertEquals(allAnswers.size(), 1);
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testUpdateOnInsertSameId() {
        UserAnswer testAns1 = new UserAnswer("chef");
        testAns1.setId(1);
        UserAnswer testAns2 = new UserAnswer("chef");
        testAns2.setId(1);

        answerDao.upsert(testAns1);
        answerDao.upsert(testAns2);

        List<UserAnswer> allAnswers;
        try {
            allAnswers = LiveDataTestUtil.getOrAwaitValue(answerDao.getAllAnswers());
            assertEquals(allAnswers.size(), 1);
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testUpdateOnInsertDiffId() {
        UserAnswer testAns1 = new UserAnswer("chef");
        testAns1.setId(1);
        UserAnswer testAns2 = new UserAnswer("chef");
        testAns2.setId(2);

        answerDao.upsert(testAns1);
        answerDao.upsert(testAns2);

        List<UserAnswer> allAnswers;
        try {
            allAnswers = LiveDataTestUtil.getOrAwaitValue(answerDao.getAllAnswers());
            assertEquals(allAnswers.size(), 1);
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testDelete() {
        //Recreate db from previous test
        UserAnswer testAns = new UserAnswer("chef");
        testAns.setId(1);

        answerDao.upsert(testAns);

        //Test delete() method
        answerDao.delete(testAns);

        List<UserAnswer> allAnswers;
        try {
            allAnswers = LiveDataTestUtil.getOrAwaitValue(answerDao.getAllAnswers());
            assertTrue(allAnswers.isEmpty());
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }

    @Test
    public void testDeleteAllAnswers() {
        //Recreate db from previous test
        UserAnswer testAns1 = new UserAnswer("chef");
        UserAnswer testAns2 = new UserAnswer("baker");
        UserAnswer testAns3 = new UserAnswer("musician");

        answerDao.upsert(testAns1);
        answerDao.upsert(testAns2);
        answerDao.upsert(testAns3);

        //Test method deleteAllAnswers()
        answerDao.deleteAllAnswers();

        List<UserAnswer> allAnswers;
        try {
            allAnswers = LiveDataTestUtil.getOrAwaitValue(answerDao.getAllAnswers());
            assertTrue(allAnswers.isEmpty());
        } catch(InterruptedException e) {
            System.err.println(e.getStackTrace());
        }
    }
}
