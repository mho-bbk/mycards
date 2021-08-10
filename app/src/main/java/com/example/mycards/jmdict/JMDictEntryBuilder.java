package com.example.mycards.jmdict;

import com.example.mycards.data.entities.JMDictEntry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO - there should be no need for this class once the JMDictRepository is up and running
 * Helper class that stores the JMDict json file as a root, extracts the list of words,
 * finds the List of words that match a given String input and returns those words.
 */
public class JMDictEntryBuilder {

    private static JMDictEntryBuilder INSTANCE; //Singleton

    private List<JMDictEntry> dictEntries;

    /**
     * Constructor populates List of JMDictEntry
     * @param jsonFileAsInputStream reference to the right json file in resources/raw
     */
    private JMDictEntryBuilder(InputStream jsonFileAsInputStream) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            this.dictEntries = mapper.readValue(jsonFileAsInputStream, new TypeReference<List<JMDictEntry>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
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

//    //TODO - put this on bg thread
//    public List<JMDictEntry> getJMDictEntries(String input) {
//        //We retrieve the specific entry from the dictionary here
//        List<JMDictEntry> dictEntries = new ArrayList<>();

//        sortEntries(dictEntries);
//        return dictEntries;
//    }

//    //TODO - test this
//    public Card getFirstEntryAsCard(String input) {
//        List<JMDictEntry> entries = getJMDictEntries(input);
//        if(entries.isEmpty()) {
//            return new Card("Blank", "Blank");
//        } else {
//            JMDictEntry jmde = entries.get(0);
//            String jWord;
//            if(jmde.getKanji().getText().equals("")) {
//                jWord = jmde.getKana().getText();
//            } else {
//                jWord = jmde.getKanji().getText() + " (" + jmde.getKana().getText() + ")";
//            }
//            return new Card(input, jWord);
//        }
//    }

    //sort by where the definition is found. GlossOrder of 1 = more commonly used term for that English word within that sense.
    //Then sort by SenseOrder. SenseOrder of 1 = assumed most common context for that English word.
    //Then sort by GlossCount. GlossCount of 1 = only word that matches the English term, assumed more likely to be the right one.
//    private static void sortEntries(List<JMDictEntry> entries) {
//        Comparator<JMDictEntry> jmDictEntryComparator = Comparator.comparingInt(JMDictEntry::getGlossOrder)
//                .thenComparingInt(JMDictEntry::getSenseOrder)
//                .thenComparingInt(JMDictEntry::getGlossCount);
//
//        Collections.sort(entries, jmDictEntryComparator);
//    }

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
