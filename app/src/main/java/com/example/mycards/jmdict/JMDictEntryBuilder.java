package com.example.mycards.jmdict;

import com.example.mycards.jmdict.pojo.Gloss;
import com.example.mycards.jmdict.pojo.Kana;
import com.example.mycards.jmdict.pojo.Kanji;
import com.example.mycards.jmdict.pojo.Root;
import com.example.mycards.jmdict.pojo.Sense;
import com.example.mycards.jmdict.pojo.JMDictWord;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class that stores the JMDict json file as a root, extracts the list of words,
 * finds the List of words that match a given String input and returns those words.
 */
public class JMDictEntryBuilder {

    private static JMDictEntryBuilder INSTANCE; //Singleton

    private Root root;
    private List<JMDictWord> words;

    /**
     * Constructor populates root and List of words
     * @param jsonFileAsInputStream reference to the right json file in resources/raw
     * @throws IOException
     */
    private JMDictEntryBuilder(InputStream jsonFileAsInputStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.root = mapper.readValue(jsonFileAsInputStream, Root.class);
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

        for (JMDictWord word: words) {
            int senseOrder = 0;
            List<Sense> senses = word.getSense();
            int senseCount = senses.size();
            for (Sense s: senses) {
                int glossOrder = 0;
                senseOrder++;
                List<Gloss> glosses = s.getGloss();
                int glossCount = glosses.size();
                for (Gloss gloss : glosses) {
                    glossOrder++;
                    if (matchEntry(input, gloss.getText())) {
                        //if match, get the kanji, kana and wordID
                        JMDictEntry entry = new JMDictEntry();
                        entry.setEngDef(input);
                        entry.setWordID(word.getId());

                        //add nums to entry to enable setPriority()
                        entry.setGlossOrder(glossOrder);
                        entry.setSenseOrder(senseOrder);
                        entry.setGlossCount(glossCount);
                        entry.setSenseCount(senseCount);

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
        sortEntries(dictEntries);
        return dictEntries;
    }

    private static void sortEntries(List<JMDictEntry> entries) {
        Comparator<JMDictEntry> jmDictEntryComparator = Comparator.comparingInt(JMDictEntry::getGlossOrder)
                .thenComparingInt(JMDictEntry::getSenseOrder)
                .thenComparingInt(JMDictEntry::getGlossCount);

        //TODO - this doesn't seem to work? NEEDS TESTING

        Collections.sort(entries, jmDictEntryComparator);
    }

    private boolean matchEntry(String inputString, String glossDef) {
        Pattern p = Pattern.compile("^" + inputString + "\\s\\([\\w\\s\\p{Punct}]*\\)");
        Matcher m = p.matcher(glossDef);

        if(inputString.equals(glossDef)) {
            return true;
        } else if(m.matches()) {
            //accounts for circs where the word would be standalone,
            // if not for elaboration in the JMDict file
            return true;
        } else {
            return false;
        }
    }
}
