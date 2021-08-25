package com.example.mycards.data.db;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.example.mycards.LiveDataTestUtil;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
    }

    @After  //executed after every test case
    public void tearDown() {
        database.close();
    }

    @Test
    public void testInsertAllValidEntries() {
        JMDictEntry entry1 = new JMDictEntry("to speed up", "1601080", new Kanji("早める"),
                new Kana("はやめる"), 3, 2, 2, 2,
                new ArrayList<>(List.of("v1", "vt")));
        JMDictEntry entry2 = new JMDictEntry("to speed up", "1400170", new Kanji("早まる"),
                new Kana("はやまる"), 3, 2, 3, 3,
                new ArrayList<>(List.of("v5r", "vi")));
        JMDictEntry entry3 = new JMDictEntry("inspection", "1312040", new Kanji("視察"),
                new Kana("しさつ"), 2, 1, 1, 1,
                new ArrayList<>(List.of("n", "vs", "adj")));

        List<JMDictEntry> testEntries = List.of(entry1, entry2, entry3);

        jmDictEntryDao.insertAll(testEntries);

        try {
            //NB: indirectly testing getAllJMDictEntries() works here
            List<JMDictEntry> getEntries = LiveDataTestUtil.getOrAwaitValue(jmDictEntryDao.getAllJMDictEntries());
            assertEquals(3, getEntries.size());
            assertTrue(getEntries.contains(entry1));
            assertTrue(getEntries.contains(entry2));
            assertTrue(getEntries.contains(entry3));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertAll_NullInnerGlossFails_NoException() {
        JMDictEntry entry1 = new JMDictEntry(null, "1601080", new Kanji("早める"),
                new Kana("はやめる"), 3, 2, 2, 2,
                new ArrayList<>(List.of("v1", "vt")));
        JMDictEntry entry2 = new JMDictEntry("to speed up", "1400170", new Kanji("早まる"),
                new Kana("はやまる"), 3, 2, 3, 3,
                new ArrayList<>(List.of("v5r", "vi")));
        JMDictEntry entry3 = new JMDictEntry("inspection", "1312040", new Kanji("視察"),
                new Kana("しさつ"), 2, 1, 1, 1,
                new ArrayList<>(List.of("n", "vs", "adj")));

        List<JMDictEntry> testEntries = List.of(entry1, entry2, entry3);

        jmDictEntryDao.insertAll(testEntries);

        try {
            //NB: indirectly testing getAllJMDictEntries() works here
            List<JMDictEntry> getEntries = LiveDataTestUtil.getOrAwaitValue(jmDictEntryDao.getAllJMDictEntries());
            assertEquals(2, getEntries.size());
            assertFalse(getEntries.contains(entry1));
            assertTrue(getEntries.contains(entry2));
            assertTrue(getEntries.contains(entry3));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertAll_NullWordIdFails_NoException() {
        JMDictEntry entry1 = new JMDictEntry("to speed up", null, new Kanji("早める"),
                new Kana("はやめる"), 3, 2, 2, 2,
                new ArrayList<>(List.of("v1", "vt")));
        JMDictEntry entry2 = new JMDictEntry("to speed up", "1400170", new Kanji("早まる"),
                new Kana("はやまる"), 3, 2, 3, 3,
                new ArrayList<>(List.of("v5r", "vi")));
        JMDictEntry entry3 = new JMDictEntry("inspection", "1312040", new Kanji("視察"),
                new Kana("しさつ"), 2, 1, 1, 1,
                new ArrayList<>(List.of("n", "vs", "adj")));

        List<JMDictEntry> testEntries = List.of(entry1, entry2, entry3);

        jmDictEntryDao.insertAll(testEntries);

        try {
            //NB: indirectly testing getAllJMDictEntries() works here
            List<JMDictEntry> getEntries = LiveDataTestUtil.getOrAwaitValue(jmDictEntryDao.getAllJMDictEntries());
            assertEquals(2, getEntries.size());

            assertTrue(getEntries.contains(entry2));
            assertTrue(getEntries.contains(entry3));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertAll_NullValuesExceptInnerGlossAndWordIdPass() {
        JMDictEntry nullKanji = new JMDictEntry("to speed up", "1601080", null,
                new Kana("はやめる"), 3, 2, 2, 2,
                new ArrayList<>(List.of("v1", "vt")));
        JMDictEntry nullKana = new JMDictEntry("to speed up", "1400170", new Kanji("早まる"),
                null, 3, 2, 3, 3,
                new ArrayList<>(List.of("v5r", "vi")));
        JMDictEntry nullSensePosTag = new JMDictEntry("inspection", "1312040", new Kanji("視察"),
                new Kana("しさつ"), 2, 1, 1, 1,
                null);

        List<JMDictEntry> testEntries = List.of(nullKanji, nullKana, nullSensePosTag);

        jmDictEntryDao.insertAll(testEntries);

        try {
            //NB: indirectly testing getAllJMDictEntries() works here
            List<JMDictEntry> getEntries = LiveDataTestUtil.getOrAwaitValue(jmDictEntryDao.getAllJMDictEntries());
            assertEquals(3, getEntries.size());

            assertTrue(getEntries.contains(nullKanji));
            assertTrue(getEntries.contains(nullKana));
            assertTrue(getEntries.contains(nullSensePosTag));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertAll_DuplicatesNotDoubleSaved() {
        //indirectly test equality of JMDictEntry class here

        JMDictEntry entry = new JMDictEntry("to speed up", "1601080", new Kanji("早める"),
                new Kana("はやめる"), 3, 2, 2, 2,
                new ArrayList<>(List.of("v1", "vt")));
        JMDictEntry exactDuplicate = new JMDictEntry("to speed up", "1601080", new Kanji("早める"),
                new Kana("はやめる"), 3, 2, 2, 2,
                new ArrayList<>(List.of("v1", "vt")));


        List<JMDictEntry> testEntries = List.of(entry, exactDuplicate);

        jmDictEntryDao.insertAll(testEntries);

        try {
            //NB: indirectly testing getAllJMDictEntries() works here
            List<JMDictEntry> getEntries = LiveDataTestUtil.getOrAwaitValue(jmDictEntryDao.getAllJMDictEntries());
            assertEquals(1, getEntries.size());
            assertTrue(getEntries.contains(entry));
            assertTrue(getEntries.contains(exactDuplicate));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertAll_DuplicateWordIdInnerGlossNotSaved() {
        JMDictEntry entry = new JMDictEntry("to speed up", "1601080", new Kanji("早める"),
                new Kana("はやめる"), 3, 2, 2, 2,
                new ArrayList<>(List.of("v1", "vt")));
        JMDictEntry duplicateWordIdInnerGlossOnly = new JMDictEntry("to speed up", "1601080", new Kanji("異なる言葉"),
                new Kana("ことなることば"), 0,0, 0, 0,
                new ArrayList<>(List.of("n")));

        List<JMDictEntry> testEntries = List.of(entry, duplicateWordIdInnerGlossOnly);

        jmDictEntryDao.insertAll(testEntries);

        try {
            //NB: indirectly testing getAllJMDictEntries() works here
            List<JMDictEntry> getEntries = LiveDataTestUtil.getOrAwaitValue(jmDictEntryDao.getAllJMDictEntries());
            assertEquals(1, getEntries.size());
            assertTrue(getEntries.contains(entry));
            assertTrue(getEntries.contains(duplicateWordIdInnerGlossOnly));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpsertValidEntry_testDelete() {
        //upsert also uses Room's @Insert annotation so this can be argued to have been tested via the insertAll tests
        JMDictEntry entry = new JMDictEntry("to speed up", "1601080", new Kanji("早める"),
                new Kana("はやめる"), 3, 2, 2, 2,
                new ArrayList<>(List.of("v1", "vt")));


        jmDictEntryDao.upsert(entry);

        try {
            //NB: indirectly testing getAllJMDictEntries() works here
            List<JMDictEntry> getEntries = LiveDataTestUtil.getOrAwaitValue(jmDictEntryDao.getAllJMDictEntries());
            assertEquals(1, getEntries.size());
            assertTrue(getEntries.contains(entry));

            //Test delete
            jmDictEntryDao.delete(entry);
            List<JMDictEntry> dbAfterDeletion = LiveDataTestUtil.getOrAwaitValue(jmDictEntryDao.getAllJMDictEntries());
            assertTrue(dbAfterDeletion.isEmpty());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteAllIfEmpty_NoException() {
        //Nothing happens if we call this and the db is empty. No exception is thrown.
        jmDictEntryDao.deleteAllJMDictEntries();
    }

    @Test
    public void testDeleteSpecificEntryIfEmpty_NoException() {
        JMDictEntry pointlessEntry = new JMDictEntry("to speed up", "1601080", new Kanji("早める"),
                new Kana("はやめる"), 3, 2, 2, 2,
                new ArrayList<>(List.of("v1", "vt")));

        //above is not inserted but we attempt a deletion anyway. No exception is thrown.
        jmDictEntryDao.delete(pointlessEntry);
    }

    @Test
    public void testGetFirstEntry() {
        //    Reminder of the method and SQL query:
        //    @Query("SELECT * FROM jmdict WHERE inner_gloss = :gloss ORDER BY gloss_order, sense_order, gloss_count LIMIT 1")
        //    public JMDictEntry getFirstJMDictEntry(String gloss);

        JMDictEntry entry1 = new JMDictEntry("to speed up", "1601080", new Kanji("早める"),
                new Kana("はやめる"), 3, 2, 2, 2,
                new ArrayList<>(List.of("v1", "vt")));
        JMDictEntry entry2 = new JMDictEntry("to speed up", "1400170", new Kanji("早まる"),
                new Kana("はやまる"), 3, 2, 3, 3,
                new ArrayList<>(List.of("v5r", "vi")));
        JMDictEntry entry3 = new JMDictEntry("to speed up", "1365990", new Kanji("進める"),
                new Kana("すすめる"), 7, 7, 3, 2,
                new ArrayList<>(List.of("v1","vt")));

        List<JMDictEntry> testEntries = List.of(entry1, entry2, entry3);

        jmDictEntryDao.insertAll(testEntries);

        try {
            //check the db is full
            List<JMDictEntry> allEntries = LiveDataTestUtil.getOrAwaitValue(jmDictEntryDao.getAllJMDictEntries());
            assertEquals(3, allEntries.size());

            //Don't need to use helper class LiveDataTestUtil as we are not using LiveData for getFirst
            JMDictEntry firstEntry = jmDictEntryDao.getFirstJMDictEntry("to speed up");
            assertEquals(entry1, firstEntry);

            jmDictEntryDao.delete(entry1);
            JMDictEntry secondEntry = jmDictEntryDao.getFirstJMDictEntry("to speed up");
            assertEquals(entry2, secondEntry);

            jmDictEntryDao.delete(entry2);
            JMDictEntry thirdEntry = jmDictEntryDao.getFirstJMDictEntry("to speed up");
            assertEquals(entry3, thirdEntry);

            jmDictEntryDao.delete(entry3);
            allEntries = LiveDataTestUtil.getOrAwaitValue(jmDictEntryDao.getAllJMDictEntries());
            assertTrue(allEntries.isEmpty());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetFirstEntryNoResult_getsNull_NoException() {
        //We don't use LiveData -> returns null if no result
        JMDictEntry noEntry = jmDictEntryDao.getFirstJMDictEntry("no word");
        assertNull(noEntry);
    }

    @Test
    public void testGetAllWithString_DifferentStrings_ExactStringMatch() {
        //
        JMDictEntry entry1 = new JMDictEntry("to speed up", "1601080", new Kanji("早める"),
                new Kana("はやめる"), 3, 2, 2, 2,
                new ArrayList<>(List.of("v1", "vt")));
        JMDictEntry entry2 = new JMDictEntry("to speed up", "1400170", new Kanji("早まる"),
                new Kana("はやまる"), 3, 2, 3, 3,
                new ArrayList<>(List.of("v5r", "vi")));
        JMDictEntry entry3 = new JMDictEntry("inspection", "1312040", new Kanji("視察"),
                new Kana("しさつ"), 2, 1, 1, 1,
                new ArrayList<>(List.of("n", "vs", "adj")));

        List<JMDictEntry> testEntries = List.of(entry1, entry2, entry3);

        //NB: indirectly testing insertAll() works here
        jmDictEntryDao.insertAll(testEntries);

        try {
            List<JMDictEntry> speedEntries = LiveDataTestUtil.getOrAwaitValue(jmDictEntryDao.getAllJMDictEntries("to speed up"));
            assertEquals(2, speedEntries.size());
            assertTrue(speedEntries.contains(entry1));
            assertTrue(speedEntries.contains(entry2));

            List<JMDictEntry> inspectionEntries = LiveDataTestUtil.getOrAwaitValue(jmDictEntryDao.getAllJMDictEntries("inspection"));
            assertEquals(1, inspectionEntries.size());
            assertTrue(inspectionEntries.contains(entry3));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAllWithString_NotExactStringMatches() {
        //TODO - test pattern matching and trim here (unless in another class)
    }

    @Test
    public void testGetAllWithStringNoResult_NotNull_NoException() {
        try {
            List<JMDictEntry> noEntries = LiveDataTestUtil.getOrAwaitValue(jmDictEntryDao.getAllJMDictEntries("no words"));
            assertTrue(noEntries.isEmpty());
            assertNotNull(noEntries);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAllWithOutParamNoResult_NotNull_NoException() {
        try {
            List<JMDictEntry> noEntries = LiveDataTestUtil.getOrAwaitValue(jmDictEntryDao.getAllJMDictEntries());
            assertTrue(noEntries.isEmpty());
            assertNotNull(noEntries);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
