package com.example.mycards.ui.main;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.R)
public class MainViewModel extends ViewModel {

    private final Map<String, String> testDictionary =
            Map.of("apple", "りんご", "orange", "オレンジ", "watermelon", "スイカ");

    public Map<String, String> getTestDictionary() {
        return testDictionary;
    }
}