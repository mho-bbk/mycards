package com.example.mycards;

import com.example.mycards.data.entities.JMDictEntry;
import com.example.mycards.jmdict.JMDictEntryBuilder;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

public class JMDictEntryBuilderTest {
    private JMDictEntryBuilder entryBuilder;

    @Before
    public void setUp() throws Exception {
        InputStream input = getClass()
                .getResourceAsStream("jmdict_eng_common_3_1_0_sample.json");
        entryBuilder = new JMDictEntryBuilder(input);
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

    //multiCommonKanjiKana
    @Test
    public void multiCommonKanjiKana_multiGlossWord_getWordTest() {
        //TODO - find word, or make one up
    }

    //glossWithMultiWords
    @Test
    public void multiCommonKanjiKana_glossWithMultiWords_getWordTest() {
        //TODO - candidate: obvious - add another word that means obvious to file
    }

    @Test
    public void multiSense_glossNotInFirstSense_getWordTest() {
        List<JMDictEntry> eligibleEntries = entryBuilder.getJMDictEntries("genitals");

        //Reflects num of Japanese words with that definition, not num of Sense in the word
        assertEquals(eligibleEntries.size(), 1);

        JMDictEntry entry = eligibleEntries.get(0);

        assertEquals(entry.getEngDef(), "genitals");
        assertEquals(entry.getKana().getText(), "あそこ");
        assertEquals(entry.getKanji().getText(), "彼処");
        assertEquals(entry.getWordID(), "1000320");
    }
}