package com.example.mycards.datamuse;

import com.example.mycards.datamuse.pojo.DatamuseWord;
import com.example.mycards.datamuse.pojo.DatamuseWordCollectionRoot;
import com.example.mycards.jmdict.JMDictEntry;
import com.example.mycards.jmdict.JMDictEntryBuilder;
import com.example.mycards.jmdict.pojo.Gloss;
import com.example.mycards.jmdict.pojo.JMDictWord;
import com.example.mycards.jmdict.pojo.Kana;
import com.example.mycards.jmdict.pojo.Kanji;
import com.example.mycards.jmdict.pojo.Root;
import com.example.mycards.jmdict.pojo.Sense;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatamuseWordBuilder {
    private static DatamuseWordBuilder INSTANCE;    //Singleton

    private List<DatamuseWord> dMWords;

    /**
     * Constructor
     * @param jsonAsString json file that has been parsed into String format
     * @throws JsonProcessingException
     */
    private DatamuseWordBuilder(String jsonAsString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        this.dMWords = mapper.readValue(jsonAsString, new TypeReference<List<DatamuseWord>>() { });
    }

    /**
     * Implement this class as a Singleton
     *
     * @param jsonAsString
     * @return the single instance of this class
     * @throws JsonProcessingException
     */
    public static synchronized DatamuseWordBuilder getInstance(String jsonAsString) throws JsonProcessingException {
        //lazy instantiation
        if(INSTANCE == null) {
            INSTANCE = new DatamuseWordBuilder(jsonAsString);
        }
        return INSTANCE;
    }

    public List<DatamuseWord> getDatamuseWords() {
        sortEntries(dMWords);
        return dMWords;
    }

    private static void sortEntries(List<DatamuseWord> entries) {
        Comparator<DatamuseWord> datamuseWordComparator = Comparator.comparingInt(DatamuseWord::getScore);
        Collections.sort(entries, datamuseWordComparator);
    }

}
