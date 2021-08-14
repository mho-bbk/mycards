package com.example.mycards;

import com.example.mycards.jmdict.JMDictEntryBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

//Other tests?:
// Do we want to return J words with only exact string matches? 'Contains' could widen pool too much?

public class JMDictEntryBuilderTest {
    private InputStream input;
    private JMDictEntryBuilder entryBuilder;

    @Before
    public void setUp() throws Exception {
        input = getClass()
                .getResourceAsStream("reverse_jmdictentries_plain_sample.json");
        entryBuilder = JMDictEntryBuilder.getInstance(input);
    }

    @After
    public void tearDown() throws Exception {
        input.close();
    }

    @Test
    public void singleCommonKanjiKana_singleGlossWord_getWordTest() {
//        List<JMDictEntry> eligibleEntries = entryBuilder.getJMDictEntries("CD player");
//
//        assertEquals(eligibleEntries.size(), 1);
//
//        JMDictEntry entry = eligibleEntries.get(0);
//
//        assertEquals(entry.getEngDef(), "CD player");
//        assertEquals(entry.getKana().getText(), "シーディープレーヤー");
//        assertEquals(entry.getKanji().getText(), "ＣＤプレーヤー");
//        assertEquals(entry.getWordID(), "1000110");
//        assertEquals(entry.getGlossCount(), 1);
//        assertEquals(entry.getGlossOrder(), 1);
//        assertEquals(entry.getSenseCount(), 1);
//        assertEquals(entry.getSenseOrder(), 1);
    }

    @Test
    public void singleCommonKanjiKana_multiGlossWord_getWordTest() {
//        List<JMDictEntry> eligibleEntries = entryBuilder.getJMDictEntries("bikini thong");
//
//        assertEquals(eligibleEntries.size(), 1);
//
//        JMDictEntry entry = eligibleEntries.get(0);
//
//        assertEquals(entry.getEngDef(), "bikini thong");
//        assertEquals(entry.getKana().getText(), "ティーバック");
//        assertEquals(entry.getKanji().getText(), "Ｔバック");
//        assertEquals(entry.getWordID(), "1000170");
//        assertEquals(entry.getGlossCount(), 2);
//        assertEquals(entry.getGlossOrder(), 2);
//        assertEquals(entry.getSenseCount(), 1);
//        assertEquals(entry.getSenseOrder(), 1);
    }

    @Test
    public void multiCommonKanjiKana_multiGlossWord_getWordTest() {
//        List<JMDictEntry> eligibleEntries = entryBuilder.getJMDictEntries("fake_definition");
//
//        assertEquals(eligibleEntries.size(), 1);
//
//        JMDictEntry entry = eligibleEntries.get(0);
//
//        assertEquals(entry.getEngDef(), "fake_definition");
//        assertEquals(entry.getKana().getText(), "fake_kana1");
//        assertEquals(entry.getKanji().getText(), "fake_kanji1");
//        assertEquals(entry.getWordID(), "XXXXXX");
    }

    @Test
    public void multiCommonKanjiKana_glossWithMultiWords_getWordTest() {
//        List<JMDictEntry> eligibleEntries = entryBuilder.getJMDictEntries("obvious");
//        JMDictEntry testTerm1 = new JMDictEntry();
//        JMDictEntry testTerm2 = new JMDictEntry();
//
//        //set up testTerm1
//        testTerm1.setWordID("1260640");
//        testTerm1.setKanji(new Kanji("顕著"));
//        testTerm1.setKana(new Kana("けんちょ"));
//        testTerm1.setEngDef("obvious");
//
//        //set up testTerm2
//        testTerm2.setWordID("1000220");
//        testTerm1.setKanji(new Kanji("明白"));
//        testTerm1.setKana(new Kana("めいはく"));
//        testTerm1.setEngDef("obvious");
//
//        //Reflects num of Japanese words with that definition
//        assertEquals(eligibleEntries.size(), 2);
//        assertTrue(eligibleEntries.contains(testTerm1));
//        assertTrue(eligibleEntries.contains(testTerm2));
//
//        JMDictEntry entry1 = eligibleEntries.get(0);
//        JMDictEntry entry2 = eligibleEntries.get(1);
//
//        //assume order unknown
//        assertTrue(entry1.equals(testTerm1) || entry1.equals(testTerm2));
//        assertTrue(entry2.equals(testTerm1) || entry2.equals(testTerm2));
//        assertNotEquals(entry1, entry2);
//
//        for (JMDictEntry entry: eligibleEntries) {
//            if(entry.equals(testTerm1)) {
//                assertEquals(entry.getGlossCount(), 3);
//                assertEquals(entry.getGlossOrder(), 3);
//                assertEquals(entry.getSenseCount(), 1);
//                assertEquals(entry.getSenseOrder(), 1);
//            } else {
//                assertEquals(entry.getGlossCount(), 7);
//                assertEquals(entry.getGlossOrder(), 1);
//                assertEquals(entry.getSenseCount(), 1);
//                assertEquals(entry.getSenseOrder(), 1);
//            }
//        }
    }

    @Test
    public void multiSense_glossNotInFirstSense_multiGloss_getWordTest() {
//        List<JMDictEntry> eligibleEntries = entryBuilder.getJMDictEntries("accountant");
//
//        //Reflects num of Japanese words with that definition, not num of Sense in the word
//        assertEquals(eligibleEntries.size(), 1);
//
//        JMDictEntry entry = eligibleEntries.get(0);
//
//        assertEquals(entry.getEngDef(), "accountant");
//        assertEquals(entry.getKana().getText(), "かいけい");
//        assertEquals(entry.getKanji().getText(), "会計");
//        assertEquals(entry.getWordID(), "1198430");
//
//        assertEquals(entry.getGlossCount(), 4);
//        assertEquals(entry.getGlossOrder(), 1);
//        assertEquals(entry.getSenseCount(), 5);
//        assertEquals(entry.getSenseOrder(), 3);
    }

    @Test
    public void nullKanjiSingleCommonKana_getWordTest() {
//        List<JMDictEntry> eligibleEntries = entryBuilder.getJMDictEntries("plainly");
//
//        //Reflects num of Japanese words with that definition, not num of Sense in the word
//        assertEquals(eligibleEntries.size(), 1);
//
//        JMDictEntry entry = eligibleEntries.get(0);
//
//        assertEquals(entry.getEngDef(), "plainly");
//        assertEquals(entry.getKana().getText(), "あっさり");
//        assertEquals(entry.getKanji().getText(), "");
//        assertEquals(entry.getWordID(), "1000360");
//
//        assertEquals(entry.getGlossCount(), 3);
//        assertEquals(entry.getGlossOrder(), 2);
//        assertEquals(entry.getSenseCount(), 2);
//        assertEquals(entry.getSenseOrder(), 2);
    }

    @Test
    public void testSortEntries() {
//        List<JMDictEntry> eligibleEntries = entryBuilder.getJMDictEntries("history");
//        JMDictEntry testTerm1 = new JMDictEntry();
//        JMDictEntry testTerm2 = new JMDictEntry();
//
//
//        //set up testTerm1
//        testTerm1.setWordID("1176730");
//        testTerm1.setKanji(new Kanji("沿革"));
//        testTerm1.setKana(new Kana("えんかく"));
//        testTerm1.setEngDef("history");
//
//        //set up testTerm2
//        testTerm2.setWordID("1558050");
//        testTerm1.setKanji(new Kanji("歴史"));
//        testTerm1.setKana(new Kana("れきし"));
//        testTerm1.setEngDef("history");
//
//        assertEquals(eligibleEntries.size(), 2);
//        assertTrue(eligibleEntries.contains(testTerm1));
//        assertTrue(eligibleEntries.contains(testTerm2));
//
//        for (JMDictEntry entry: eligibleEntries) {
//            if(entry.equals(testTerm1)) {
//                assertEquals(entry.getGlossCount(), 2);
//                assertEquals(entry.getGlossOrder(), 1);
//                assertEquals(entry.getSenseCount(), 1);
//                assertEquals(entry.getSenseOrder(), 1);
//            } else {
//                assertEquals(entry.getGlossCount(), 1);
//                assertEquals(entry.getGlossOrder(), 1);
//                assertEquals(entry.getSenseCount(), 1);
//                assertEquals(entry.getSenseOrder(), 1);
//            }
//        }
//
//        assertEquals(eligibleEntries.get(0), testTerm2);    //testTerm2 has better stats, should be sorted first
//        assertEquals(eligibleEntries.get(1), testTerm1);    //testTerm1 has worse stats, should be sorted second

    }

    @Test
    public void testMatchEntryAccountsForBrackets() {
        //first word
//        List<JMDictEntry> eligibleEntriesCat = entryBuilder.getJMDictEntries("cat");
//
//        assertEquals(eligibleEntriesCat.size(), 1);
//
//        JMDictEntry entryCat = eligibleEntriesCat.get(0);
//
//        assertEquals(entryCat.getEngDef(), "cat");
//        assertEquals(entryCat.getKana().getText(), "ねこ");
//        assertEquals(entryCat.getKanji().getText(), "猫");
//        assertEquals(entryCat.getWordID(), "1467640");
//
//        //second word
//        List<JMDictEntry> eligibleEntriesTeacher = entryBuilder.getJMDictEntries("teacher");
//
//        assertEquals(eligibleEntriesCat.size(), 1);
//
//        JMDictEntry entryTeacher = eligibleEntriesTeacher.get(0);
//
//        assertEquals(entryTeacher.getEngDef(), "teacher");
//        assertEquals(entryTeacher.getKanji().getText(), "教師");
//        assertEquals(entryTeacher.getKana().getText(), "きょうし");
//        assertEquals(entryTeacher.getWordID(), "1237130");
    }

    @Test
    public void testMatchEntryNoSpaceOrOtherCharAtStart() {
//        List<JMDictEntry> eligibleEntriesCat = entryBuilder.getJMDictEntries("cat");
//        JMDictEntry testTermNotIncl = new JMDictEntry();
//
//        //Equality currently based on word id so only need to set this
//        testTermNotIncl.setWordID("1303100");
//
//        assertFalse(eligibleEntriesCat.contains(testTermNotIncl));
    }

    //TODO - difficult cards - keeps coming back with just kana, eg reading should be dokusho, not katakana reading
    //TODO - as dokusho but with 'science'
    //TODO - 'math' doesn't work for mathematics
}