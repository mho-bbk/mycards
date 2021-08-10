package com.example.mycards.usecases;

import com.example.mycards.base.usecasetypes.BaseUseCaseWithParam;
import com.example.mycards.data.entities.JMDictEntry;
import com.example.mycards.data.repositories.JMDictRepository;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Remit:
 * Take a list of String (related English words)
 * Connect to JMDictRepository
 * Search the local db and match English words with Jp equivalents
 * Return as HashMap to client (for card creation)
 */
public class GetJpWordsUseCase implements BaseUseCaseWithParam<List<String>, HashMap<String, String>> {

    private JMDictRepository dictRepository;

    @Inject
    public GetJpWordsUseCase(JMDictRepository dictRepository) {
        this.dictRepository = dictRepository;
    }

    @Override
    public HashMap<String, String> run(List<String> param) {
        HashMap<String, String> engToJapMap = new HashMap<>();
        for (String s: param) {
            //we only want one jp entry per eng word, so use getFirst
            JMDictEntry jp = dictRepository.getFirstJMDictEntry(s).getValue();
            String jpString = formatJMDictEntry(jp);
            engToJapMap.put(s, jpString);
        }
        return engToJapMap;
    }

    private String formatJMDictEntry(JMDictEntry jmDictEntry) {
        //String formatted differently, depending on whether Kanji is blank
        if(jmDictEntry.getKanji().getKanjiText().equals("")) {
            return jmDictEntry.getKana().getKanaText();
        } else {
            return jmDictEntry.getKanji().getKanjiText() + " (" + jmDictEntry.getKana().getKanaText() + ")";
        }
    }
}
