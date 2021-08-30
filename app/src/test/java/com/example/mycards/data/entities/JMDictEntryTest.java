package com.example.mycards.data.entities;

import com.example.mycards.usecases.jptranslate.jmdict.pojo.Kana;
import com.example.mycards.usecases.jptranslate.jmdict.pojo.Kanji;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class JMDictEntryTest {

    @Test
    public void testEquals() {
        //Custom equality defined on gloss and id. Same as @Index Room annotations.
        JMDictEntry entry1 = new JMDictEntry("to speed up", "1601080", new Kanji("早める"),
                new Kana("はやめる"), 3, 2, 2, 2,
                new ArrayList<>(List.of("v1", "vt")));
        JMDictEntry entry1Duplicate = new JMDictEntry("to speed up", "1601080", new Kanji("早める"),
                new Kana("はやめる"), 3, 2, 2, 2,
                new ArrayList<>(List.of("v1", "vt")));
        JMDictEntry entry1DiffWordId = new JMDictEntry("to speed up", "12345", new Kanji("早める"),
                new Kana("はやめる"), 3, 2, 2, 2,
                new ArrayList<>(List.of("v1", "vt")));
        JMDictEntry entry1DiffGloss = new JMDictEntry("tospeedup", "1601080", new Kanji("早める"),
                new Kana("はやめる"), 3, 2, 2, 2,
                new ArrayList<>(List.of("v1", "vt")));
        JMDictEntry entry1DiffEverythingExceptGlossAndId = new JMDictEntry("to speed up", "1601080", new Kanji("早まる"),
                new Kana("はやまる"), 3, 2, 3, 3,
                new ArrayList<>(List.of("v5r", "vi")));

        JMDictEntry entirelyDifferentEntry = new JMDictEntry("inspection", "1312040", new Kanji("視察"),
                new Kana("しさつ"), 2, 1, 1, 1,
                new ArrayList<>(List.of("n", "vs", "adj")));

        assertEquals(entry1, entry1Duplicate);
        assertNotEquals(entry1, entry1DiffWordId);
        assertNotEquals(entry1, entry1DiffGloss);
        assertEquals(entry1, entry1DiffEverythingExceptGlossAndId);
        assertNotEquals(entry1, entirelyDifferentEntry);
    }

    @Test
    public void testSensePosTagManipulation() {
        //TODO - This is not really a test, but an experiment with sensePosTag possibilities...
        JMDictEntry entry1 = new JMDictEntry("to speed up", "1601080", new Kanji("早める"),
                new Kana("はやめる"), 3, 2, 2, 2,
                new ArrayList<>(List.of("v1", "vt", "fakeTag")));
        JMDictEntry entry2 = new JMDictEntry("to speed up", "1400170", new Kanji("早まる"),
                new Kana("はやまる"), 3, 2, 3, 3,
                new ArrayList<>(List.of("v5r", "vi", "fakeTag")));

        List<String> entry1SensePosTags = entry1.getSensePosTags();
        List<String> entry2SensePosTags = entry2.getSensePosTags();

        Set<String> commonTagsReal = entry1SensePosTags.stream()
                .distinct()
                .filter(entry2SensePosTags::contains)
                .collect(Collectors.toSet());

        Set<String> commonTagsExpected = new HashSet<>(Collections.singletonList("fakeTag"));

        assertEquals(commonTagsExpected, commonTagsReal);
    }
}