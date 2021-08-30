package com.example.mycards.usecases.jptranslate;

import android.util.Log;

import com.example.mycards.base.usecasetypes.BaseUseCaseWithParam;
import com.example.mycards.data.entities.JMDictEntry;
import com.example.mycards.data.repositories.JMDictRepository;
import com.example.mycards.usecases.jptranslate.jmdict.kanatoromaji.KanaToRomaji;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Remit:
 * Takes a HashMap with the input word as the key and a list of related words (in English) as the values
 * Connects to JMDictRepository (local db)
 * Searches the local db and matches English words with their Jp equivalent using 'best' counts + order
 * Returns HashMap with input word as key (as original) and HashMap Eng:Jp words as value
 * Jp values use helper class KanaToRomaji so String includes romaji readings
 */
@Singleton
public class GetJpWordsUseCase implements BaseUseCaseWithParam<HashMap<String, List<String>>,
        HashMap<String, HashMap<String, String>>> {

    private final String TAG = "GetJpWordsUseCase";

    private final JMDictRepository dictRepository;
    private final KanaToRomaji romajiConverter = new KanaToRomaji();

    private final HashMap<String, HashMap<String, String>> wordAndRelatedWordsWithTranslations = new HashMap<>();

    @Inject
    public GetJpWordsUseCase(JMDictRepository dictRepository) {
        this.dictRepository = dictRepository;
    }

    @Override
    public HashMap<String, HashMap<String, String>> run(HashMap<String, List<String>> wordsForTranslation) {

        wordsForTranslation.entrySet().forEach( entry  -> {
            //the String key also needs to be translated so add to List
            List<String> inputStringAndRelatedWords = new ArrayList<>();
            inputStringAndRelatedWords.add(entry.getKey()); //add the original input String
            inputStringAndRelatedWords.addAll(entry.getValue());    //add all the related words

            wordAndRelatedWordsWithTranslations.put(entry.getKey(), translateWords(inputStringAndRelatedWords));
        });

        return wordAndRelatedWordsWithTranslations;
    }

    private HashMap<String, String> translateWords(List<String> wordsForTranslation) {
        HashMap<String, String> engToJpMap = new HashMap<>();

        wordsForTranslation.forEach(word -> {
            try {
                JMDictEntry result = dictRepository.getFirstJMDictEntry(word);  //NPE is currently handled by Repo - 27/8: is this still true?? TEST
                engToJpMap.put(result.getInnerGloss(), formatKanjiAndKana(result));
            } catch (NullPointerException npe) {
                //do nothing
                Log.d(TAG, "NPE: no result found for the word " + word);
            }
        });

        return engToJpMap;
    }

    //Helper method
    private String formatKanjiAndKana(JMDictEntry jmDictEntry) {
        //String formatted differently, depending on whether Kanji is blank
        if(jmDictEntry.getKanji().getKanjiText().equals("")) {
            return jmDictEntry.getKana().getKanaText() + " (" + romajiConverter.convert(jmDictEntry.getKana().getKanaText()) + ")";
        } else {
            return jmDictEntry.getKanji().getKanjiText() +
                    " (" + jmDictEntry.getKana().getKanaText() + ", " +
                    romajiConverter.convert(jmDictEntry.getKana().getKanaText()) +  ")";
        }
    }

}
