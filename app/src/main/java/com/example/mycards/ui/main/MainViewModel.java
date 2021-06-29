package com.example.mycards.ui.main;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.R)
public class MainViewModel extends ViewModel {

    //TODO - Replace Map with List of type Card
    private final Map<String, String> testDictionary =
            Map.of("apple", "りんご", "orange", "オレンジ", "watermelon", "スイカ");
    private Map<String, Boolean> shownWords = new HashMap<>();
    private Iterator<String> keyIterator = testDictionary.keySet().iterator();
    private String lastDisplayed;

    {
        //shownWords is a map of flags indicating whether a key value has been displayed or not
        //ASSUMPTION: shownWords contains exactly the same keys as testDictionary and its values are false initially
        testDictionary.entrySet().forEach(entry -> shownWords.put(entry.getKey(), false));
    }

    public Map<String, String> getTestDictionary() {
        return testDictionary;
    }

    public Map<String, Boolean> getShownWords() {
        return shownWords;
    }

    public Iterator<String> getKeyIterator() {
        return keyIterator;
    }

    public void setLastDisplayed(String str) {
        lastDisplayed = str;
    }

    public String getLastDisplayed() {
        return lastDisplayed;
    }
}