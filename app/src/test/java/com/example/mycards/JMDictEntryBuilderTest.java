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
    public void singleCommonKanjiKana_singleGloss_getWordTest() {
        List<JMDictEntry> eligibleEntries = entryBuilder.getJMDictEntries("CD player");

        assertEquals(eligibleEntries.size(), 1);
    }

}