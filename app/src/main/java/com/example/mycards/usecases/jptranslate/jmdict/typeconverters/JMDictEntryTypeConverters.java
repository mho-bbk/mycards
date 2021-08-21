package com.example.mycards.usecases.jptranslate.jmdict.typeconverters;

import androidx.room.TypeConverter;

import com.example.mycards.usecases.jptranslate.jmdict.pojo.Kana;
import com.example.mycards.usecases.jptranslate.jmdict.pojo.Kanji;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

//TODO - test this. Unsure correct String param for stringToX methods or the correct result from xToString
public class JMDictEntryTypeConverters {

    @TypeConverter
    public static Kanji stringToKanji(String json) {
        ObjectMapper mapper = new ObjectMapper();
        Kanji kanji = null;

        try {
            kanji = mapper.readValue(json, Kanji.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return kanji;
    }

    @TypeConverter
    public static String kanjiToString(Kanji kanji) {
        ObjectMapper mapper = new ObjectMapper();
        String kanjiAsString = null;

        try {
            kanjiAsString = mapper.writeValueAsString(kanji);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return  kanjiAsString;
    }

    @TypeConverter
    public static Kana stringToKana(String json) {
        ObjectMapper mapper = new ObjectMapper();
        Kana kana = null;

        try {
            kana = mapper.readValue(json, Kana.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return kana;
    }

    @TypeConverter
    public static String kanaToString(Kana kana) {
        ObjectMapper mapper = new ObjectMapper();
        String kanaAsString = null;

        try {
            kanaAsString = mapper.writeValueAsString(kana);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return  kanaAsString;
    }

    @TypeConverter
    public static List<String> stringToListString(String string) {
        ObjectMapper mapper = new ObjectMapper();
        List<String> stringList = null;

        try {
            stringList = mapper.readValue(string, new TypeReference<List<String>>() { });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return stringList;
    }

    @TypeConverter
    public static String listStringToString(List<String> json) {
        ObjectMapper mapper = new ObjectMapper();
        String resultString = null;

        try {
            resultString = mapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return resultString;
    }
}
