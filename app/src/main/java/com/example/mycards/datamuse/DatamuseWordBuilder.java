package com.example.mycards.datamuse;

import com.example.mycards.datamuse.pojo.DatamuseWord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//TODO - rename class
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

    //Sort by highest score first
    private static void sortEntries(List<DatamuseWord> entries) {
        Comparator<DatamuseWord> datamuseWordComparator = Comparator.comparingInt(DatamuseWord::getScore)
                .reversed();
        Collections.sort(entries, datamuseWordComparator);
    }

}
