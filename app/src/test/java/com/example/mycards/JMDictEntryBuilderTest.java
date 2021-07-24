package com.example.mycards;

import com.example.mycards.jmdict.JMDictEntry;
import com.example.mycards.jmdict.JMDictEntryBuilder;
import com.example.mycards.jmdict.pojo.Kana;
import com.example.mycards.jmdict.pojo.Kanji;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

//Other tests?:
// Do we want to return J words with only exact string matches? 'Contains' could widen pool too much?

public class JMDictEntryBuilderTest {
    private InputStream input;
    private JMDictEntryBuilder entryBuilder;

    @Before
    public void setUp() throws Exception {
        input = getClass()
                .getResourceAsStream("jmdict_eng_common_3_1_0_sample.json");
        entryBuilder = JMDictEntryBuilder.getInstance(input);
    }

    @After
    public void tearDown() throws Exception {
        input.close();
    }

    @Test
    public void singleCommonKanjiKana_singleGlossWord_getWordTest() {
        List<JMDictEntry> eligibleEntries = entryBuilder.getJMDictEntries("CD player");

        assertEquals(eligibleEntries.size(), 1);

        JMDictEntry entry = eligibleEntries.get(0);

        assertEquals(entry.getEngDef(), "CD player");
        assertEquals(entry.getKana().getText(), "シーディープレーヤー");
        assertEquals(entry.getKanji().getText(), "ＣＤプレーヤー");
        assertEquals(entry.getWordID(), "1000110");
    }

    @Test
    public void singleCommonKanjiKana_multiGlossWord_getWordTest() {
        List<JMDictEntry> eligibleEntries = entryBuilder.getJMDictEntries("T-back");

        assertEquals(eligibleEntries.size(), 1);

        JMDictEntry entry = eligibleEntries.get(0);

        assertEquals(entry.getEngDef(), "T-back");
        assertEquals(entry.getKana().getText(), "ティーバック");
        assertEquals(entry.getKanji().getText(), "Ｔバック");
        assertEquals(entry.getWordID(), "1000170");
    }

    @Test
    public void multiCommonKanjiKana_multiGlossWord_getWordTest() {
        List<JMDictEntry> eligibleEntries = entryBuilder.getJMDictEntries("fake_definition");

        assertEquals(eligibleEntries.size(), 1);

        JMDictEntry entry = eligibleEntries.get(0);

        assertEquals(entry.getEngDef(), "fake_definition");
        assertEquals(entry.getKana().getText(), "fake_kana1");
        assertEquals(entry.getKanji().getText(), "fake_kanji1");
        assertEquals(entry.getWordID(), "XXXXXX");
    }

    @Test
    public void multiCommonKanjiKana_glossWithMultiWords_getWordTest() {
        List<JMDictEntry> eligibleEntries = entryBuilder.getJMDictEntries("obvious");
        JMDictEntry testTerm1 = new JMDictEntry();
        JMDictEntry testTerm2 = new JMDictEntry();

        //set up testTerm1
        testTerm1.setWordID("1260640");
        testTerm1.setKanji(new Kanji("顕著"));
        testTerm1.setKana(new Kana("けんちょ"));
        testTerm1.setEngDef("obvious");

        //set up testTerm2
        testTerm2.setWordID("1000220");
        testTerm1.setKanji(new Kanji("明白"));
        testTerm1.setKana(new Kana("めいはく"));
        testTerm1.setEngDef("obvious");

        //Reflects num of Japanese words with that definition
        assertEquals(eligibleEntries.size(), 2);
        assertTrue(eligibleEntries.contains(testTerm1));
        assertTrue(eligibleEntries.contains(testTerm2));

        JMDictEntry entry1 = eligibleEntries.get(0);
        JMDictEntry entry2 = eligibleEntries.get(1);

        //assume order unknown
        assertTrue(entry1.equals(testTerm1) || entry1.equals(testTerm2));
        assertTrue(entry2.equals(testTerm1) || entry2.equals(testTerm2));
        assertNotEquals(entry1, entry2);
    }

    @Test
    public void multiSense_glossNotInFirstSense_multiGloss_getWordTest() {
        List<JMDictEntry> eligibleEntries = entryBuilder.getJMDictEntries("accountant");

        //Reflects num of Japanese words with that definition, not num of Sense in the word
        assertEquals(eligibleEntries.size(), 1);

        JMDictEntry entry = eligibleEntries.get(0);

        assertEquals(entry.getEngDef(), "accountant");
        assertEquals(entry.getKana().getText(), "かいけい");
        assertEquals(entry.getKanji().getText(), "会計");
        assertEquals(entry.getWordID(), "1198430");
    }

    @Test
    public void nullKanjiSingleCommonKana_getWordTest() {
        List<JMDictEntry> eligibleEntries = entryBuilder.getJMDictEntries("plainly");

        //Reflects num of Japanese words with that definition, not num of Sense in the word
        assertEquals(eligibleEntries.size(), 1);

        JMDictEntry entry = eligibleEntries.get(0);

        assertEquals(entry.getEngDef(), "plainly");
        assertEquals(entry.getKana().getText(), "あっさり");
        assertEquals(entry.getKanji().getText(), "");
        assertEquals(entry.getWordID(), "1000360");
    }

}