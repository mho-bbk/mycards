package com.example.mycards.jmdict;

import android.content.Context;

import com.example.mycards.R;
import com.example.mycards.data.entities.JMDictEntry;
import com.example.mycards.jmdict.pojo.Gloss;
import com.example.mycards.jmdict.pojo.Kana;
import com.example.mycards.jmdict.pojo.Kanji;
import com.example.mycards.jmdict.pojo.Word;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class that stores the JMDict json file as a root, extracts the list of words,
 * finds the List of words that match a given String input and returns those words.
 */
public class JMDictEntryBuilder {

    private final JMDictJSONRoot root;
    private final List<Word> words;

    // Do we want this class to be a Singleton?
    // Should be only one per Activity (and this is a single activity app)
    /**
     * Constructor populates root and List of words
     * @param jsonFileAsInputStream reference to the right json file in resources/raw
     * @throws IOException
     */
    public JMDictEntryBuilder(InputStream jsonFileAsInputStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.root = mapper.readValue(jsonFileAsInputStream, JMDictJSONRoot.class);
        this.words = root.getWords();
    }

    public List<JMDictEntry> getJMDictEntries(String input) {
        //We retrieve the specific entry from the dictionary here
        List<JMDictEntry> dictEntries = new ArrayList<>();
        //To deal with multiple matches
        List<JMDictEntry> possibleMatches = new ArrayList<>();

        for (Word word: words) {
            List<Gloss> glosses = word.getSense().get(0).getGloss();
            for (Gloss gloss: glosses) {
                if(input.equals(gloss.getText())) {
                    //if match, get the kanji, kana and wordID
                    JMDictEntry entry = new JMDictEntry();
                    entry.setEngDef(input);
                    entry.setWordID(word.getId());
                    //find the common kana
                    for (Kana kana: word.getKana()) {
                        if(kana.isCommon()) {
                            entry.setKana(kana);
                        }
                    }
                    //find the common kanji
                    for (Kanji kanji: word.getKanji()) {
                        if(kanji.isCommon()) {
                            entry.setKanji(kanji);
                        }
                    }
                    dictEntries.add(entry);
                }
            }
        }
        return dictEntries;
    }
}
