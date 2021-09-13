package com.example.mycards.usecases.jptranslate.jmdict.typeconverters;

import com.example.mycards.usecases.jptranslate.jmdict.pojo.Kana;
import com.example.mycards.usecases.jptranslate.jmdict.pojo.Kanji;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

//Note: Indirectly tests equality in Kanji and Kana
public class JMDictEntryTypeConvertersTest {

    @Test
    public void stringToKanjiTest() {
        String json = "{\"text\":\"漢字\"}";
        Kanji expectedKanji = new Kanji("漢字");

        Kanji resultKanji = JMDictEntryTypeConverters.stringToKanji(json);

        assertEquals(expectedKanji, resultKanji);
    }

    @Test
    public void kanjiToString() {
        Kanji inputKanji = new Kanji("漢字");
        String expectedJson = "{\"kanjiText\":\"漢字\"}";

        String actualJson = JMDictEntryTypeConverters.kanjiToString(inputKanji);

        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void stringToKana() {
        String json = "{\"text\":\"かな\"}";
        Kana expectedKana = new Kana("かな");

        Kana resultKana = JMDictEntryTypeConverters.stringToKana(json);

        assertEquals(expectedKana, resultKana);
    }

    @Test
    public void kanaToString() {
        Kana inputKana = new Kana("かな");
        String expectedJson = "{\"kanaText\":\"かな\"}";

        String actualJson = JMDictEntryTypeConverters.kanaToString(inputKana);

        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void stringToListString() {
        //Method used to convert "sensePosTags" in the format:
        //  "sensePosTags:["v1","vt"]
        String stringInput = "[\"String 1\",\"String 2\",\"String 3\"]";
        List<String> expectedListOutput = new ArrayList<>(List.of("String 1", "String 2", "String 3"));

        assertEquals(expectedListOutput, JMDictEntryTypeConverters.stringToListString(stringInput));
    }

    @Test
    public void listStringToString() {
        List<String> listOfString = new ArrayList<>(List.of("String 1", "String 2", "String 3"));
        String expectedStringOutput = "[\"String 1\",\"String 2\",\"String 3\"]";

        assertEquals(expectedStringOutput, JMDictEntryTypeConverters.listStringToString(listOfString));
    }
}