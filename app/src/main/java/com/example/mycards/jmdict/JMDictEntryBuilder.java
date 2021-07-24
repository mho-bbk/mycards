package com.example.mycards.jmdict;

import androidx.room.Room;

import com.example.mycards.data.db.CardEntityDatabase;
import com.example.mycards.jmdict.pojo.Gloss;
import com.example.mycards.jmdict.pojo.Kana;
import com.example.mycards.jmdict.pojo.Kanji;
import com.example.mycards.jmdict.pojo.Sense;
import com.example.mycards.jmdict.pojo.Word;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Helper class that stores the JMDict json file as a root, extracts the list of words,
 * finds the List of words that match a given String input and returns those words.
 */
public class JMDictEntryBuilder {

    private static JMDictEntryBuilder INSTANCE; //Singleton

    private JMDictJSONRoot root;
    private List<Word> words;

    /**
     * Constructor populates root and List of words
     * @param jsonFileAsInputStream reference to the right json file in resources/raw
     * @throws IOException
     */
    private JMDictEntryBuilder(InputStream jsonFileAsInputStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.root = mapper.readValue(jsonFileAsInputStream, JMDictJSONRoot.class);
        this.words = root.getWords();
    }

    /**
     * Implement this class as a Singleton
     *
     * @param jsonFileAsInputStream
     * @return the single instance of this class
     * @throws IOException
     */
    public static synchronized JMDictEntryBuilder getInstance(InputStream jsonFileAsInputStream) throws IOException {
        //lazy instantiation
        if(INSTANCE == null) {
            INSTANCE = new JMDictEntryBuilder(jsonFileAsInputStream);
        }
        return INSTANCE;
    }

    public List<JMDictEntry> getJMDictEntries(String input) {
        //We retrieve the specific entry from the dictionary here
        List<JMDictEntry> dictEntries = new ArrayList<>();
        //To deal with multiple matches
        List<JMDictEntry> possibleMatches = new ArrayList<>();

        for (Word word: words) {
            List<Sense> senses = word.getSense();
            for (Sense s: senses) {
                List<Gloss> glosses = s.getGloss();
                for (Gloss gloss : glosses) {
                    if (input.equals(gloss.getText())) {
                        //if match, get the kanji, kana and wordID
                        JMDictEntry entry = new JMDictEntry();
                        entry.setEngDef(input);
                        entry.setWordID(word.getId());
                        //find the common kana
                        for (Kana kana : word.getKana()) {
                            //assumes only one common kana
                            //or if more than one, take whichever common spelling is last
                            //(earlier results overwritten)
                            if (kana.isCommon()) {
                                entry.setKana(kana);
                                break;
                            }
                        }
                        //find the common kanji
                        for (Kanji kanji : word.getKanji()) {
                            //assumes only one common kanji
                            //or if more than one, take whichever common spelling is last
                            //(earlier results overwritten)
                            if (kanji.isCommon()) {
                                entry.setKanji(kanji);
                                break;
                            }
                        }
                        dictEntries.add(entry);
                    }
                }
            }
        }
        return dictEntries;
    }
}
