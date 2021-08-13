package com.example.mycards.usecases;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.example.mycards.base.usecasetypes.BaseUseCaseWithParam;
import com.example.mycards.data.entities.JMDictEntry;
import com.example.mycards.data.repositories.JMDictRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Remit:
 * Take a String (English)
 * Connect to JMDictRepository
 * Search the local db and match English word with Jp equivalent with 'best' counts + order
 * Add to HashMap that client can then call 'get' on
 * (Client uses HashMap for card creation)
 */
public class GetJpWordsUseCase implements BaseUseCaseWithParam<String, Boolean> {

    private final JMDictRepository dictRepository;

    private  HashMap<String, String> engToJpMap = new HashMap<>();

    private MutableLiveData<String> eachString = new MutableLiveData<>();
//    private LiveData<JMDictEntry> stringToJmDict = Transformations.switchMap(eachString,
//            (eachString) -> dictRepository.getFirstJMDictEntry(eachString));


    private final Observer<String> jmDictEntryObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            //Get result of the LiveData here and process
            JMDictEntry result = dictRepository.getFirstJMDictEntry(s);
            engToJpMap.put(result.getInnerGloss(), formatKanjiAndKana(result));
        }
    };

    @Inject
    public GetJpWordsUseCase(JMDictRepository dictRepository) {
        this.dictRepository = dictRepository;
//        stringToJmDict.observeForever(jmDictEntryObserver);
        eachString.observeForever(jmDictEntryObserver);
    }

    @Override
    public synchronized Boolean run(String param) {
        //Trigger the observer to get result and add to the hashmap
        eachString.setValue(param);

        return true;

        //Old implementation, didn't work - LiveData calls bc no observer?
//        HashMap<String, String> engToJapMap = new HashMap<>();
//        for (String s: param) {
//            //we only want one jp entry per eng word, so use getFirst
//            JMDictEntry jp = dictRepository.getFirstJMDictEntry(s).getValue();
//            String jpString = formatKanjiAndKana(jp);
//            engToJapMap.put(s, jpString);
//        }
//        return engToJapMap;
    }


    public String formatKanjiAndKana(JMDictEntry jmDictEntry) {
        //String formatted differently, depending on whether Kanji is blank
        if(jmDictEntry.getKanji().getKanjiText().equals("")) {
            return jmDictEntry.getKana().getKanaText();
        } else {
            return jmDictEntry.getKanji().getKanjiText() + " (" + jmDictEntry.getKana().getKanaText() + ")";
        }
    }

    public HashMap<String, String> getEngToJpMap() {
        return engToJpMap;
    }

    //enables VM to add the observer...
//    public void setObserver(Observer observer) {
//        stringToJmDict.observeForever(observer);
//    }
//
//    //enables VM to remove the observer...
//    public void removeObserver() {
//        stringToJmDict.removeObserver(jmDictEntryObserver);
//    }
}
